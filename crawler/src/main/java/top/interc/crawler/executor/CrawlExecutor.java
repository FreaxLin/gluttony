package top.interc.crawler.executor;

import top.interc.crawler.controller.CrawlerConfig;

import java.util.concurrent.*;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 10:37
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlExecutor{

    private ExecutorService chartExecutor;

    private CrawlerConfig config;



    public CrawlExecutor(CrawlerConfig config) {
//        int threadNum = config.getCrawlerNumber();
//        this.chartExecutor = new ThreadPoolExecutor(threadNum, threadNum,
//                0L, TimeUnit.MILLISECONDS,
//                new MmapBlockingQueue<>(config, new CrawlTaskSerializer()),
//                new CrawlhreadFactory(),
//                new CrawlRejectedExecutionHandler());
    }


}
