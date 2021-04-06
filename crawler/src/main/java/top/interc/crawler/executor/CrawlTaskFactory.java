package top.interc.crawler.executor;

import top.interc.crawler.controller.CrawlConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/6 16:56
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlTaskFactory {

    public static CrawlTask build(String className, String url, CrawlConfig config){

        try {
            Class clazz = Class.forName(className);
            Constructor<CrawlTask> constructor = clazz.getConstructor(String.class, CrawlConfig.class);
            return constructor.newInstance(url, config);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            return null;
        }


    }
}
