package top.interc.gluttony.web.mapper;

import top.interc.gluttony.web.model.RsListedCompanyChina;

import java.util.List;

public interface RsListedCompanyChinaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RsListedCompanyChina record);

    int insertSelective(RsListedCompanyChina record);

    RsListedCompanyChina selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RsListedCompanyChina record);

    int updateByPrimaryKey(RsListedCompanyChina record);

    List<RsListedCompanyChina> findAll(Integer id);
}