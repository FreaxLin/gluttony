package top.interc.crawler.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import top.interc.crawler.controller.CrawlerConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class ConfigRequestHandler implements Handler {

    private CrawlerConfig crawlConfig;

    public ConfigRequestHandler(CrawlerConfig crawlConfig) {
        this.crawlConfig = crawlConfig;
    }

    @Override
    public void execute(HttpServletRequest request, PrintWriter out) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode conf = mapper.createObjectNode();
        conf.put("Crawl storage folder", crawlConfig.getCrawlStorageFolder());
        conf.put("Resumable crawling", crawlConfig.isResumableCrawling());
        conf.put("Max depth of crawl", crawlConfig.getMaxDepthOfCrawling());
        conf.put("Max pages to fetch", crawlConfig.getMaxPagesToFetch());
        conf.put("User agent string", crawlConfig.getUserAgentString());
        conf.put("Include https pages", crawlConfig.isIncludeHttpsPages());
        conf.put("Include binary content", crawlConfig.isIncludeBinaryContentInCrawling());
        conf.put("Max connections per host", crawlConfig.getMaxConnectionsPerHost());
        conf.put("Max total connections", crawlConfig.getMaxTotalConnections());
        conf.put("Socket timeout", crawlConfig.getSocketTimeout());
        conf.put("Max total connections", crawlConfig.getMaxTotalConnections());
        conf.put("Max outgoing links to follow", crawlConfig.getMaxOutgoingLinksToFollow());
        conf.put("Max download size", crawlConfig.getMaxDownloadSize());
        conf.put("Should follow redirects?", crawlConfig.isFollowRedirects());
        conf.put("Proxy host", crawlConfig.getProxyHost());
        conf.put("Proxy port", crawlConfig.getProxyPort());
        conf.put("Proxy username", crawlConfig.getProxyUsername());
        conf.put("Thread monitoring delay", crawlConfig.getThreadMonitoringDelaySeconds());
        conf.put("Thread shutdown delay", crawlConfig.getThreadShutdownDelaySeconds());
        conf.put("Cleanup delay", crawlConfig.getCleanupDelaySeconds());
        conf.put("Cookie policy", crawlConfig.getCookiePolicy());
        conf.put("Respect nofollow", crawlConfig.isRespectNoFollow());
        conf.put("Respect noindex", crawlConfig.isRespectNoIndex());
        conf.put("Halt on error", crawlConfig.isHaltOnError());
        conf.put("Allow single level domain:", crawlConfig.isAllowSingleLevelDomain());
        conf.put("Batch read size", crawlConfig.getBatchReadSize());
        conf.put("Crawl storage folder", crawlConfig.getCrawlStorageFolder());
        out.println(conf);
        out.flush();

    }
}
