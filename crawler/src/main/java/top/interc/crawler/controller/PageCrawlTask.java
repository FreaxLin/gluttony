package top.interc.crawler.controller;

import top.interc.crawler.connect.HttpConnection;
import top.interc.crawler.connect.HttpRequest;
import top.interc.crawler.connect.HttpResult;
import top.interc.crawler.util.JsonMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 17:14
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class PageCrawlTask implements Runnable {

    private String url;

    private String method;

    private HttpConnection connection;

    private Map<String, String> header = new HashMap<>();

    private Map<String, String> param = new HashMap<>();



    @Override
    public void run() {

        if (method.equals("post")){
            HttpRequest request = new HttpRequest();
            request.setUrl(url);
            for (Map.Entry<String, String> entry : param.entrySet()){
                request.addParam(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String> entry : header.entrySet()){
                request.addHead(entry.getKey(), entry.getValue());
            }

            HttpResult result = null;
            try {
                result = connection.post(request);
                if (result.getContent() != null){
                    JsonMapper.getInstance().fromJson(result.getContent(), Map.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
