/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.interc.crawler.frontier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.interc.crawler.storage.UnCralwUrlQueues;
import top.interc.crawler.url.WebURL;
import top.interc.crawler.controller.CrawlerConfig;

import java.util.List;


public class Frontier {
    protected static final Logger logger = LoggerFactory.getLogger(Frontier.class);

    private static final String DATABASE_NAME = "PendingURLsDB";

    private static final int IN_PROCESS_RESCHEDULE_BATCH_SIZE = 100;

    private CrawlerConfig config;

    private UnCralwUrlQueues queues;

    protected final Object mutex = new Object();
    protected final Object waitingList = new Object();

    protected boolean isFinished = false;

    protected long scheduledPages;

    protected Counters counters;

    public Frontier(CrawlerConfig config) {
        this.config = config;
        UnCralwUrlQueues unCralwUrlQueues = new UnCralwUrlQueues(config);
    }

    public void scheduleAll(List<WebURL> urls) {

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
