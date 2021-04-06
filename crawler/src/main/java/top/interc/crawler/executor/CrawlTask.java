package top.interc.crawler.executor;

import top.interc.crawler.connect.HttpConnection;
import top.interc.crawler.connect.HttpResult;
import top.interc.crawler.parser.ParseData;

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
        System.out.println(url);
//        try {
//            HttpResult result = connection.get(url);
//            Map<String, String> headerMap = result.getHeader();
//            String contentType = headerMap.get("Content-Type");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public abstract void save(ParseData data);
//
//    public abstract void filter(String url);
}
