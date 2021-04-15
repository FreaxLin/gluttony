package top.interc.gluttony.web.executor;

import top.interc.gluttony.web.connect.HttpConnection;
import top.interc.gluttony.web.controller.CrawlConfig;

import java.util.concurrent.*;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 10:37
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlExecutor{

    private ThreadPoolExecutor crawlExecutor;

    private CrawlConfig config;

    public CrawlExecutor(CrawlConfig config, HttpConnection connection) {
        int threadNum = config.getCrawlerNumber();
        this.crawlExecutor = new ThreadPoolExecutor(threadNum, threadNum,
                0L, TimeUnit.MILLISECONDS,
                new MmapBlockingQueue(config, new CrawlTaskSerializer(connection, config)),
                new CrawlThreadFactory(),
                new CrawlRejectedExecutionHandler());
    }

    public int getActiveThread(){
        return this.crawlExecutor.getActiveCount();
    }

    public void execute(CrawlTask task){
        this.crawlExecutor.execute(task);
    }
}
