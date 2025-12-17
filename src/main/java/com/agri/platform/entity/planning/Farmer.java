package com.agri.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Farmer {
    private Long farmerId;        // 农场主ID（主键）
    private String farmerName;    // 农场主姓名（如：农场主A）
    private String phone;         // 联系电话
    private String address;       // 农场地址
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
