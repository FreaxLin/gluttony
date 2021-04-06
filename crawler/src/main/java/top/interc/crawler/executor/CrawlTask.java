package top.interc.crawler.executor;

import top.interc.crawler.connect.HttpConnection;
import top.interc.crawler.connect.HttpResult;

import java.io.IOException;
import java.util.Map;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 11:49
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlTask implements Runnable {

    private String url;

    private HttpConnection connection;

    public CrawlTask(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
//        filter(url);
        try {
            HttpResult result = connection.get(url);
            Map<String, String> headerMap = result.getHeader();
            String contentType = headerMap.get("Content-Type");
            String[] values =contentType.split(";");

            if (values.length > 0){

            }

            System.out.println(contentType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpConnection connection) {
        this.connection = connection;
    }

//    public abstract void save(ParseData data);
//
//    public abstract void filter(String url);
}
