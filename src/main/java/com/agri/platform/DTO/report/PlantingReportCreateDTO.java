package com.agri.platform.DTO.report;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 农场主-种植记录报告创建请求（功能-1）
 * 说明：
 * - 系统会自动采集：种植规划、田间操作、监控数据（由外部模块通过 client 接口获取）
 * - 用户可补充：种植笔记、收获总结、收获照片
 * - 该 DTO 仅承担请求数据承载职责，不包含业务逻辑
 */
public class PlantingReportCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 生成人用户ID（农场主） */
    private Long creatorId;

    /** 农场ID（用于资源、监控等数据聚合） */
    private Long farmId;

    /** 关联土地ID */
    private Long landId;

    /** 关联种植计划ID（用于自动汇总种植规划、作业记录等） */
    private Long plantingPlanId;

    /** 收获日期（触发生成条件满足时可传） */
    private LocalDate harvestDate;

    /** 手动补充的种植笔记 */
    private String notes;

    /** 收获总结（文字描述） */
    private String harvestSummary;

    /** 收获实景照片URL列表 */
    private List<String> photoUrls;

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public Long getLandId() {
        return landId;
    }

    public void setLandId(Long landId) {
        this.landId = landId;
    }

    public Long getPlantingPlanId() {
        return plantingPlanId;
    }

    public void setPlantingPlanId(Long plantingPlanId) {
        this.plantingPlanId = plantingPlanId;
    }

    public LocalDate getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(LocalDate harvestDate) {
        this.harvestDate = harvestDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHarvestSummary() {
        return harvestSummary;
    }

    public void setHarvestSummary(String harvestSummary) {
        this.harvestSummary = harvestSummary;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
