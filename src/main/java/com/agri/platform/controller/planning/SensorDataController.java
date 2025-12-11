package com.agri.platform.controller.planning;

import com.agri.platform.entity.planning.SensorData;
import com.agri.platform.service.planning.SensorDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sensor-data")
public class SensorDataController {
    @Autowired
    private SensorDataService sensorDataService;

    // 实时数据查询（按土地ID）
    // 访问：GET http://localhost:8080/sensor-data/realtime/1（1是土地ID）
    @GetMapping("/realtime/{landId}")
    public SensorData getRealTime(@PathVariable Long landId) {
        return sensorDataService.getRealTimeData(landId);
    }

    // 历史趋势查询（按土地ID+时间范围）
    // 访问：GET http://localhost:8080/sensor-data/history/1?startTime=2024-01-01 00:00:00&endTime=2024-01-02 00:00:00
    @GetMapping("/history/{landId}")
    public List<SensorData> getHistory(
            @PathVariable Long landId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime
    ) {
        return sensorDataService.getHistoryData(landId, startTime, endTime);
    }

    // 模拟传感器上报数据（测试用）
    // 访问：POST http://localhost:8080/sensor-data/report
    @PostMapping("/report")
    public String reportData(@RequestBody SensorData data) {
        boolean success = sensorDataService.reportSensorData(data);
        return success ? "传感器数据上报成功！" : "上报失败！";
    }
}
