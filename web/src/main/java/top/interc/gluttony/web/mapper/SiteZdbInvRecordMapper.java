package top.interc.gluttony.web.mapper;

import top.interc.gluttony.web.model.SiteZdbInvRecord;

public interface SiteZdbInvRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SiteZdbInvRecord record);

    int insertSelective(SiteZdbInvRecord record);

    SiteZdbInvRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SiteZdbInvRecord record);

    int updateByPrimaryKey(SiteZdbInvRecord record);
}