// src/main/java/com/agri/platform/entity/analysis/EcommerceData.java
package com.agri.platform.entity.analysis;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EcommerceData {
    private Long dataId;
    private String cropType;
    private String platformName; // 电商平台名称
    private BigDecimal price; // 平台售价
    private Integer salesVolume; // 销售量
    private Integer commentCount; // 评论数
    private BigDecimal positiveRate; // 好评率
    private LocalDateTime crawlTime; // 数据抓取时间
}