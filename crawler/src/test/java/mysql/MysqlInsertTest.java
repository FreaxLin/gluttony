package mysql;

import org.junit.Test;
import top.interc.crawler.persistence.HikariPoolManager;
import top.interc.crawler.persistence.PostMysqlStorage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 14:58
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class MysqlInsertTest {

    @Test
    public void test() throws IOException, SQLException {
        PostMysqlStorage storage = new PostMysqlStorage(HikariPoolManager.init());
        Object[] args = new Object[]{"lieping", "java", "香洲", new Date(), "无", 10000};
        storage.save(args);
    }
}
