package top.interc.model;

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

    private CrawlRule rule;

    public CrawlRule getRule() {
        return rule;
    }

    public void setRule(CrawlRule rule) {
        this.rule = rule;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getCrawlRequestBody() {
        return crawlRequestBody;
    }

    public void setCrawlRequestBody(Map<String, String> crawlRequestBody) {
        this.crawlRequestBody = crawlRequestBody;
    }

    public Map<String, String> getCrawlQueryParam() {
        return crawlQueryParam;
    }

    public void setCrawlQueryParam(Map<String, String> crawlQueryParam) {
        this.crawlQueryParam = crawlQueryParam;
    }
}
