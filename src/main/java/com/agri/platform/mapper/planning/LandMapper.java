package com.agri.platform.mapper;

import com.agri.platform.entity.Land;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface LandMapper {
    // 标准CRUD方法
    Land selectById(Long landId);
    int insert(Land land);
    int update(Land land);
    int deleteById(Long landId);
    
    // 根据农场主ID查询所有地块（前端选择地块用）
    List<Land> selectByFarmerId(Long farmerId);

    // 根据地块ID查询地块信息（关联预警、传感器数据用）
    Land selectLandById(Long landId);
}