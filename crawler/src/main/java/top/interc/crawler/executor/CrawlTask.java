package top.interc.crawler.executor;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 11:49
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlTask implements Runnable {

    private String url;

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

    }
}
