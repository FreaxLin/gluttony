package top.interc.gluttony.web.executor;

import top.interc.gluttony.web.connect.HttpConnection;
import top.interc.gluttony.web.connect.HttpResult;
import top.interc.gluttony.web.controller.CrawlConfig;

import java.io.IOException;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 11:49
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public abstract class CrawlTask implements Runnable {

    private String url;

    private HttpConnection connection;

    private CrawlConfig config;

    public CrawlTask(String url, CrawlConfig config) {
        this.url = url;
        this.config = config;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(config.getSleepTime());
            filter(url);
            HttpResult result = connection.get(url);
            save(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public HttpConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpConnection connection) {
        this.connection = connection;
    }

    public abstract void save(HttpResult data);

    public abstract void filter(String url);
}
