package top.interc.crawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.interc.crawler.executor.CrawlExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 9:26
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class MonitorThread implements Runnable {

    public static Logger logger = LoggerFactory.getLogger(MonitorThread.class);

    private CountDownLatch stop;

    private CrawlExecutor executor;

    private AtomicInteger counter = new AtomicInteger(0);

    public MonitorThread(CountDownLatch stop, CrawlExecutor executor) {
        this.stop = stop;
        this.executor = executor;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(2000);
                int active = executor.getActiveThread();
                if (active == 0){
                    int i = counter.incrementAndGet();
                    if (i == 5){
                        logger.info("超过5次统计所有线程空闲，即将退出......");
                        stop.countDown();
                    }
                }else{
                    if (counter.incrementAndGet() > 0){
                        counter.set(0);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
