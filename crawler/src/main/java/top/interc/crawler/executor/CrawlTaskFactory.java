package top.interc.crawler.executor;

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

    public static CrawlTask build(String className, String url){

        try {
            Class clazz = Class.forName(className);
            Constructor<CrawlTask> constructor = clazz.getConstructor(String.class);
            return constructor.newInstance(url);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            return null;
        }


    }
}
