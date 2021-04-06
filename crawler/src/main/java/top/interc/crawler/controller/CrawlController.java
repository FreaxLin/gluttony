package top.interc.crawler.controller;

import top.interc.crawler.connect.DefaultHttpConnection;
import top.interc.crawler.connect.HttpConnection;
import top.interc.crawler.executor.CrawlExecutor;
import top.interc.crawler.executor.CrawlTask;
import top.interc.crawler.executor.CrawlTaskFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CrawlController {

    private CrawlConfig config;

    private CrawlExecutor crawlExecutor;

    private HttpConnection connection;

    private CountDownLatch status;

    private String className;

    public CrawlController(CrawlConfig config) {
        this.config = config;
        this.className = config.getBaseCrawlClazzName();
        try {
            this.connection = new DefaultHttpConnection(config);
            this.crawlExecutor = new CrawlExecutor(config, connection);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        this.status = new CountDownLatch(1);
        List<String> seeds = config.getSeeds();
        for (String url : seeds){
            CrawlTask task = CrawlTaskFactory.build(className, url);
            task.setConnection(this.connection);
            this.crawlExecutor.execute(task);
        }

        try {
            status.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
