package top.interc.crawler.controller;

public class CrawlerConfig {

    private String crawlStorageFolder;

    private boolean resumableCrawling = false;

    private int maxDepthOfCrawling = -1;

    private int maxPagesToFetch = -1;

    private String userAgentString = "";

    private int crawlerNumber = Runtime.getRuntime().availableProcessors() * 4;

    public String getCrawlStorageFolder() {
        return crawlStorageFolder;
    }

    public void setCrawlStorageFolder(String crawlStorageFolder) {
        this.crawlStorageFolder = crawlStorageFolder;
    }

    public boolean isResumableCrawling() {
        return resumableCrawling;
    }

    public void setResumableCrawling(boolean resumableCrawling) {
        this.resumableCrawling = resumableCrawling;
    }

    public int getMaxDepthOfCrawling() {
        return maxDepthOfCrawling;
    }

    public void setMaxDepthOfCrawling(int maxDepthOfCrawling) {
        this.maxDepthOfCrawling = maxDepthOfCrawling;
    }

    public int getMaxPagesToFetch() {
        return maxPagesToFetch;
    }

    public void setMaxPagesToFetch(int maxPagesToFetch) {
        this.maxPagesToFetch = maxPagesToFetch;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public int getCrawlerNumber() {
        return crawlerNumber;
    }

    public void setCrawlerNumber(int crawlerNumber) {
        this.crawlerNumber = crawlerNumber;
    }
}
