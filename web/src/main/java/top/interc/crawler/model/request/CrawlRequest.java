package top.interc.crawler.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 17:25
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlRequest {

    private String url;

    private Map<String, String> headers;

    private String method;

    @JsonProperty("crawl_request_body")
    private Map<String, String> crawlRequestBody;

    @JsonProperty("crawlQueryParam")
    private Map<String, String> crawlQueryParam;
}
