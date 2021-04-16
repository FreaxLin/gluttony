package top.interc.gluttony.web.mapper;

import top.interc.gluttony.web.model.SiteZdbInvGroup;

public interface SiteZdbInvGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SiteZdbInvGroup record);

    int insertSelective(SiteZdbInvGroup record);

    SiteZdbInvGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SiteZdbInvGroup record);

    int updateByPrimaryKey(SiteZdbInvGroup record);
}