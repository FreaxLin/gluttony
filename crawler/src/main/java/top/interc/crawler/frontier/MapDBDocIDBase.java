package top.interc.crawler.frontier;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import top.interc.crawler.controller.CrawlerConfig;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class MapDBDocIDBase implements DocIDService {

    private DB db;

    private BloomFilter<CharSequence> bloomFilter;

    private CrawlerConfig crawlerConfig;

    private static final String DATABASE_NAME = "DocIDs";

    private AtomicLong lastID = new AtomicLong(0);

    private ConcurrentMap<String, Long> docIDMap;

    public MapDBDocIDBase(CrawlerConfig crawlerConfig) {
        //对于1，表示如果支持的话使用mmap，也就是在64为操作系统开启，32位不开启，因为太小了；
        //对于2，是对使用mmap的优化，官方文档说快快快
        //对于3，这是针对使用mmap时，jvm所出现的bug所做的处理，实际上也还有其他问题，具体可参考官方文档和http://www.mapdb.org/blog/mmap_file_and_jvm_crash/
        //对于4，指的是jvm正常关闭时，将会关闭数据库，但是如果使用kill -9等强制关闭措施将导致mapdb的校验和出现问题，下次打开时将出现异常，虽然可以使用checksumHeaderBypass参数规避这个问题，但是官方还是建议使用事务来保证数据的安全性
        //对于5，开启事务，写的速度下降，但是数据安全了
        //对于6，数据库内部本质还是读写锁，因此更高的并发度设置在并发写的时候可以提供写性能
        DB db = DBMaker.fileDB(crawlerConfig.getCrawlStorageFolder())
                .fileMmapEnableIfSupported()
                .fileMmapPreclearDisable()
                .cleanerHackEnable()
                .closeOnJvmShutdown()
                .transactionEnable()
                .concurrencyScale(128)
                .make();
        this.db = db;

        this.docIDMap = db.treeMap(DATABASE_NAME)
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.LONG)
                .valuesOutsideNodesEnable()
                .createOrOpen();

        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 200000, 1E-7);
        this.bloomFilter = bloomFilter;
    }

    @Override
    public int getDocId(String url) {
        return 0;
    }

    @Override
    public int createDocId(String url) {

        return 0;
    }

    @Override
    public void addUrlAndDocId(String url, int docId) {

    }

    @Override
    public int getDocCount() {
        return 0;
    }

    @Override
    public boolean containId(String url) {
        return bloomFilter.mightContain(url);
    }
}
