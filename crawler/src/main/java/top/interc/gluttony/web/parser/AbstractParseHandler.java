package top.interc.gluttony.web.parser;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/6 17:30
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public abstract class AbstractParseHandler implements ParseHandler {

    @Override
    public ParseData parse(String raw, String contentType) {
        return null;
    }

    public abstract ParseData process(String raw, ParseDataType type);
}
