package queue;

import org.junit.Before;
import org.junit.Test;
import top.interc.crawler.controller.CrawlerConfig;
import top.interc.crawler.executor.CrawlTask;
import top.interc.crawler.executor.CrawlTaskSerializer;
import top.interc.crawler.storage.EmbeddedQueue;
import top.interc.crawler.storage.PreCrawlUrlQueue;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 17:39
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class EmbedQueueTest {

    private EmbeddedQueue<CrawlTask> queue ;

    @Before
    public void before() {
        CrawlerConfig config = new CrawlerConfig();
        config.setCrawlStorageFolder("./folder");
        this.queue = new PreCrawlUrlQueue<>(config, new CrawlTaskSerializer());
    }

    @Test
    public void test(){
        String url = "www.crawl.com";
        CrawlTask task = new CrawlTask(url);
        queue.put(task);
        CrawlTask task1 = queue.getFirst();
        System.out.println(task1.getUrl());
    }
}
