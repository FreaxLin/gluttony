import org.junit.Test;
import top.interc.crawler.controller.CrawlController;
import top.interc.crawler.controller.CrawlerConfig;
import top.interc.crawler.fetcher.PageFetcher;
import top.interc.crawler.robotstxt.RobotstxtConfig;
import top.interc.crawler.robotstxt.RobotstxtServer;


public class CrawlerTest {

    @Test
    public void test() throws Exception {
        String crawlStorageFolder = "/Users/sai/OpenSources/gluttony/data";
        int numberOfCrawlers = 7;

        CrawlerConfig config = new CrawlerConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        // Instantiate the controller for this crawl.
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // For each crawl, you need to add some seed urls. These are the first
        // URLs that are fetched and then the crawler starts following links
        // which are found in these pages
        controller.addSeed("https://www.ics.uci.edu/~lopes/");
        controller.addSeed("https://www.ics.uci.edu/~welling/");
        controller.addSeed("https://www.ics.uci.edu/");

        // The factory which creates instances of crawlers.
        CrawlController.WebCrawlerFactory<MyCrawler> factory = MyCrawler::new;

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        controller.start(factory, numberOfCrawlers);
    }
}
