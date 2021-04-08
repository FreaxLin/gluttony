package io;

import org.junit.Test;
import top.interc.crawler.connect.DefaultHttpConnection;
import top.interc.crawler.connect.HttpConnection;
import top.interc.crawler.connect.HttpRequest;
import top.interc.crawler.connect.HttpResult;
import top.interc.crawler.controller.CrawlConfig;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 15:33
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class HttpTest {

    @Test
    public void post() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CrawlConfig crawlConfig = new CrawlConfig();
        HttpConnection connection = new DefaultHttpConnection(crawlConfig);

        HttpRequest request = new HttpRequest();
        request.setUrl("https://www.lagou.com/jobs/positionAjax.json?px=default&city=%E7%8F%A0%E6%B5%B7&needAddtionalResult=false");
        request.addParam("first", "false");
        request.addParam("pn", "2");
        request.addParam("kd", "Java");
        request.addParam("sid", "6f29275825b84c019e8e96edcec087a2");

        request.addHead("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36");
        request.addHead("authority", "www.lagou.com");
        request.addHead("method", "POST");
        request.addHead("accept", "application/json, text/javascript, */*; q=0.01");
        request.addHead("cookie", "JSESSIONID=ABAAABAABEIABCI5CE688C31337EEF26853ED2499BD7920; WEBTJ-ID=2021048%E4%B8%8B%E5%8D%883:09:46150946-178b050c67d296-0c3d1d63bc739d-5771133-2073600-178b050c67e75b; RECOMMEND_TIP=true; privacyPolicyPopup=false; PRE_UTM=; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; user_trace_token=20210408150946-27d50b4e-eda0-473b-8a88-d54a3245f612; LGUID=20210408150946-f02abc19-7d9e-4a10-81d3-400e6806eb80; sajssdk_2015_cross_new_user=1; sensorsdata2015session=%7B%7D; _ga=GA1.2.2002267987.1617865788; _gid=GA1.2.1694219674.1617865788; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1617865788; LGSID=20210408150947-a428f101-800c-401d-876c-0e39ca5345ae; PRE_HOST=www.baidu.com; PRE_SITE=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DbtL0zyZFCapogZn09VW1sCsqWgdsqkjadRlYnNARrTC%26wd%3D%26eqid%3Da7fbbcba0005b07e00000006606eac37; index_location_city=%E5%B9%BF%E5%B7%9E; TG-TRACK-CODE=index_navigation; __lg_stoken__=7966418ccf704d87e241df20427ab694174607ebb106760514ed4b5eb574f8cd824a4cc9fe98cc4608223eef128a0ba5cd4add458612dc2c5ca486cc595cd5d77c6cdfd0380f; SEARCH_ID=723c503776644f1ba610f888b313748b; X_HTTP_TOKEN=7dea9faa837a37bf1495687161a57aea118464323e; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22178b050c89b24-09176404e473f8-5771133-2073600-178b050c89ca8b%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E8%87%AA%E7%84%B6%E6%90%9C%E7%B4%A2%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fwww.baidu.com%2Flink%22%2C%22%24os%22%3A%22Windows%22%2C%22%24browser%22%3A%22Chrome%22%2C%22%24browser_version%22%3A%2289.0.4389.82%22%7D%2C%22%24device_id%22%3A%22178b050c89b24-09176404e473f8-5771133-2073600-178b050c89ca8b%22%7D; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1617865943; LGRID=20210408151258-f3e7054b-54b9-477d-a025-90e2e1fc4213");
        request.addHead("origin", "https://www.lagou.com");
        request.addHead("referer", "https://www.lagou.com/jobs/list_Java/p-city_216?px=default");
        request.addHead("sec-ch-ua", "\"Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99\"");

        HttpResult result = connection.post(request);
        System.out.println(result.getContent());
    }
}
