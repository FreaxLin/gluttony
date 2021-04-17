package top.interc.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import top.interc.gluttony.web.WebApplication;
import top.interc.gluttony.web.mapper.RsListedCompanyChinaMapper;
import top.interc.gluttony.web.mapper.RsListedCompanyDetailMapper;
import top.interc.gluttony.web.model.RsListedCompanyChina;
import top.interc.gluttony.web.model.RsListedCompanyDetail;
import top.interc.gluttony.web.util.JsonMapper;

import java.io.IOException;
import java.util.List;

@Profile("local")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyDetailCrawlTask {

    @Autowired
    private RsListedCompanyChinaMapper companyChinaMapper;

    @Autowired
    private RsListedCompanyDetailMapper detailMapper;

    @Test
    public void test() throws IOException, InterruptedException {
        List<RsListedCompanyChina> companyChinaList = companyChinaMapper.findAll(2418);
        String url = "http://ig507.com/data/time/f10/info/%s?licence=2A3059C9-3BD0-78F1-6AFE-0DEC3CCAA9EC";
        for (RsListedCompanyChina companyChina : companyChinaList){
            Thread.sleep(2000);
            String s = String.format(url, companyChina.getCode());
            Document document = Jsoup.connect(String.format(s)).get();
            String data = document.body().text();
            System.out.println(data);
            RsListedCompanyDetail companyDetail = JsonMapper.getInstance().fromJson(data, RsListedCompanyDetail.class);
            companyDetail.setCode(companyChina.getCode());
            detailMapper.insert(companyDetail);
        }

    }
}
