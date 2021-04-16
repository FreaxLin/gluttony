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
import top.interc.gluttony.web.mapper.SiteZdbInvGroupMapper;
import top.interc.gluttony.web.mapper.SiteZdbInvRecordMapper;
import top.interc.gluttony.web.mapper.SiteZdbPedailyCnInvestCompanyMapper;
import top.interc.gluttony.web.model.SiteZdbInvGroup;
import top.interc.gluttony.web.model.SiteZdbInvRecord;
import top.interc.gluttony.web.model.SiteZdbPedailyCnInvestCompany;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/16 14:55
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
@Profile("local")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZdbPedailyCnInvCrawlTask {

    @Autowired
    private SiteZdbInvRecordMapper recordMapper;

    @Autowired
    private SiteZdbInvGroupMapper groupMapper;

    @Autowired
    private SiteZdbPedailyCnInvestCompanyMapper investCompanyMapper;

    @Test
    public void test(){
        List<SiteZdbPedailyCnInvestCompany> investCompanyList = investCompanyMapper.findAll();
        for (int i = 0; i < investCompanyList.size(); i++){
            SiteZdbPedailyCnInvestCompany company = investCompanyList.get(i);
            String url = "https://zdb.pedaily.cn/inv/v%s-p%s";
            try {
                Thread.sleep(2000);
                int index = 1;
                Document document = Jsoup.connect(String.format(url, company.getCode(), index)).get();
                while (document != null){
                    Element inv = document.select("ul#inv-list").get(0);
                    Elements invList = inv.getElementsByTag("li");
                    for (Element e : invList){
                        SiteZdbInvRecord record = new SiteZdbInvRecord();
                        record.setInvCompanyUrl(e.select("div.top").get(0).getElementsByTag("a").attr("href"));
                        record.setInvCompany(e.select("div.top").get(0).getElementsByTag("a").text());

                        record.setInvTurn(e.select("div.info").get(0).getElementsByTag("span").get(0).text());
                        record.setInvMoney(e.select("div.info").get(0).getElementsByTag("span").get(1).text());
                        Elements invcList = e.select("div.group").get(0).getElementsByTag("a");
                        List<SiteZdbInvGroup> groupList = new ArrayList<>();
                        for (Element invc : invcList){
                            SiteZdbInvGroup group = new SiteZdbInvGroup();
                            group.setInvGroupShowUrl(invc.attr("href"));
                            group.setInvGroupName(invc.text());
                            groupList.add(group);
                        }
                        record.setInvTime(e.select("div.date").text());
                        record.setDescriptionUrl(e.select("div.view").get(0).getElementsByTag("a").get(0).attr("href"));
                        recordMapper.insert(record);
                        for (SiteZdbInvGroup group : groupList) {
                            group.setInvRecordId(record.getId());
                            groupMapper.insert(group);
                        }

                    }
                    i++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
