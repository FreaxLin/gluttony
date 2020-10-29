package top.interc.crawler.storage;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import top.interc.crawler.controller.CrawlerConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapDBDocIDBase implements DocIDService {

    private DB db;

    private BloomFilter<CharSequence> bloomFilter;

    private CrawlerConfig crawlerConfig;

    private static final String DATABASE_NAME = "DocIDs";

    private AtomicInteger lastID = new AtomicInteger(0);

    private ConcurrentMap<String, Integer> docIDMap;

    public MapDBDocIDBase(CrawlerConfig crawlerConfig) {

        String urlCrawFile = crawlerConfig.getCrawlStorageFolder() + "/id";

        DB db = DBMaker.fileDB(urlCrawFile)
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
                .valueSerializer(Serializer.INTEGER)
                .valuesOutsideNodesEnable()
                .createOrOpen();

        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 200000, 1E-7);
        int max = 0;
        //恢复布隆过滤器
        if (crawlerConfig.isResumableCrawling()){
            for (Map.Entry<String, Integer> entry : docIDMap.entrySet()){
                bloomFilter.put(entry.getKey());
                if (entry.getValue() > max){
                    max = entry.getValue();
                }
            }
            this.lastID = new AtomicInteger(max);
        }

        this.bloomFilter = bloomFilter;
    }

    @Override
    public int getDocId(String url) {
        return this.docIDMap.get(url);
    }

    @Override
    public int createDocId(String url) {
        return 0;
    }

    @Override
    public void addUrlAndDocId(String url, int docId) {
        this.docIDMap.put(url, docId);
    }

    @Override
    public int getDocCount() {
        return this.docIDMap.size();
    }

    @Override
    public boolean containId(String url) {
        return bloomFilter.mightContain(url);
    }
}
