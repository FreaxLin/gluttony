package queue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import top.interc.gluttony.web.controller.CrawlConfig;
import top.interc.gluttony.web.executor.CrawlTaskSerializer;
import top.interc.gluttony.web.executor.MmapBlockingQueue;
import top.interc.gluttony.web.executor.SimpleCrawlTask;

import java.util.concurrent.BlockingQueue;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/6 15:02
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class MmapBlockingQueueTest {

    @Test
    public void test(){
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("queue");
        BlockingQueue queue = new MmapBlockingQueue(config, new CrawlTaskSerializer());
        for(int i = 0; i < 10; i++){
            queue.offer(new SimpleCrawlTask(RandomStringUtils.randomAlphabetic(10), config));
        }
        for(int i = 0; i < 10; i++){
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
