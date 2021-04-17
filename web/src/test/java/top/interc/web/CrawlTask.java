package top.interc.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

public class CrawlTask {

    @Test
    public void test() throws IOException {
//        String url = "http://zzw.hsmdb.com/iwin_zzbweb-webapp/quote/v1/sort?en_hq_type_code=SS.ESA.M,SZ.ESA&sort_field_name=px_change_rate&data_count=10&sort_type=1&fields=prod_name,last_px,business_amount,current_amount,preclose_px,open_px,high_px,low_px,vol_ratio,business_balance,px_change,hq_type_code,px_change_rate&start_pos=0";
        String url = "http://zzw.hsmdb.com/iwin_zzbweb-webapp/quote/v1/block/query?prod_code=000001.SS";
        Document document = Jsoup.connect(String.format(url)).get();
        System.out.println(document.body().text());
    }
}
