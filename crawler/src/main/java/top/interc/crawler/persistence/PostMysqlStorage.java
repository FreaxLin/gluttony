package top.interc.crawler.persistence;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 10:44
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class PostMysqlStorage {

    private JdbcTemplate jdbcTemplate;


    public PostMysqlStorage(DataSource source) {
        this.jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(source);
    }

    public int save(Object[] args){
        String sql = "insert into crawl_post(site, post, post_location, post_create_date, post_introduce, post_salary, create_date, update_date)" +
                "values (?, ?, ?, ?, ? , ?, now(), now()) ";
        return jdbcTemplate.update(sql, args);
    }


}
