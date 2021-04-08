package top.interc.crawler.controller;

import org.junit.Test;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/6 15:20
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class TestController {

    @Test
    public void test(){
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("queue");
        config.setCrawlerNumber(1);
        config.addSeed("https://www.baidu.com/s?wd=apple&pn=1&rn=10&tn=json");
        config.addSeed("https://zhuanlan.zhihu.com/p/41322638");
        config.addSeed("https://blog.csdn.net/u014133299/article/details/80676147");
        config.addSeed("https://github.com/FreaxLin/gluttony");
        CrawlController controller = new CrawlController(config);
        controller.start();
    }
}
