// src/main/java/com/agri/platform/mapper/analysis/YieldPredictionMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.YieldPrediction;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface YieldPredictionMapper {
    YieldPrediction selectById(Long predictionId);
    List<YieldPrediction> selectByPlanId(Long planId);
    List<YieldPrediction> selectByFarmerId(Long farmerId);
    int insert(YieldPrediction prediction);
    int update(YieldPrediction prediction);
    int deleteById(Long predictionId);
}