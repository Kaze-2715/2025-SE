package com.agri.platform.VO.report;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import com.agri.platform.enums.ReportTypeEnum;
/**
 * 报告详情视图对象
 * contentJson：统一存储文本+图表的结构化JSON（前端按模板渲染）
 */
public class ReportDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long reportId;
    private ReportTypeEnum type;
    private String title;
    private Long creatorId;
    private String creatorName;
    private LocalDateTime createdAt;
    private Long relatedLandId;
    private Long plantingPlanId;
    private Long relatedUserId;      // 如技术服务对象
    private Integer latestVersion;
    private String contentJson;      // 报告正文（结构化 JSON）
    private List<String> attachments; // 图片/文档等

    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }
    public ReportTypeEnum getType() { return type; }
    public void setType(ReportTypeEnum type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    public String getCreatorName() { return creatorName; }
    public void setCreatorName(String creatorName) { this.creatorName = creatorName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Long getRelatedLandId() { return relatedLandId; }
    public void setRelatedLandId(Long relatedLandId) { this.relatedLandId = relatedLandId; }
    public Long getPlantingPlanId() { return plantingPlanId; }
    public void setPlantingPlanId(Long plantingPlanId) { this.plantingPlanId = plantingPlanId; }
    public Long getRelatedUserId() { return relatedUserId; }
    public void setRelatedUserId(Long relatedUserId) { this.relatedUserId = relatedUserId; }
    public Integer getLatestVersion() { return latestVersion; }
    public void setLatestVersion(Integer latestVersion) { this.latestVersion = latestVersion; }
    public String getContentJson() { return contentJson; }
    public void setContentJson(String contentJson) { this.contentJson = contentJson; }
    public List<String> getAttachments() { return attachments; }
    public void setAttachments(List<String> attachments) { this.attachments = attachments; }
}
