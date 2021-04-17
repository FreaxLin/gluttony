package top.interc.gluttony.web.mapper;

import top.interc.gluttony.web.model.RsListedCompanyDetail;

public interface RsListedCompanyDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RsListedCompanyDetail record);

    int insertSelective(RsListedCompanyDetail record);

    RsListedCompanyDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RsListedCompanyDetail record);

    int updateByPrimaryKey(RsListedCompanyDetail record);
}