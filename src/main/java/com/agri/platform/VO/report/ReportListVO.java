package com.agri.platform.VO.report;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.agri.platform.enums.ReportTypeEnum;

/**
 * 报告列表展示对象
 * 用于列表页/查询结果
 */
public class ReportListVO implements Serializable {

    private Long reportId;
    private ReportTypeEnum type;
    private String title;
    private Long creatorId;
    private String creatorName;
    private LocalDateTime createdAt;
    private Integer latestVersion;    // 最新版本号
    private Long relatedLandId;
    private Long plantingPlanId;
    private boolean shared;           // 是否已分享给他人

    public Long getReportId() { return reportId; }
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
    public Integer getLatestVersion() { return latestVersion; }
    public void setLatestVersion(Integer latestVersion) { this.latestVersion = latestVersion; }
    public Long getRelatedLandId() { return relatedLandId; }
    public void setRelatedLandId(Long relatedLandId) { this.relatedLandId = relatedLandId; }
    public Long getPlantingPlanId() { return plantingPlanId; }
    public void setPlantingPlanId(Long plantingPlanId) { this.plantingPlanId = plantingPlanId; }
    public boolean isShared() { return shared; }
    public void setShared(boolean shared) { this.shared = shared; }
}
