package top.interc.crawler.executor;

import org.jetbrains.annotations.NotNull;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 11:52
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class CrawlTaskSerializer implements Serializer<CrawlTask> {

    @Override
    public void serialize(@NotNull DataOutput2 dataOutput2, @NotNull CrawlTask crawlTask) throws IOException {
        dataOutput2.writeChars(crawlTask.getUrl());
    }

    @Override
    public CrawlTask deserialize(@NotNull DataInput2 dataInput2, int i) throws IOException {
        if (i == 0){
            return null;
        }else{
            String url = dataInput2.readUTF();
            CrawlTask task = new CrawlTask(url);
            return task;
        }

    }
}
