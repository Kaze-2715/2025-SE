package com.agri.platform.entity.planning;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SensorData {
    private Long dataId; // 数据ID
    private Long landId; // 土地ID
    private BigDecimal soilHumidity; // 土壤湿度（%）
    private BigDecimal soilTemperature; // 土壤温度（℃）
    private BigDecimal airTemperature; // 空气温度（℃）
    private BigDecimal airHumidity; // 空气湿度（%）
    private LocalDateTime collectTime; // 采集时间
}