package mapdb;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.IndexTreeList;
import org.mapdb.Serializer;
import top.interc.crawler.executor.CrawlTask;
import top.interc.crawler.executor.CrawlTaskSerializer;

import java.util.NavigableSet;

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
        CrawlTask task = new CrawlTask("www.bai.com");

        s.add(task);
        db.commit();
    }
}
