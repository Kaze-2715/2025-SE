package com.agri.platform.service.planning;

import com.agri.platform.entity.planning.PestWarning;
import com.agri.platform.entity.planning.SensorData;
import com.agri.platform.mapper.planning.PestWarningMapper;
import com.agri.platform.mapper.planning.SensorDataMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PestWarningService {
    @Autowired
    private SensorDataMapper sensorDataMapper;
    @Autowired
    private PestWarningMapper pestWarningMapper;

    // 定时任务：每小时分析一次所有土地的环境数据（cron表达式：0 0 * * * ?）
    @Scheduled(cron = "0 0 * * * ?")
    public void autoPredictPestRisk() {
        // 模拟所有土地ID（实际应从种植计划中查询）
        List<Long> landIds = new ArrayList<>();
        landIds.add(1L); // 土地1
        landIds.add(2L); // 土地2

        // 遍历每块土地，分析风险
        for (Long landId : landIds) {
            SensorData latestData = sensorDataMapper.selectLatestByLandId(landId);
            if (latestData == null) continue; // 无数据则跳过

            // 核心：风险判断逻辑（高温高湿→红色预警）
            PestWarning warning = judgeRisk(latestData);
            if (warning != null) {
                pestWarningMapper.insert(warning);
                // 红色预警（等级3）触发报警
                if (warning.getWarningLevel() == 3) {
                    triggerRedAlarm(warning);
                }
            }
        }
    }

    // 风险判断逻辑（可自定义扩展）
    private PestWarning judgeRisk(SensorData data) {
        PestWarning warning = new PestWarning();
        warning.setLandId(data.getLandId());
        warning.setHandleStatus(0); // 0=未处理

        // 条件1：土壤温度>30℃ + 土壤湿度>80% → 小麦锈病（红色预警）
        boolean wheatRustRisk = data.getSoilTemperature().compareTo(new java.math.BigDecimal("30")) > 0
                && data.getSoilHumidity().compareTo(new java.math.BigDecimal("80")) > 0;

        // 条件2：空气温度>28℃ + 空气湿度>75% → 蚜虫（黄色预警）
        boolean aphidRisk = data.getAirTemperature().compareTo(new java.math.BigDecimal("28")) > 0
                && data.getAirHumidity().compareTo(new java.math.BigDecimal("75")) > 0;

        if (wheatRustRisk) {
            warning.setWarningType("小麦锈病");
            warning.setWarningLevel(3); // 红色预警
            warning.setWarningReason("土壤温度：" + data.getSoilTemperature() + "℃，土壤湿度：" + data.getSoilHumidity() + "%，符合高风险条件");
            warning.setCreateTime(LocalDateTime.now());
            warning.setUpdateTime(LocalDateTime.now());
            return warning;
        } else if (aphidRisk) {
            warning.setWarningType("蚜虫");
            warning.setWarningLevel(2); // 黄色预警
            warning.setWarningReason("空气温度：" + data.getAirTemperature() + "℃，空气湿度：" + data.getAirHumidity() + "%，符合中风险条件");
            warning.setCreateTime(LocalDateTime.now());
            warning.setUpdateTime(LocalDateTime.now());
            return warning;
        }

        return null; // 无风险
    }

    // 红色预警触发（实际可扩展为短信、APP推送）
    private void triggerRedAlarm(PestWarning warning) {
        System.out.println("【红色报警】土地ID：" + warning.getLandId() + "，预警类型：" + warning.getWarningType() + "，原因：" + warning.getWarningReason() + "，请立即处理！");
    }
}
