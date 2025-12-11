package com.agri.platform.entity.planning;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PestWarning {
    private Long warningId;
    private Long landId;
    private String warningType; // 修改为String类型，与Service中的赋值一致
    private Integer warningLevel;
    private Integer handleStatus;
    private String description;
    private String warningReason; // 添加缺失的字段
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}