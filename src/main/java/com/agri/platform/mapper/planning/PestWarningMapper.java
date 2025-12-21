package com.agri.platform.mapper.planning;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.agri.platform.entity.planning.PestWarning;

import java.util.List;

@Mapper
public interface PestWarningMapper {
    // 自定义方法：按处理状态查询预警
    @Select("SELECT * FROM pest_warning WHERE handle_status = #{status}")
    List<PestWarning> selectByHandleStatus(Integer handleStatus);

    @Select("SELECT * FROM pest_warning WHERE warning_id = #{warningId}")
    PestWarning selectById(Long warningId);

    int insert(PestWarning warning);

    int update(PestWarning warning);

    @Select("DELETE FROM pest_warning WHERE warning_id = #{warningId}")
    int deleteById(Long warningId);

    // 自定义方法：按预警等级查询预警
    @Select("SELECT * FROM pest_warning WHERE warning_level = #{level}")
    List<PestWarning> selectByWarningLevel(Integer warningLevel);
    
    // 查询所有预警数据
    @Select("SELECT * FROM pest_warning")
    List<PestWarning> selectAll();
}