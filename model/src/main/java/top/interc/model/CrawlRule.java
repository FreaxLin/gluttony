package top.interc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 17:29
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlRule {

    private String type;

    @JsonProperty("page_rule")
    private String pageRule;

    private String stop;

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPageRule() {
        return pageRule;
    }

    public void setPageRule(String pageRule) {
        this.pageRule = pageRule;
    }
}
