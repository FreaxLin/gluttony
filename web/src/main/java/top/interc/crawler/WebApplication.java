package top.interc.crawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/8 17:30
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
@SpringBootApplication
@MapperScan("top.interc.crawler.mapper")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }
}
