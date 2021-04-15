package top.interc.gluttony.web.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 10:44
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class HikariPoolManager {

    private static Logger logger = LoggerFactory.getLogger(HikariPoolManager.class);

    private static HikariDataSource dataSource;

    private static HikariPoolManager hikariPoolManager;



    /**
     * 启动
     *
     * @throws IOException
     * @throws SQLException
     */
    public static DataSource init() throws IOException, SQLException {
        logger.info("连接池初始化Start!!!");

        String db_url = "jdbc:mysql://81.68.228.111:3306/crawl_data?characterEncoding=utf8&useSSL=false&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true";
        if (db_url == null || db_url.length() == 0) {
            logger.error("配置的数据库ip地址错误!");
            System.exit(0);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(db_url);
        config.setUsername("root");
        config.setPassword("B2IZ8lCOIt2dgvOu");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        // 设置连接超时为8小时
        config.setConnectionTimeout(8 * 60 * 60);
        dataSource = new HikariDataSource(config);

        logger.info("连接池初始化End!!!");
        return dataSource;
    }


}
