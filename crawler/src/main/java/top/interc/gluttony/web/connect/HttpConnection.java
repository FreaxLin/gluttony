package top.interc.gluttony.web.connect;

import java.io.IOException;

public interface HttpConnection {

    public HttpResult post(HttpRequest httpRequest) throws IOException;

    public HttpResult get(String url) throws IOException;
}
