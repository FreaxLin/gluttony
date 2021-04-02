package top.interc.crawler.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：linweisen
 * @date ：Created in 2021/3/23 9:51
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlUncheckedExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(CrawlUncheckedExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("查询异常:", e);
    }
}
