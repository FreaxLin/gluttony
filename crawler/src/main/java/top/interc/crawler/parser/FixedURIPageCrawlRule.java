package top.interc.crawler.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 10:14
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class FixedURIPageCrawlRule implements CrawlRule{


    private String parentUrl;


    @Override
    public List<String> getNextUrls() {
        List<String> urls = new ArrayList<>(1);

        return null;
    }
}
