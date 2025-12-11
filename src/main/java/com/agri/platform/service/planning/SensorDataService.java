package com.agri.platform.service.planning;

import com.agri.platform.entity.planning.SensorData;
import com.agri.platform.mapper.planning.SensorDataMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class SensorDataService {
    @Autowired
    private SensorDataMapper sensorDataMapper;

    // 获取某块土地的实时数据（最新一条）
    public SensorData getRealTimeData(Long landId) {
        return sensorDataMapper.selectLatestByLandId(landId);
    }

    // 获取历史趋势数据（按时间范围）
    public List<SensorData> getHistoryData(Long landId, Date startTime, Date endTime) {
        return sensorDataMapper.selectByLandIdAndTimeRange(landId, startTime, endTime);
    }

    // 模拟传感器数据上报（实际项目中由硬件推送，这里用于测试）
    public boolean reportSensorData(SensorData data) {
        return sensorDataMapper.insert(data) > 0;
    }
}
