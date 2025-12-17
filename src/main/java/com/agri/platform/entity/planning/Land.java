package com.agri.platform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Land {
    private Long landId;          // 地块ID（主键）
    private Long farmerId;        // 所属农场主ID（关联Farmer）
    private String landName;      // 地块名称（如：1号地块、2号地块）
    private BigDecimal area;      // 地块面积（亩）
    private String location;      // 地块位置（如：农场东侧）
    private Integer status;       // 地块状态（0=空闲，1=种植中）
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
