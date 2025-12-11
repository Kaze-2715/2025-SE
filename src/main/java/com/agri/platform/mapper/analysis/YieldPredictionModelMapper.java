// src/main/java/com/agri/platform/mapper/analysis/YieldPredictionModelMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.YieldPredictionModel;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface YieldPredictionModelMapper {
    YieldPredictionModel selectById(Long modelId);
    List<YieldPredictionModel> selectByCropType(String cropType);
    List<YieldPredictionModel> selectAll();
    int insert(YieldPredictionModel model);
    int update(YieldPredictionModel model);
    int deleteById(Long modelId);
}