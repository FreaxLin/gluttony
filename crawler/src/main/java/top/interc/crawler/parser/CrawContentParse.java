package top.interc.crawler.parser;

import top.interc.crawler.controller.Page;
import top.interc.crawler.exceptions.ParseException;

public interface CrawContentParse {

    ParseData parse(Page page, String contextURL) throws ParseException;
}
