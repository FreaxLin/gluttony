package top.interc.analysis;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HupuUserCityAnalyse {

    public static void main(String[] args) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setConnectionTimeout(10000);
        hikariDataSource.setPassword("123456");
        hikariDataSource.setUsername("root");
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/craw_data");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);
        List<Map<String, Object>> datas = jdbcTemplate.queryForList("SELECT city FROM craw_data.hupu_bxj_user where city != ''");
        JiebaSegmenter segmenter = new JiebaSegmenter();

//        WordDictionary.getInstance().init(Paths.get("conf"));
        Map<String, Integer> cache = new HashMap<>();
        for (Map<String, Object> data : datas){
            String city = data.get("city").toString();
            List<String> parse = segmenter.sentenceProcess(city);
            if (parse.size() > 0){
                Integer i = cache.getOrDefault(parse.get(0), 0);
                i++;
                cache.put(parse.get(0), i);
            }

        }
        TopK<CityCount> topK = new TopK<>(10);
        cache.forEach((k, v) -> {
            topK.add(new CityCount(k, v));
        });
        for (CityCount c : topK.sortedList()){
            System.out.println(c);
        }
    }
}
