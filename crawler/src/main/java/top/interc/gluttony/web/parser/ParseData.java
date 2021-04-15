package top.interc.gluttony.web.parser;


import java.util.Map;

public interface ParseData {

    public String getContent();

    public ParseDataType getType();

    public Map<String, String> getHeaders();

    public String getUrl();
}