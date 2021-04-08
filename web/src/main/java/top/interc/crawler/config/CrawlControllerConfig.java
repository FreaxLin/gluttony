package top.interc.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.interc.crawler.controller.CrawlConfig;
import top.interc.crawler.controller.CrawlController;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 17:46
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
@Configuration
public class CrawlControllerConfig {

    @Bean
    public CrawlController crawlController(){
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("queue");
        config.setCrawlerNumber(1);
        CrawlController crawlController = new CrawlController(config);
        return crawlController;
    }
}
