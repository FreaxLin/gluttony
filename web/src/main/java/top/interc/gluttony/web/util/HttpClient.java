package top.interc.gluttony.web.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/14 15:20
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class HttpClient {

    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    public static HttpResult get(String url, Map<String, String> headerMap){

        HttpGet httpGet = new HttpGet(url);
        if (headerMap != null){
            for(Map.Entry<String, String> entry : headerMap.entrySet()){
                Header header = new BasicHeader(entry.getKey(), entry.getValue());
                httpGet.setHeader(header);
            }
        }


        CloseableHttpResponse response = null;
        try {

            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200){
                HttpResult result = new HttpResult();
                HttpEntity responseEntity = response.getEntity();
                result.setBody(EntityUtils.toString(responseEntity));
                result.setCode(200);
                Header[] headers = response.getAllHeaders();
                for (Header header : headers){
                    result.addHeader(header.getName(), header.getValue());
                }
                return result;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
