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

package top.interc.crawler.controller;


import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.interc.crawler.connect.HttpConnection;
import top.interc.crawler.monitor.ConfigRequestHandler;
import top.interc.crawler.monitor.CrawInfoRequestHandler;
import top.interc.crawler.monitor.JVMInfoRequestHandler;
import top.interc.crawler.monitor.RequestHandler;
import top.interc.crawler.parser.Parser;
import top.interc.crawler.robotstxt.RobotstxtConfig;
import top.interc.crawler.robotstxt.RobotstxtServer;
import top.interc.crawler.schedule.Dispatcher;
import top.interc.crawler.storage.DocIDService;
import top.interc.crawler.storage.MapDBDocIDBase;
import top.interc.crawler.url.URLCanonicalizer;
import top.interc.crawler.url.WebURL;
import top.interc.crawler.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class CrawlController {

    static final Logger logger = LoggerFactory.getLogger(CrawlController.class);

    private final CrawlerConfig config;

    protected Object customData;

    protected boolean finished;

    private Throwable error;

    private Server server;

    private Dispatcher dispatcher;

    protected boolean shuttingDown;

    protected RobotstxtServer robotstxtServer;

    protected DocIDService docIdServer;

    private HttpConnection httpConnection;

    protected final Object waitingLock = new Object();

    protected Parser parser;


    public CrawlController(CrawlerConfig config) throws Exception {
        config.validate();
        this.config = config;

        File folder = new File(config.getCrawlStorageFolder());
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                logger.info("Created folder: " + folder.getAbsolutePath());
            } else {
                throw new Exception(
                    "couldn't create the storage folder: " + folder.getAbsolutePath() +
                    " does it already exist ?");
            }
        }

        boolean resumable = config.isResumableCrawling();

        if (!resumable) {
            Util.cleanFolder(config.getCrawlStorageFolder());
            logger.info("Deleted contents of: " + config.getCrawlStorageFolder() +
                    " ( as you have configured resumable crawling to false )");
        }

        URLCanonicalizer.setHaltOnError(config.isHaltOnError());

        server = new Server(config.getHttpServerPort());
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.addHander("/config", new ConfigRequestHandler(config));
        requestHandler.addHander("/jvm", new JVMInfoRequestHandler());
        requestHandler.addHander("/static", new CrawInfoRequestHandler(docIdServer));
        server.setHandler(requestHandler);

        server.start();
        server.join();


        docIdServer = new MapDBDocIDBase(config);

        this.parser = parser == null ? new Parser(config) : parser;
