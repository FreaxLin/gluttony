package top.interc.crawler.persistence;

import java.util.List;
import java.util.Map;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 10:52
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public interface CrawlDataPersistence {

    int persist(List<Map<String, Object>> dataList);
}
