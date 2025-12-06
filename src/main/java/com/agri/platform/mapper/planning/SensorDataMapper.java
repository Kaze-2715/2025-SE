package com.agri.platform.mapper.planning;

import org.apache.ibatis.annotations.Mapper;

import com.agri.platform.entity.planning.SensorData;

import java.util.Date;
import java.util.List;

@Mapper
public interface SensorDataMapper {
    // 自定义方法：查询某块土地的最新一条传感器数据
    SensorData selectLatestByLandId(Long landId);
    SensorData selectById(Long dataId);
    int insert(SensorData data);
    int update(SensorData data);
    int deleteById(Long dataId);

    // 自定义方法：按土地ID和时间范围查询历史数据
    List<SensorData> selectByLandIdAndTimeRange(Long landId, Date startTime, Date endTime);
}