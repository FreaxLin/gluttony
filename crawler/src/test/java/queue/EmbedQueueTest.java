package queue;

import org.junit.Before;
import org.junit.Test;
import org.mapdb.Serializer;
import top.interc.gluttony.web.controller.CrawlConfig;
import top.interc.gluttony.web.storage.EmbeddedQueue;
import top.interc.gluttony.web.storage.PreCrawlUrlQueue;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 17:39
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class EmbedQueueTest {

    private EmbeddedQueue<String> queue ;

    @Before
    public void before() {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("queue");
        this.queue = new PreCrawlUrlQueue<>(config, Serializer.STRING);
    }

    @Test
    public void test(){
        String url = "www.crawl.com";
        for (int i = 0; i < 10000; i++){
            queue.put(url);
        }
        for (int i = 0; i < 10000; i++){
            System.out.println(queue.poll());
        }
        System.out.println("end");

//        CrawlTask task1 = queue.getFirst();
//        System.out.println(task1.getUrl());
//        task1 = queue.getFirst();
//        System.out.println(task1.getUrl());
    }
}
