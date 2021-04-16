package top.interc.gluttony.web.mapper;

import top.interc.gluttony.web.model.SiteZdbPedailyCnInvestCompany;

import java.util.List;

public interface SiteZdbPedailyCnInvestCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SiteZdbPedailyCnInvestCompany record);

    int insertSelective(SiteZdbPedailyCnInvestCompany record);

    SiteZdbPedailyCnInvestCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SiteZdbPedailyCnInvestCompany record);

    int updateByPrimaryKey(SiteZdbPedailyCnInvestCompany record);

    List<SiteZdbPedailyCnInvestCompany> findAll();
}