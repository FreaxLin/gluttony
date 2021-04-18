package top.interc.gluttony.web.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class AbstractCrawlJsonTask implements Runnable {

    private String url;

    @Override
    public void run() {
        String crawlUrl = start(url);

        while (crawlUrl != null){
            try {
                Document document = Jsoup.connect(crawlUrl).get();
                process(document);
                crawlUrl = getNextUrl(crawlUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        end();


    }

    public abstract void process(Document document);

    public abstract String getNextUrl(String url);

    public abstract String start(String url);

    public abstract void end();
}
