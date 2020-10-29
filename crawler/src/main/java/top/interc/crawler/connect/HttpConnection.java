package top.interc.crawler.connect;

import java.io.IOException;

public interface HttpConnection {

    public HttpResult post(HttpRequest httpRequest);

    public HttpResult get(String url) throws IOException;
}
