package com.agri.platform.mapper.planning;

import org.apache.ibatis.annotations.Mapper;

import com.agri.platform.entity.planning.PlantingPlan;

import java.util.List;

@Mapper // 标识为MyBatis Mapper接口
public interface PlantingPlanMapper {
    // 自定义方法
    List<PlantingPlan> selectByFarmerId(Long farmerId);
    PlantingPlan selectById(Long id);
    int insert(PlantingPlan plan);
    int update(PlantingPlan plan);
    int deleteById(Long id);
}