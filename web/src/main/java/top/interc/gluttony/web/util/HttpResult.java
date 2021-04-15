package top.interc.gluttony.web.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/14 15:28
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class HttpResult {

    private int code;

    private Map<String, String> headers = new HashMap<>();

    private String body;

    public void addHeader(String key, String value){
        this.headers.put(key, value);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
