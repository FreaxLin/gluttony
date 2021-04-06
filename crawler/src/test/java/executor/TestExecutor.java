package executor;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.mapdb.Serializer;
import top.interc.crawler.controller.CrawlerConfig;
import top.interc.crawler.executor.CrawlExecutor;
import top.interc.crawler.executor.CrawlTask;
import top.interc.crawler.storage.PreCrawlUrlQueue;

import java.util.concurrent.Executors;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 20:24
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class TestExecutor {

    @Test
    public void test(){
        CrawlerConfig config = new CrawlerConfig();
        config.setCrawlStorageFolder("queue");
        config.setCrawlerNumber(1);
        CrawlExecutor executor = new CrawlExecutor(config);
        for(int i = 0; i < 10; i++){

            executor.execute(new CrawlTask(RandomStringUtils.randomAlphabetic(10)));
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
