package top.interc.web;

import com.fasterxml.jackson.databind.JavaType;
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
import top.interc.gluttony.web.model.RsListedCompanyChina;
import top.interc.gluttony.web.util.JsonMapper;

import java.io.IOException;
import java.util.List;

@Profile("local")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrawlTaskShSZCompany {

    @Autowired
    private RsListedCompanyChinaMapper companyChinaMapper;

    @Test
    public void test() throws IOException {
        String url = "http://ig507.com/data/base/gplist?licence=2A3059C9-3BD0-78F1-6AFE-0DEC3CCAA9EC";
        Document document = Jsoup.connect(String.format(url)).get();
        String data = document.body().text();
//        String data = "[{\"dm\":\"605399\",\"mc\":\"晨光新材\",\"jys\":\"sh\"},{\"dm\":\"605398\",\"mc\":\"新炬网络\",\"jys\":\"sh\"}]";
        JavaType javaType = JsonMapper.getInstance().createCollectionType(List.class, RsListedCompanyChina.class);
        List<RsListedCompanyChina> companyChinaList = JsonMapper.getInstance().fromJson(data, javaType);
        for (RsListedCompanyChina c : companyChinaList){
            companyChinaMapper.insert(c);
        }
        System.out.println("a");
    }
}
