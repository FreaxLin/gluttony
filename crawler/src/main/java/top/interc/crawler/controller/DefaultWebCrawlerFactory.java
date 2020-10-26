package top.interc.crawler.controller;

public class DefaultWebCrawlerFactory<T extends WebCrawler> implements WebCrawlerFactory<T> {

    private final Class<T> clazz;

    DefaultWebCrawlerFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T newInstance() throws Exception {
        try {
            return clazz.newInstance();
        } catch (ReflectiveOperationException e) {
            throw e;
        }
    }
}
