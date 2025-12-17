package com.agri.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WarningHandleRecordDTO {
    // 处理记录信息
    private Long id; // 记录ID
    private Long warningId; // 关联预警ID
    private Long handlePerson; // 处理人（农场主ID）
    private String handleMeasure; // 处理措施
    
    // 使用LocalDateTime类型并配置Jackson解析ISO格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime handleTime; // 处理时间
    
    private String effectFeedback; // 效果反馈

    // 关联的预警信息
    private String warningType; // 预警类型（如小麦锈病）
    private String cropType; // 作物类型（如小麦）
    private String warningReason; // 预警原因
    private Integer warningLevel; // 预警等级
}