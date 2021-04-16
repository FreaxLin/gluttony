package top.interc.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import top.interc.gluttony.web.WebApplication;
import top.interc.gluttony.web.mapper.SiteZdbPedailyCnMapper;
import top.interc.gluttony.web.model.SiteZdbPedailyCn;

import java.io.IOException;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/16 12:29
 * @description：爬取了投资网所有投资机构的总览
 * @modified By：
 * @version: 1.0
 */
@Profile("local")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SiteZdbPedailyCnCrawlTask {

    @Autowired
    private SiteZdbPedailyCnMapper siteZdbPedailyCnMapper;

    @Test
    public void test() throws IOException {
        Document document = Jsoup.connect("https://zdb.pedaily.cn/company/").get();
        Elements elements = document.select("div.company");
        for (Element e : elements){
            String trade = e.getElementsByTag("h3").get(0).text();
            System.out.println(trade);
            Elements uls = e.getElementsByTag("ul");
            for (Element ul : uls){
                Elements lis = ul.getElementsByTag("li");
                for (Element li : lis){
                    SiteZdbPedailyCn siteZdbPedailyCn = new SiteZdbPedailyCn();
                    siteZdbPedailyCn.setCompany(li.getElementsByTag("a").text());
                    siteZdbPedailyCn.setTrade(trade);
                    siteZdbPedailyCn.setUrl(li.getElementsByTag("a").attr("href"));
                    siteZdbPedailyCnMapper.insertSelective(siteZdbPedailyCn);
                    System.out.println(li);
                }
            }
        }

    }
}
