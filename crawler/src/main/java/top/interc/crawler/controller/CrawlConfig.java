package top.interc.crawler.controller;

public class CrawlConfig {

    private String crawlStorageFolder;

    private boolean resumableCrawling = false;

    private int maxDepthOfCrawling = -1;

    private int maxPagesToFetch = -1;

    private String userAgentString = "";

    private int connectionTimeOut = 60000;

    private int socketTimeOut = 60000;

    private int crawlerNumber = Runtime.getRuntime().availableProcessors() * 4;

    private String cookiePolicy = "";

    private boolean includeHttpsPage = true;

    public boolean isIncludeHttpsPage() {
        return includeHttpsPage;
    }

    public void setIncludeHttpsPage(boolean includeHttpsPage) {
        this.includeHttpsPage = includeHttpsPage;
    }

    public String getCookiePolicy() {
        return cookiePolicy;
    }

    public void setCookiePolicy(String cookiePolicy) {
        this.cookiePolicy = cookiePolicy;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

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
