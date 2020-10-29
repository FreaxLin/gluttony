package top.interc.crawler.connect;

public interface HttpConnection {

    public HttpResult post(HttpRequest httpRequest);

    public HttpResult get(String url);
}
