package top.interc.gluttony.web.mapper;

import org.apache.ibatis.annotations.Param;
import top.interc.gluttony.web.model.SiteZdbPedailyCn;

import java.util.List;

public interface SiteZdbPedailyCnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SiteZdbPedailyCn record);

    int insertSelective(SiteZdbPedailyCn record);

    SiteZdbPedailyCn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SiteZdbPedailyCn record);

    int updateByPrimaryKey(SiteZdbPedailyCn record);

    List<SiteZdbPedailyCn> selectAll(@Param("id") Integer id);
}