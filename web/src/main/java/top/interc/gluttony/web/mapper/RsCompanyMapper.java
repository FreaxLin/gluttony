package top.interc.gluttony.web.mapper;

import top.interc.gluttony.web.model.RsCompany;

public interface RsCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RsCompany record);

    int insertSelective(RsCompany record);

    RsCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RsCompany record);

    int updateByPrimaryKey(RsCompany record);

    RsCompany selectByCode(String code);
}