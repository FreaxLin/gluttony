package top.interc.crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.interc.crawler.model.request.CrawlRequest;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 17:23
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
@Controller
@RequestMapping("/crawl")
public class CrawlerController {


    @PostMapping("/submit")
    public boolean submit(@RequestBody CrawlRequest request){
        return true;
    }
}
