package top.interc.gluttony.web.connect;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.interc.gluttony.web.controller.CrawlConfig;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DefaultHttpConnection implements HttpConnection {

    protected static final Logger logger = LoggerFactory.getLogger(DefaultHttpConnection.class);

    protected final CrawlConfig config;
    protected PoolingHttpClientConnectionManager connectionManager;
    protected CloseableHttpClient httpClient;
    protected IdleConnectionMonitorThread connectionMonitorThread = null;

    public DefaultHttpConnection(CrawlConfig config) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        this.config = config;
        RequestConfig requestConfig = RequestConfig.custom()
                .setExpectContinueEnabled(false)
                .setCookieSpec(config.getCookiePolicy())
                .setRedirectsEnabled(false)
                .setSocketTimeout(config.getSocketTimeOut())
                .setConnectTimeout(config.getConnectionTimeOut())
                .build();

        RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
        connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);

        if (config.isIncludeHttpsPage()){
            try{
                // By always trusting the ssl certificate
                SSLContext sslContext =
                        SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                            @Override
                            public boolean isTrusted(final X509Certificate[] chain, String authType) {
                                return true;
                            }
                        }).build();
                SSLConnectionSocketFactory sslsf =
                        new SniSSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
                connRegistryBuilder.register("https", sslsf);
            } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | RuntimeException e) {
                logger.warn("Exception thrown while trying to register https");
                logger.debug("Stacktrace", e);
                throw e;
            }
        }

        Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
        connectionManager =
                new SniPoolingHttpClientConnectionManager(connRegistry, new SystemDefaultDnsResolver());
        connectionManager.setMaxTotal(2);
        connectionManager.setDefaultMaxPerRoute(2);

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.setDefaultRequestConfig(requestConfig);
        clientBuilder.setConnectionManager(connectionManager);
        clientBuilder.setUserAgent(config.getUserAgentString());

        httpClient = clientBuilder.build();

        if (connectionMonitorThread == null) {
            connectionMonitorThread = new IdleConnectionMonitorThread(connectionManager);
        }
        connectionMonitorThread.start();
    }

    @Override
    public HttpResult post(HttpRequest httpRequest) throws IOException {
        HttpPost post = new HttpPost(httpRequest.getUrl());
        if (httpRequest.getPostParamList() != null){
            List<NameValuePair> list = new ArrayList<>();

            for (HttpRequest.PostParam param : httpRequest.getPostParamList()){
                list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
            post.setEntity(uefEntity);
        }
        Map<String, String> headerMap = httpRequest.getHeaderMap();
        for (Map.Entry<String, String> entry : headerMap.entrySet()){
            post.addHeader(entry.getKey(), entry.getValue());
        }
        HttpResult httpResult = new HttpResult();
        try {
            CloseableHttpResponse response = httpClient.execute(post);
            httpResult = parseResult(response);
            return httpResult;

        } finally {
            if ((httpResult.getContent() == null) && (post != null)) {
                post.abort();
            }
        }

    }

    @Override
    public HttpResult get(String url) throws IOException {
        HttpResult httpResult = new HttpResult();
        HttpGet request = null;
        try {
            request = new HttpGet(url);

            CloseableHttpResponse response = httpClient.execute(request);
            httpResult = parseResult(response);
            return httpResult;

        } finally { // occurs also with thrown exceptions
            if ((httpResult.getContent() == null) && (request != null)) {
                request.abort();
            }
        }
    }

    private HttpResult parseResult(CloseableHttpResponse response) throws IOException {
        HttpResult httpResult = new HttpResult();
        httpResult.setContent(EntityUtils.toString(response.getEntity()));

        Header[] headers = response.getAllHeaders();
        for (Header header : headers){
            httpResult.addHeader(header.getName(), header.getValue());
        }

        httpResult.setCode(response.getStatusLine().getStatusCode());

        return httpResult;
    }
}
