package com.agri.platform.entity.planning;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WarningHandleRecord {
    private Long recordId; // 记录ID
    private Long warningId; // 关联预警ID
    private Long farmerId; // 处理人（农场主ID）
    private String handleMeasure; // 处理措施
    private LocalDateTime handleTime; // 处理时间
    private String effectFeedback; // 效果反馈
}