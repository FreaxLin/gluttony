import org.junit.Before;
import org.junit.Test;
import top.interc.crawler.connect.DefaultHttpConnection;
import top.interc.crawler.connect.HttpRequest;
import top.interc.crawler.connect.HttpResult;
import top.interc.crawler.controller.CrawlerConfig;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/6 10:31
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class TestHttpConnection {

    private List<String> testUrls = new ArrayList<>();

    @Before
    public void before(){
        testUrls.add("http://www.baidu.com");
        testUrls.add("https://zhuanlan.zhihu.com/p/41322638");
        testUrls.add("https://blog.csdn.net/u014133299/article/details/80676147");
        testUrls.add("https://github.com/FreaxLin/gluttony");
    }

    @Test
    public void test() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        CrawlerConfig config = new CrawlerConfig();

        DefaultHttpConnection connection = new DefaultHttpConnection(config);
        try {
            for (String s : testUrls){

                HttpResult result = connection.get(s);
                System.out.println(result.getContent());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
