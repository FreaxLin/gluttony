//package top.interc.crawler.connect;
//
//import org.apache.http.Header;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.Credentials;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustStrategy;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.ssl.SSLContexts;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import top.interc.crawler.controller.CrawlerConfig;
//
//import javax.net.ssl.SSLContext;
//import java.io.IOException;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//import java.util.HashMap;
//import java.util.Map;
//
//public class DefaultHttpConnection implements HttpConnection {
//
//    protected static final Logger logger = LoggerFactory.getLogger(DefaultHttpConnection.class);
//    protected final Object mutex = new Object();
//    /**
//     * This field is protected for retro compatibility. Please use the getter method: getConfig() to
//     * read this field;
//     */
//    protected final CrawlerConfig config;
//    protected PoolingHttpClientConnectionManager connectionManager;
//    protected CloseableHttpClient httpClient;
//    protected long lastFetchTime = 0;
//    protected IdleConnectionMonitorThread connectionMonitorThread = null;
//
//    public DefaultHttpConnection(CrawlerConfig config) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        this.config = config;
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setExpectContinueEnabled(false)
//                .setCookieSpec(config.getCookiePolicy())
//                .setRedirectsEnabled(false)
//                .setSocketTimeout(config.getSocketTimeout())
//                .setConnectTimeout(config.getConnectionTimeout())
//                .build();
//
//        RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
//        connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);
//        if (config.isIncludeHttpsPages()) {
//            try { // Fixing: https://code.google.com/p/crawler4j/issues/detail?id=174
//                // By always trusting the ssl certificate
//                SSLContext sslContext =
//                        SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
//                            @Override
//                            public boolean isTrusted(final X509Certificate[] chain, String authType) {
//                                return true;
//                            }
//                        }).build();
//                SSLConnectionSocketFactory sslsf =
//                        new SniSSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
//                connRegistryBuilder.register("https", sslsf);
//            } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | RuntimeException e) {
//                if (config.isHaltOnError()) {
//                    throw e;
//                } else {
//                    logger.warn("Exception thrown while trying to register https");
//                    logger.debug("Stacktrace", e);
//                }
//            }
//        }
//
//        Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
//        connectionManager =
//                new SniPoolingHttpClientConnectionManager(connRegistry, config.getDnsResolver());
//        connectionManager.setMaxTotal(config.getMaxTotalConnections());
//        connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerHost());
//
//        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
//        if (config.getCookieStore() != null) {
//            clientBuilder.setDefaultCookieStore(config.getCookieStore());
//        }
//        clientBuilder.setDefaultRequestConfig(requestConfig);
//        clientBuilder.setConnectionManager(connectionManager);
//        clientBuilder.setUserAgent(config.getUserAgentString());
//        clientBuilder.setDefaultHeaders(config.getDefaultHeaders());
//
//        Map<AuthScope, Credentials> credentialsMap = new HashMap<>();
//        if (config.getProxyHost() != null) {
//            if (config.getProxyUsername() != null) {
//                AuthScope authScope = new AuthScope(config.getProxyHost(), config.getProxyPort());
//                Credentials credentials = new UsernamePasswordCredentials(config.getProxyUsername(),
//                        config.getProxyPassword());
//                credentialsMap.put(authScope, credentials);
//            }
//
//            HttpHost proxy = new HttpHost(config.getProxyHost(), config.getProxyPort());
//            clientBuilder.setProxy(proxy);
//            logger.debug("Working through Proxy: {}", proxy.getHostName());
//        }
//
//        httpClient = clientBuilder.build();
//
//        if (connectionMonitorThread == null) {
//            connectionMonitorThread = new IdleConnectionMonitorThread(connectionManager);
//        }
//        connectionMonitorThread.start();
//    }
//
//    @Override
//    public HttpResult post(HttpRequest httpRequest) {
//        return null;
//    }
//
//    @Override
//    public HttpResult get(String url) throws IOException {
//        HttpResult httpResult = new HttpResult();
//        HttpGet request = null;
//        try {
//            request = new HttpGet(url);
//
//            CloseableHttpResponse response = httpClient.execute(request);
//            httpResult.setContent(EntityUtils.toString(response.getEntity()));
//
//            Header[] headers = response.getAllHeaders();
//            for (Header header : headers){
//                httpResult.addHeader(header.getName(), header.getValue());
//            }
//
//            httpResult.setCode(response.getStatusLine().getStatusCode());
//
//            return httpResult;
//
//        } finally { // occurs also with thrown exceptions
//            if ((httpResult.getContent() == null) && (request != null)) {
//                request.abort();
//            }
//        }
//    }
//}
