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

import org.apache.http.HttpStatus;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.interc.crawler.connect.HttpConnection;
import top.interc.crawler.connect.HttpResult;
import top.interc.crawler.exceptions.ContentFetchException;
import top.interc.crawler.exceptions.PageBiggerThanMaxSizeException;
import top.interc.crawler.exceptions.ParseException;
import top.interc.crawler.parser.HtmlParseData;
import top.interc.crawler.parser.NotAllowedContentException;
import top.interc.crawler.parser.ParseData;
import top.interc.crawler.parser.Parser;
import top.interc.crawler.robotstxt.RobotstxtServer;
import top.interc.crawler.schedule.Dispatcher;
import top.interc.crawler.storage.DocIDService;
import top.interc.crawler.url.WebURL;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class WebCrawler implements Runnable {

    protected static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

    protected int myId;

    protected CrawlController myController;

    private Thread myThread;

    private Parser parser;

    private RobotstxtServer robotstxtServer;

    private HttpConnection httpConnection;

    private Dispatcher dispatcher;

    private DocIDService docIDService;

    private boolean isWaitingForNewURLs;

    private Throwable error;

    private int batchReadSize;

    /**
     * Initializes the current instance of the crawler
     *
     * @param id
     *            the id of this crawler instance
     * @param crawlController
     *            the controller that manages this crawling session
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void init(int id, CrawlController crawlController)
        throws InstantiationException, IllegalAccessException {
        this.myId = id;
        this.httpConnection = crawlController.getHttpConnection();
        this.robotstxtServer = crawlController.getRobotstxtServer();
        this.docIDService = crawlController.getDocIdServer();
        this.dispatcher = crawlController.getDispatcher();
        this.parser = crawlController.getParser();
        this.myController = crawlController;
        this.isWaitingForNewURLs = false;
        this.batchReadSize = crawlController.getConfig().getBatchReadSize();
    }

    /**
     * Get the id of the current crawler instance
     *
     * @return the id of the current crawler instance
     */
    public int getMyId() {
        return myId;
    }

    public CrawlController getMyController() {
        return myController;
    }

    /**
     * This function is called just before starting the crawl by this crawler
     * instance. It can be used for setting up the data structures or
     * initializations needed by this crawler instance.
     */
    public void onStart() {
        // Do nothing by default
        // Sub-classed can override this to add their custom functionality
    }

    /**
     * This function is called just before the termination of the current
     * crawler instance. It can be used for persisting in-memory data or other
     * finalization tasks.
     */
    public void onBeforeExit() {
        // Do nothing by default
        // Sub-classed can override this to add their custom functionality
    }


    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        // Do nothing by default
        // Sub-classed can override this to add their custom functionality
    }


    protected WebURL handleUrlBeforeProcess(WebURL curURL) {
        return curURL;
    }


    protected void onPageBiggerThanMaxSize(String urlStr, long pageSize) {
        logger.warn("Skipping a URL: {} which was bigger ( {} ) than max allowed size", urlStr,
                    pageSize);
    }


    protected void onRedirectedStatusCode(Page page) {
        //Subclasses can override this to add their custom functionality
    }

    /**
     * Emitted when the crawler is redirected to an invalid Location.
     * @param page
     */
    protected void onRedirectedToInvalidUrl(Page page) {
        logger.warn("Unexpected error, URL: {} is redirected to NOTHING",
            page.url.getURL());
    }


    protected void onUnexpectedStatusCode(String urlStr, int statusCode, String contentType,
                                          String description) {
        logger.warn("Skipping URL: {}, StatusCode: {}, {}, {}", urlStr, statusCode, contentType,
                    description);
        // Do nothing by default (except basic logging)
        // Sub-classed can override this to add their custom functionality
    }

    /**
     * This function is called if the content of a url could not be fetched.
     *
     * @param webUrl URL which content failed to be fetched
     *
     * @deprecated use {@link #onContentFetchError(Page)}
     */
    @Deprecated
    protected void onContentFetchError(WebURL webUrl) {
        logger.warn("Can't fetch content of: {}", webUrl.getURL());
        // Do nothing by default (except basic logging)
        // Sub-classed can override this to add their custom functionality
    }

    /**
     * This function is called if the content of a url could not be fetched.
     *
     * @param page Partial page object
     */
    protected void onContentFetchError(Page page) {
        logger.warn("Can't fetch content of: {}", page.getWebURL().getURL());
        // Do nothing by default (except basic logging)
        // Sub-classed can override this to add their custom functionality
    }

    /**
     * This function is called when a unhandled exception was encountered during fetching
     *
     * @param webUrl URL where a unhandled exception occured
     */
    protected void onUnhandledException(WebURL webUrl, Throwable e) {
        if (myController.getConfig().isHaltOnError() && !(e instanceof IOException)) {
            throw new RuntimeException("unhandled exception", e);
        } else {
            String urlStr = (webUrl == null ? "NULL" : webUrl.getURL());
            logger.warn("Unhandled exception while fetching {}: {}", urlStr, e.getMessage());
            logger.info("Stacktrace: ", e);
            // Do nothing by default (except basic logging)
            // Sub-classed can override this to add their custom functionality
        }
    }


    protected void onParseError(WebURL webUrl, ParseException e) throws ParseException {
        onParseError(webUrl);
    }


    @Deprecated
    protected void onParseError(WebURL webUrl) {
        logger.warn("Parsing error of: {}", webUrl.getURL());
        // Do nothing by default (Except logging)
        // Sub-classed can override this to add their custom functionality
    }


    public Object getMyLocalData() {
        return null;
    }

    @Override
    public void run() {
        try {
            onStart();
            setError(null);
            boolean halt = false;
            while (!halt) {
                List<WebURL> assignedURLs = new ArrayList<>(batchReadSize);
                isWaitingForNewURLs = true;
                dispatcher.getNextURLs(batchReadSize, assignedURLs);
                isWaitingForNewURLs = false;
                if (assignedURLs.isEmpty()) {
                    if (dispatcher.isFinished()) {
                        return;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        logger.error("Error occurred", e);
                    }
                } else {
                    for (WebURL curURL : assignedURLs) {
                        if (myController.isShuttingDown()) {
                            logger.info("Exiting because of controller shutdown.");
                            return;
                        }
                        if (curURL != null) {
                            curURL = handleUrlBeforeProcess(curURL);
                            processPage(curURL);
                            dispatcher.setProcessed(curURL);
                        }
                    }
                }
                if (myController.getConfig().isHaltOnError() && myController.getError() != null) {
                    halt = true;
                    logger.info("halting because an error has occurred on another thread");
                }
            }
        } catch (Throwable t) {
            setError(t);
        }
    }


    public boolean shouldVisit(Page referringPage, WebURL url) {
        if (myController.getConfig().isRespectNoFollow()) {
            return !((referringPage != null &&
                    referringPage.getContentType() != null &&
                    referringPage.getContentType().contains("html") &&
                    ((HtmlParseData)referringPage.getParseData())
                        .getMetaTagValue("robots")
                        .contains("nofollow")) ||
                    url.getAttribute("rel").contains("nofollow"));
        }

        return true;
    }


    protected boolean shouldFollowLinksIn(WebURL url) {
        return true;
    }

    /**
     * Classes that extends WebCrawler should overwrite this function to process
     * the content of the fetched and parsed page.
     *
     * @param page
     *            the page object that is just fetched and parsed.
     */
    public void visit(Page page) {
        // Do nothing by default
        // Sub-classed should override this to add their custom functionality
    }

    private void processPage(WebURL curURL) throws IOException, InterruptedException, ParseException {



    }

    public Thread getThread() {
        return myThread;
    }

    public void setThread(Thread myThread) {
        this.myThread = myThread;
    }

    public boolean isNotWaitingForNewURLs() {
        return !isWaitingForNewURLs;
    }

    protected synchronized Throwable getError() {
        return error;
    }

    private synchronized void setError(Throwable error) {
        this.error = error;
    }
}
