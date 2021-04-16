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
import top.interc.gluttony.web.mapper.SiteZdbPedailyCnInvestCompanyMapper;
import top.interc.gluttony.web.mapper.SiteZdbPedailyCnMapper;
import top.interc.gluttony.web.model.SiteZdbPedailyCn;
import top.interc.gluttony.web.model.SiteZdbPedailyCnInvestCompany;

import java.io.IOException;
import java.util.List;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/16 12:51
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
@Profile("local")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SiteZdbPedailyCnCompanyCrawlTask {

    @Autowired
    private SiteZdbPedailyCnInvestCompanyMapper companyMapper;

    @Autowired
    private SiteZdbPedailyCnMapper cnMapper;

    @Test
    public void test() {
        List<SiteZdbPedailyCn> companyList = cnMapper.selectAll(null);
        for (SiteZdbPedailyCn cn : companyList) {

            String url = cn.getUrl().substring(0, cn.getUrl().length() - 1);
            System.out.println(url);
            try {
                Document document = Jsoup.connect(url).get();
                Element element = document.select("div.info").get(0);
                SiteZdbPedailyCnInvestCompany company = new SiteZdbPedailyCnInvestCompany();
                company.setCode(url.substring(url.lastIndexOf("/") + 1).replace("show", ""));
                company.setChName(element.getElementsByTag("h1").get(0).ownText());
                company.setEnName(element.getElementsByTag("h2").get(0).ownText());
                company.setCapitalType(element.getElementsByTag("li").get(0).ownText());
                company.setInvestmentType(element.getElementsByTag("li").get(1).ownText());
                company.setPlaceRegister(element.getElementsByTag("li").get(2).ownText());
                company.setFoundingTime(element.getElementsByTag("li").get(3).ownText());
                company.setHeadQuarter(element.getElementsByTag("li").get(4).ownText());
                if (element.getElementsByTag("li").get(5).getElementsByTag("a").size() > 0) {

                    company.setOfficialWebsite(element.getElementsByTag("li").get(5).getElementsByTag("a").get(0).attr("href"));
                }
                company.setInvestmentStage(element.getElementsByTag("li").get(6).ownText());
                Elements desc = document.select("div#desc");
                if (desc.size() > 0) {
                    Elements plist = document.select("div#desc").get(0).getElementsByTag("p");
                    if (plist.size() > 1) {
                        company.setDescription(document.select("div#desc").get(0).getElementsByTag("p").get(1).ownText());
                    }
                }


                if (document.select("div#contact").size() > 0) {
                    company.setContactWay(document.select("div#contact").get(0).getElementsByTag("p").get(0).ownText());
                }

//        System.out.println(document.getElementsByTag("div#desc"));
                companyMapper.insert(company);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

//        System.out.println(document);

        }
//
//        System.out.println(element.getElementsByTag("h1").get(0).ownText());
    }
}
