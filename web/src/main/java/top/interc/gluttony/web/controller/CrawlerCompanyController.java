package top.interc.gluttony.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.interc.gluttony.web.service.RsCompanyService;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/14 19:55
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
@Controller
@RequestMapping("/api")
public class CrawlerCompanyController {

    @Autowired
    private RsCompanyService companyService;

    @GetMapping("/financial")
    public boolean getFinancial(@RequestParam String code){
        return companyService.crawlCompanyFinancial(code);
    }
}