//        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
//        this.robotstxtServer = new RobotstxtServer(robotstxtConfig);

        finished = false;
        shuttingDown = false;

        robotstxtServer.setCrawlConfig(config);
    }



    /**
     * Start the crawling session and wait for it to finish.
     *
     * @param crawlerFactory
     *            factory to create crawlers on demand for each thread
     * @param numberOfCrawlers
     *            the number of concurrent threads that will be contributing in
     *            this crawling session.
     * @param <T> Your class extending WebCrawler
     */
    public <T extends WebCrawler> void start(WebCrawlerFactory<T> crawlerFactory,
                                             int numberOfCrawlers) {
        this.start(crawlerFactory, numberOfCrawlers, true);
    }



    protected <T extends WebCrawler> void start(final WebCrawlerFactory<T> crawlerFactory,
                                                final int numberOfCrawlers, boolean isBlocking) {
        try {
            finished = false;
            setError(null);
            final List<Thread> threads = new ArrayList<>();

            final List<T> crawlers = new ArrayList<>();

            for (int i = 1; i <= numberOfCrawlers; i++) {
                T crawler = crawlerFactory.newInstance();
                Thread thread = new Thread(crawler, "Crawler " + i);
                crawler.setThread(thread);
                crawler.init(i, this);
                thread.start();
                crawlers.add(crawler);
                threads.add(thread);
                logger.info("Crawler {} started", i);
            }

            final CrawlController controller = this;
//            Thread monitorThread = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        synchronized (waitingLock) {
//
//                            while (true) {
//                                sleep(config.getThreadMonitoringDelaySeconds());
//                                boolean someoneIsWorking = false;
//                                for (int i = 0; i < threads.size(); i++) {
//                                    Thread thread = threads.get(i);
//                                    if (!thread.isAlive()) {
//                                        if (!shuttingDown && !config.isHaltOnError()) {
//                                            logger.info("Thread {} was dead, I'll recreate it", i);
//                                            T crawler = crawlerFactory.newInstance();
//                                            thread = new Thread(crawler, "Crawler " + (i + 1));
//                                            threads.remove(i);
//                                            threads.add(i, thread);
//                                            crawler.setThread(thread);
//                                            crawler.init(i + 1, controller);
//                                            thread.start();
//                                            crawlers.remove(i);
//                                            crawlers.add(i, crawler);
//                                        }
//                                    } else if (crawlers.get(i).isNotWaitingForNewURLs()) {
//                                        someoneIsWorking = true;
//                                    }
//                                    Throwable t = crawlers.get(i).getError();
//                                    if (t != null && config.isHaltOnError()) {
//                                        throw new RuntimeException(
//                                                "error on thread [" + threads.get(i).getName() + "]", t);
//                                    }
//                                }
//                                boolean shutOnEmpty = config.isShutdownOnEmptyQueue();
//                                if (!someoneIsWorking && shutOnEmpty) {
//                                    // Make sure again that none of the threads
//                                    // are
//                                    // alive.
//                                    logger.info(
//                                        "It looks like no thread is working, waiting for " +
//                                         config.getThreadShutdownDelaySeconds() +
//                                         " seconds to make sure...");
//                                    sleep(config.getThreadShutdownDelaySeconds());
//
//                                    someoneIsWorking = false;
//                                    for (int i = 0; i < threads.size(); i++) {
//                                        Thread thread = threads.get(i);
//                                        if (thread.isAlive() &&
//                                            crawlers.get(i).isNotWaitingForNewURLs()) {
//                                            someoneIsWorking = true;
//                                        }
//                                    }
//                                    if (!someoneIsWorking) {
//                                        if (!shuttingDown) {
//                                            long queueLength = frontier.getQueueLength();
//                                            if (queueLength > 0) {
//                                                continue;
//                                            }
//                                            logger.info(
//                                                "No thread is working and no more URLs are in " +
//                                                "queue waiting for another " +
//                                                config.getThreadShutdownDelaySeconds() +
//                                                " seconds to make sure...");
//                                            sleep(config.getThreadShutdownDelaySeconds());
//                                            queueLength = frontier.getQueueLength();
//                                            if (queueLength > 0) {
//                                                continue;
//                                            }
//                                        }
//
//                                        logger.info(
//                                            "All of the crawlers are stopped. Finishing the " +
//                                            "process...");
//                                        // At this step, frontier notifies the threads that were
//                                        // waiting for new URLs and they should stop
//                                        frontier.finish();
//                                        for (T crawler : crawlers) {
//                                            crawler.onBeforeExit();
////                                            crawlersLocalData.add(crawler.getMyLocalData());
//                                        }
//
//                                        logger.info(
//                                            "Waiting for " + config.getCleanupDelaySeconds() +
//                                            " seconds before final clean up...");
//                                        sleep(config.getCleanupDelaySeconds());
//
//                                        frontier.close();
////                                        docIdServer.close();
//                                        pageFetcher.shutDown();
//
//                                        finished = true;
//                                        waitingLock.notifyAll();
////                                        env.close();
//
//                                        return;
//                                    }
//                                }
//                            }
//                        }
//                    } catch (Throwable e) {
//                        if (config.isHaltOnError()) {
//                            setError(e);
//                            synchronized (waitingLock) {
////                                frontier.finish();
////                                frontier.close();
////                                docIdServer.close();
//                                pageFetcher.shutDown();
//                                waitingLock.notifyAll();
////                                env.close();
//                            }
//                        } else {
//                            logger.error("Unexpected Error", e);
//                        }
//                    }
//                }
//
//            });

//            monitorThread.start();

            if (isBlocking) {
                waitUntilFinish();
            }

        } catch (Exception e) {
            if (config.isHaltOnError()) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException)e;
                } else {
                    throw new RuntimeException("error running the monitor thread", e);
                }
            } else {
                logger.error("Error happened", e);
            }
        }
    }

    /**
     * Wait until this crawling session finishes.
     */
    public void waitUntilFinish() {
        while (!finished) {
            synchronized (waitingLock) {
                if (config.isHaltOnError()) {
                    Throwable t = getError();
                    if (t != null && config.isHaltOnError()) {
                        if (t instanceof RuntimeException) {
                            throw (RuntimeException)t;
                        } else if (t instanceof Error) {
                            throw (Error)t;
                        } else {
                            throw new RuntimeException("error on monitor thread", t);
                        }
                    }
                }
                if (finished) {
                    return;
                }
                try {
                    waitingLock.wait();
                } catch (InterruptedException e) {
                    logger.error("Error occurred", e);
                }
            }
        }
    }

    protected static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {
            // Do nothing
        }
    }


    public void addSeed(String pageUrl) throws IOException, InterruptedException {
        addSeed(pageUrl, -1);
    }


    public void addSeed(String pageUrl, int docId) throws IOException, InterruptedException {
        String canonicalUrl = URLCanonicalizer.getCanonicalURL(pageUrl);
        if (canonicalUrl == null) {
            logger.error("Invalid seed URL: {}", pageUrl);
        } else {
            if (docId < 0) {
                docId = docIdServer.getDocId(canonicalUrl);
                if (docId > 0) {
                    logger.trace("This URL is already seen.");
                    return;
                }
                docId = docIdServer.createDocId(canonicalUrl);
            } else {
                try {
                    docIdServer.addUrlAndDocId(canonicalUrl, docId);
                } catch (RuntimeException e) {
                    if (config.isHaltOnError()) {
                        throw e;
                    } else {
                        logger.error("Could not add seed: {}", e.getMessage());
                    }
                }
            }

            WebURL webUrl = new WebURL();
            webUrl.setURL(canonicalUrl);
            webUrl.setDocid(docId);
            webUrl.setDepth((short) 0);
            if (robotstxtServer.allows(webUrl)) {
                dispatcher.schedule(webUrl);
            } else {
                // using the WARN level here, as the user specifically asked to add this seed
                logger.warn("Robots.txt does not allow this seed: {}", pageUrl);
            }
        }
    }

    /**
     * This function can called to assign a specific document id to a url. This
     * feature is useful when you have had a previous crawl and have stored the
     * Urls and their associated document ids and want to have a new crawl which
     * is aware of the previously seen Urls and won't re-crawl them.
     *
     * Note that if you add three seen Urls with document ids 1,2, and 7. Then
     * the next URL that is found during the crawl will get a doc id of 8. Also
     * you need to ensure to add seen Urls in increasing order of document ids.
     *
     * @param url
     *            the URL of the page
     * @param docId
     *            the document id that you want to be assigned to this URL.
     * @throws UnsupportedEncodingException
     *
     */
    public void addSeenUrl(String url, int docId) throws UnsupportedEncodingException {
        String canonicalUrl = URLCanonicalizer.getCanonicalURL(url);
        if (canonicalUrl == null) {
            logger.error("Invalid Url: {} (can't cannonicalize it!)", url);
        } else {
            try {
                docIdServer.addUrlAndDocId(canonicalUrl, docId);
            } catch (RuntimeException e) {
                if (config.isHaltOnError()) {
                    throw e;
                } else {
                    logger.error("Could not add seen url: {}", e.getMessage());
                }
            }
        }
    }


    public boolean isFinished() {
        return this.finished;
    }

    public boolean isShuttingDown() {
        return shuttingDown;
    }

    /**
     * Set the current crawling session set to 'shutdown'. Crawler threads
     * monitor the shutdown flag and when it is set to true, they will no longer
     * process new pages.
     */
    public void shutdown() {
        logger.info("Shutting down...");
        this.shuttingDown = true;
        dispatcher.close();
    }

    public CrawlerConfig getConfig() {
        return config;
    }

    protected synchronized Throwable getError() {
        return error;
    }

    private synchronized void setError(Throwable e) {
        this.error = e;
    }

    public RobotstxtServer getRobotstxtServer() {
        return robotstxtServer;
    }

    public void setRobotstxtServer(RobotstxtServer robotstxtServer) {
        this.robotstxtServer = robotstxtServer;
    }

    public DocIDService getDocIdServer() {
        return docIdServer;
    }

    public void setDocIdServer(DocIDService docIdServer) {
        this.docIdServer = docIdServer;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public HttpConnection getHttpConnection() {
        return httpConnection;
    }

    public void setHttpConnection(HttpConnection httpConnection) {
        this.httpConnection = httpConnection;
    }

    public interface WebCrawlerFactory<T extends WebCrawler> {
        T newInstance() throws Exception;
    }
}
