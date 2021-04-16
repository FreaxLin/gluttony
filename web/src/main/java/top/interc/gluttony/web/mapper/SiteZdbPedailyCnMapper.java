package top.interc.gluttony.web.mapper;

import top.interc.gluttony.web.model.SiteZdbPedailyCn;

public interface SiteZdbPedailyCnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SiteZdbPedailyCn record);

    int insertSelective(SiteZdbPedailyCn record);

    SiteZdbPedailyCn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SiteZdbPedailyCn record);

    int updateByPrimaryKey(SiteZdbPedailyCn record);
}