package top.interc.crawler.executor;

import top.interc.crawler.controller.CrawlConfig;

import java.util.concurrent.*;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 10:37
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlExecutor{

    private ExecutorService crawlExecutor;

    private CrawlConfig config;

    public CrawlExecutor(CrawlConfig config) {
        int threadNum = config.getCrawlerNumber();
        this.crawlExecutor = new ThreadPoolExecutor(threadNum, threadNum,
                0L, TimeUnit.MILLISECONDS,
                new MmapBlockingQueue(config, new CrawlTaskSerializer()),
//                new ArrayBlockingQueue<>(10),
                new CrawlhreadFactory(),
                new CrawlRejectedExecutionHandler());
    }

    public void execute(CrawlTask task){
        this.crawlExecutor.execute(task);
    }
}
