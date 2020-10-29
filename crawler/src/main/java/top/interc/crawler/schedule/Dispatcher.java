package top.interc.crawler.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.interc.crawler.controller.CrawlerConfig;

import top.interc.crawler.storage.DocIDService;
import top.interc.crawler.storage.EmbeddedQueue;
import top.interc.crawler.storage.MapDBDocIDBase;
import top.interc.crawler.storage.UnCralwUrlQueues;
import top.interc.crawler.url.WebURL;

import java.util.List;

public class Dispatcher {

    protected static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    private static final String DATABASE_NAME = "PendingURLsDB";

    private static final int IN_PROCESS_RESCHEDULE_BATCH_SIZE = 100;

    private CrawlerConfig config;

    private EmbeddedQueue<WebURL> queues;

    private DocIDService docIDService;

    protected final Object waitingList = new Object();

    protected boolean isFinished = false;

    protected long scheduledPages;

    protected Counters counters;

    public Dispatcher(CrawlerConfig config) {
        this.config = config;
        this.queues = new UnCralwUrlQueues(config);
        this.docIDService = new MapDBDocIDBase(config);
    }


    public void schedule(WebURL url) {
        int maxPagesToFetch = config.getMaxPagesToFetch();

        if (maxPagesToFetch < 0 || scheduledPages < maxPagesToFetch) {
            queues.put(url);
            scheduledPages++;
            counters.increment(Counters.ReservedCounterNames.SCHEDULED_PAGES);
        }
    }

    public void getNextURLs(int max, List<WebURL> result) {
//        while (true) {
//            synchronized (mutex) {
//                if (isFinished) {
//                    return;
//                }
//                try {
//                    List<WebURL> curResults = workQueues.get(max);
//                    workQueues.delete(curResults.size());
//                    if (inProcessPages != null) {
//                        for (WebURL curPage : curResults) {
//                            inProcessPages.put(curPage);
//                        }
//                    }
//                    result.addAll(curResults);
//                } catch (DatabaseException e) {
//                    logger.error("Error while getting next urls", e);
//                }
//
//                if (result.size() > 0) {
//                    return;
//                }
//            }
//
//            try {
//                synchronized (waitingList) {
//                    waitingList.wait();
//                }
//            } catch (InterruptedException ignored) {
//                // Do nothing
//            }
//            if (isFinished) {
//                return;
//            }
//        }
    }

    public void setProcessed(WebURL webURL) {
        counters.increment(Counters.ReservedCounterNames.PROCESSED_PAGES);
//        if (inProcessPages != null) {
//            if (!inProcessPages.removeURL(webURL)) {
//                logger.warn("Could not remove: {} from list of processed pages.", webURL.getURL());
//            }
//        }
    }

    public long getQueueLength() {
        return queues.size();
    }

//    public long getNumberOfAssignedPages() {
//        if (inProcessPages != null) {
//            return inProcessPages.getLength();
//        } else {
//            return 0;
//        }
//    }

    public long getNumberOfProcessedPages() {
        return counters.getValue(Counters.ReservedCounterNames.PROCESSED_PAGES);
    }

    public long getNumberOfScheduledPages() {
        return counters.getValue(Counters.ReservedCounterNames.SCHEDULED_PAGES);
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void close() {
//        .close();
//        counters.close();
//        if (inProcessPages != null) {
//            inProcessPages.close();
//        }
    }

    public void finish() {
        isFinished = true;
        synchronized (waitingList) {
            waitingList.notifyAll();
        }
    }
}
