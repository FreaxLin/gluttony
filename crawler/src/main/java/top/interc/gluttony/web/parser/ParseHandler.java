package top.interc.gluttony.web.parser;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/6 17:29
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public interface ParseHandler {

    ParseData parse(String raw, String contentType);
}
