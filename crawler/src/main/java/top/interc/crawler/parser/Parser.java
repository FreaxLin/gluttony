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

package top.interc.crawler.parser;


import org.apache.tika.language.LanguageIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.interc.crawler.controller.CrawlConfig;
import top.interc.crawler.controller.Page;
import top.interc.crawler.exceptions.ParseException;
import top.interc.crawler.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yasser Ganjisaffar
 */
public class Parser {

    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    private final CrawlConfig config;

    private Map<String, CrawContentParse> contentParseMap = new HashMap<>();

    public Parser(CrawlConfig config) {
        this.config = config;

    }

    public void parse(Page page, String contextURL) throws NotAllowedContentException, ParseException {
//        if (Util.hasBinaryContent(page.getContentType())) { // BINARY
//            BinaryParseData parseData = new BinaryParseData();
//            if (config.isIncludeBinaryContentInCrawling()) {
//                if (config.isProcessBinaryContentInCrawling()) {
//                    try {
//                        parseData.setBinaryContent(page.getContentData());
//                    } catch (Exception e) {
//                        if (config.isHaltOnError()) {
//                            throw new ParseException(e);
//                        } else {
//                            logger.error("Error parsing file", e);
//                        }
//                    }
//                } else {
//                    parseData.setHtml("<html></html>");
//                }
//                page.setParseData(parseData);
//                if (parseData.getHtml() == null) {
//                    throw new ParseException();
//                }
//                parseData.setOutgoingUrls(net.extractUrls(parseData.getHtml()));
//            } else {
//                throw new NotAllowedContentException();
//            }
//        } else if (Util.hasCssTextContent(page.getContentType())) { // text/css
//            try {
//                CssParseData parseData = new CssParseData();
//                if (page.getContentCharset() == null) {
//                    parseData.setTextContent(new String(page.getContentData()));
//                } else {
//                    parseData.setTextContent(
//                        new String(page.getContentData(), page.getContentCharset()));
//                }
//                parseData.setOutgoingUrls(page.getWebURL());
//                page.setParseData(parseData);
//            } catch (Exception e) {
//                logger.error("{}, while parsing css: {}", e.getMessage(), page.getWebURL().getURL());
//                throw new ParseException();
//            }
//        } else if (Util.hasPlainTextContent(page.getContentType())) { // plain Text
//            try {
//                TextParseData parseData = new TextParseData();
//                if (page.getContentCharset() == null) {
//                    parseData.setTextContent(new String(page.getContentData()));
//                } else {
//                    parseData.setTextContent(
//                        new String(page.getContentData(), page.getContentCharset()));
//                }
//                parseData.setOutgoingUrls(net.extractUrls(parseData.getTextContent()));
//                page.setParseData(parseData);
//            } catch (Exception e) {
//                logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL());
//                throw new ParseException(e);
//            }
//        } else { // isHTML
//
//            HtmlParseData parsedData = this.htmlContentParser.parse(page, contextURL);
//
//            if (page.getContentCharset() == null) {
//                page.setContentCharset(parsedData.getContentCharset());
//            }
//
//            // Please note that identifying language takes less than 10 milliseconds
//            LanguageIdentifier languageIdentifier = new LanguageIdentifier(parsedData.getText());
//            page.setLanguage(languageIdentifier.getLanguage());
//
//            page.setParseData(parsedData);
//
//        }
    }
}
