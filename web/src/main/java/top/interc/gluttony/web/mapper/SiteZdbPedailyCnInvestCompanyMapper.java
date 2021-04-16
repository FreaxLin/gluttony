package top.interc.gluttony.web.mapper;

import top.interc.gluttony.web.model.SiteZdbPedailyCnInvestCompany;

public interface SiteZdbPedailyCnInvestCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SiteZdbPedailyCnInvestCompany record);

    int insertSelective(SiteZdbPedailyCnInvestCompany record);

    SiteZdbPedailyCnInvestCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SiteZdbPedailyCnInvestCompany record);

    int updateByPrimaryKey(SiteZdbPedailyCnInvestCompany record);
}