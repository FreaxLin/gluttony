package top.interc.gluttony.web.storage;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.IndexTreeList;
import org.mapdb.Serializer;
import top.interc.gluttony.web.controller.CrawlConfig;


public class PreCrawlUrlQueue<T> implements EmbeddedQueue<T>{

    private DB db;

    private top.interc.gluttony.web.controller.CrawlConfig CrawlConfig;

    private static final String DATABASE_NAME = "queue";

    private IndexTreeList<T> urlQueue;

    public PreCrawlUrlQueue(CrawlConfig CrawlConfig, Serializer<T> serializer) {
        //对于1，表示如果支持的话使用mmap，也就是在64为操作系统开启，32位不开启，因为太小了；
        //对于2，是对使用mmap的优化，官方文档说快快快
        //对于3，这是针对使用mmap时，jvm所出现的bug所做的处理，实际上也还有其他问题，具体可参考官方文档和http://www.mapdb.org/blog/mmap_file_and_jvm_crash/
        //对于4，指的是jvm正常关闭时，将会关闭数据库，但是如果使用kill -9等强制关闭措施将导致mapdb的校验和出现问题，下次打开时将出现异常，虽然可以使用checksumHeaderBypass参数规避这个问题，但是官方还是建议使用事务来保证数据的安全性
        //对于5，开启事务，写的速度下降，但是数据安全了
        //对于6，数据库内部本质还是读写锁，因此更高的并发度设置在并发写的时候可以提供写性能
        this.db = DBMaker.fileDB(CrawlConfig.getCrawlStorageFolder())
                .fileMmapEnableIfSupported()
                .fileMmapPreclearDisable()
                .cleanerHackEnable()
                .checksumHeaderBypass()
                .closeOnJvmShutdown()
//                .transactionEnable()
                .concurrencyScale(128)
                .make();

        this.urlQueue = this.db.indexTreeList(DATABASE_NAME, serializer).createOrOpen();
    }

    @Override
    public boolean put(T data) {
        this.urlQueue.add(data);
        this.db.commit();
        return true;
    }

    @Override
    public T getFirst() {
        return this.urlQueue.get(0);
    }

    @Override
    public T getLast() {
        return null;
    }

    @Override
    public T poll() {
        T t = this.urlQueue.remove(0);
        return t;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public int size() {
        return this.urlQueue.size();
    }
}
