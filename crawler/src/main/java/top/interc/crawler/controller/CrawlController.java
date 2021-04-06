package top.interc.crawler.controller;

import top.interc.crawler.connect.DefaultHttpConnection;
import top.interc.crawler.connect.HttpResult;
import top.interc.crawler.executor.CrawlExecutor;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class CrawlController {

    private CrawlConfig config;

    private CrawlExecutor crawlExecutor;

    public CrawlController(CrawlConfig config) {
        this.config = config;
        this.crawlExecutor = new CrawlExecutor(config);
        try {
            DefaultHttpConnection connection = new DefaultHttpConnection(config);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void start(){


    }
}
