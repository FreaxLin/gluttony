package mapdb;


import org.junit.Test;
import org.mapdb.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 9:55
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class TestSerializers {

    @Test
    public void run() throws IOException {
        TestBean bean = new TestBean(2);
        //#a
        DB db = DBMaker
                .fileDB("file.db")
                .fileMmapEnable()
                .make();
        ConcurrentMap<String,TestBean> map = db
                .hashMap("map", Serializer.STRING, new TestBeanSerializer())
                .createOrOpen();
        System.out.println(map.get("something").getId());

        db.close();
        //#z
        //cleanup, not part of example
        new File("file.db").delete();
    }



}
