package top.interc.gluttony.web.connect;

import java.util.HashMap;
import java.util.Map;

public class HttpResult {

    private int code;

    private String version;

    private String reasonPhrase;

    private Map<String, String> header = new HashMap<>();

    private String content;

    public void addHeader(String key, String value){
        this.header.put(key, value);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
