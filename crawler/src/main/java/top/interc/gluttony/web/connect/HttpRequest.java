package top.interc.gluttony.web.connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    private String url;

    private Map<String, String> headerMap = new HashMap<>();

    private List<PostParam> postParamList;

    public void addParam(String key, String value){
        if (postParamList == null){
            postParamList = new ArrayList<>();
        }
        postParamList.add(new PostParam(key, value));
    }

    public List<PostParam> getPostParamList() {
        return postParamList;
    }

    public void setPostParamList(List<PostParam> postParamList) {
        this.postParamList = postParamList;
    }

    public void addHead(String key, String value){
        this.headerMap.put(key, value);
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class PostParam{
        private String key;

        private String value;

        public PostParam(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
