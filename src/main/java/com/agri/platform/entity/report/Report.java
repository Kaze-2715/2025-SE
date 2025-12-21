package com.agri.platform.entity.report;

import java.time.LocalDateTime;

/**
 * 报告主表
 * - latestVersion: 当前最新版本号
 * - type/title/creatorId/createdAt 基本元数据
 * - 关联对象：土地、种植计划、用户（如技术服务对象）
 */
public class Report {

    private Long id;
    private String type;              // 对应 model.enums.ReportType.name()
    private String title;
    private Long creatorId;
    private LocalDateTime createdAt;
    private Integer latestVersion;

    private Long relatedLandId;
    private Long plantingPlanId;
    private Long relatedUserId;

    private Boolean archived;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Integer getLatestVersion() { return latestVersion; }
    public void setLatestVersion(Integer latestVersion) { this.latestVersion = latestVersion; }
    public Long getRelatedLandId() { return relatedLandId; }
    public void setRelatedLandId(Long relatedLandId) { this.relatedLandId = relatedLandId; }
    public Long getPlantingPlanId() { return plantingPlanId; }
    public void setPlantingPlanId(Long plantingPlanId) { this.plantingPlanId = plantingPlanId; }
    public Long getRelatedUserId() { return relatedUserId; }
    public void setRelatedUserId(Long relatedUserId) { this.relatedUserId = relatedUserId; }
    public Boolean getArchived() { return archived; }
    public void setArchived(Boolean archived) { this.archived = archived; }
}
