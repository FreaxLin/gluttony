package top.interc.crawler.connect;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface HttpConnection {

    public HttpResult post(HttpRequest httpRequest) throws IOException;

    public HttpResult get(String url) throws IOException;
}
