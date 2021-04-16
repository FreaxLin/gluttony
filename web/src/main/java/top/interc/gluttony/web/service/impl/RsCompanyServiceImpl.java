package top.interc.gluttony.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.interc.gluttony.web.mapper.RsCompanyMapper;
import top.interc.gluttony.web.model.RsCompany;
import top.interc.gluttony.web.service.RsCompanyService;
import top.interc.gluttony.web.util.HttpClient;
import top.interc.gluttony.web.util.HttpResult;
import top.interc.gluttony.web.util.JsonMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/14 19:59
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
@Service
public class RsCompanyServiceImpl implements RsCompanyService {

    private String url = "http://www.cninfo.com.cn/data/yellowpages/singleStockData?jsonpCallback=jQuery02690408090823675_1618384644897&scode=%s&sign=2&type=1&mergerMark=financeData&_=1618384644898";

    @Autowired
    private RsCompanyMapper rsCompanyMapper;

    @Override
    public boolean crawlCompanyFinancial(String code) {
        RsCompany company = rsCompanyMapper.selectByCode(code);
        if (company == null){
            company = new RsCompany();
            company.setCode(code);
            rsCompanyMapper.insertSelective(company);
        }
        HttpResult result = HttpClient.get(String.format(url, code), null);
        Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Matcher matcher = pattern.matcher(result.getBody());

        while (matcher.find()) {
            Map map = JsonMapper.getInstance().fromJson(matcher.group(0), Map.class);
            List<Map<String, Object>> profitsData = (List<Map<String, Object>>) map.get("profitsData");
            for (Map<String, Object> data : profitsData){

            }
            break;
        }
        return false;
    }
}
