package com.agri.platform.mapper;

import com.agri.platform.entity.Farmer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FarmerMapper {
    // 根据农场主ID查询信息（关联种植计划、地块用）
    Farmer selectByFarmerId(Long farmerId);
    
    // 标准CRUD方法
    Farmer selectById(Long id);
    int insert(Farmer farmer);
    int update(Farmer farmer);
    int deleteById(Long id);
}