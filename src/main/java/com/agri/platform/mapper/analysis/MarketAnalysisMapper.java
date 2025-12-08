// src/main/java/com/agri/platform/mapper/analysis/MarketAnalysisMapper.java
package com.agri.platform.mapper.analysis;

import com.agri.platform.entity.analysis.MarketAnalysis;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MarketAnalysisMapper {
    MarketAnalysis selectById(Long analysisId);
    List<MarketAnalysis> selectByCropType(String cropType);
    List<MarketAnalysis> selectLatestByCropType(String cropType);
    int insert(MarketAnalysis analysis);
    int update(MarketAnalysis analysis);
    int deleteById(Long analysisId);
}