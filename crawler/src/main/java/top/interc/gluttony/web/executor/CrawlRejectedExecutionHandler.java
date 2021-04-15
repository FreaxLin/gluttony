package top.interc.gluttony.web.executor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 11:38
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r);
    }
}
