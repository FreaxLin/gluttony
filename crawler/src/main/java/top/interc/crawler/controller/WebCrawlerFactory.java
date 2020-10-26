package top.interc.crawler.controller;

public interface WebCrawlerFactory<T extends WebCrawler> {

    T newInstance() throws Exception;
}
