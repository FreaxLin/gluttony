package mapdb;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.IndexTreeList;
import top.interc.crawler.executor.CrawlTask;
import top.interc.crawler.executor.CrawlTaskSerializer;
import top.interc.crawler.executor.SimpleCrawlTask;


/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 19:51
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class MapDbTest {

    @Test
    public void test(){
        DB db = DBMaker.fileDB("test")
                .checksumHeaderBypass()
                .fileMmapEnableIfSupported()
                .fileMmapPreclearDisable()
                .cleanerHackEnable()
                .checksumHeaderBypass()
                .transactionEnable()
                .make();

        IndexTreeList<CrawlTask> s = db.indexTreeList("test", new CrawlTaskSerializer()).createOrOpen();
        CrawlTask task = new SimpleCrawlTask("www.bai.com");

        s.add(task);
        db.commit();
    }
}
