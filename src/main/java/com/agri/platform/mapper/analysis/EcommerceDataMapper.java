// src/main/java/com/agri/platform/mapper/analysis/EcommerceDataMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.EcommerceData;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EcommerceDataMapper {
    EcommerceData selectById(Long dataId);
    List<EcommerceData> selectByCropType(String cropType);
    List<EcommerceData> selectByPlatform(String platformName);
    int insert(EcommerceData data);
    int batchInsert(List<EcommerceData> dataList);
    int deleteOlderThan(LocalDateTime time);
}