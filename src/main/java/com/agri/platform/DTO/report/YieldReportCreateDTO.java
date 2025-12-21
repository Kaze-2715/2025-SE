package com.agri.platform.DTO.report;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 农场主-产量报告创建请求（功能-3）
 * 用途：承载生成“产量报告”所需的输入数据，由控制器接收后传递给服务层。
 * 说明：
 * - 实际产量（actualYield）通常为必填；预估产量/面积可由种植计划回填。
 * - 本 DTO 不包含业务逻辑或校验，业务侧进行参数校验与补全。
 */
public class YieldReportCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 生成人用户ID（农场主） */
    private Long creatorId;

    /** 农场ID（可选，用于上下文统计/聚合） */
    private Long farmId;

    /** 关联种植计划ID（用于预估产量、面积关联） */
    private Long plantingPlanId;

    /** 作物类型（用于对比分析） */
    private String cropType;

    /** 种植面积（可留空，系统可从种植计划回填） */
    private BigDecimal area;

    /** 预估产量（可留空，系统查询/计算填充） */
    private BigDecimal estimatedYield;

    /** 实际产量（通常为必填） */
    private BigDecimal actualYield;

    /** 收获日期 */
    private LocalDate harvestDate;

    /** 收获情况说明/备注 */
    private String notes;

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    public Long getFarmId() { return farmId; }
    public void setFarmId(Long farmId) { this.farmId = farmId; }

    public Long getPlantingPlanId() { return plantingPlanId; }
    public void setPlantingPlanId(Long plantingPlanId) { this.plantingPlanId = plantingPlanId; }

    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }

    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }

    public BigDecimal getEstimatedYield() { return estimatedYield; }
    public void setEstimatedYield(BigDecimal estimatedYield) { this.estimatedYield = estimatedYield; }

    public BigDecimal getActualYield() { return actualYield; }
    public void setActualYield(BigDecimal actualYield) { this.actualYield = actualYield; }

    public LocalDate getHarvestDate() { return harvestDate; }
    public void setHarvestDate(LocalDate harvestDate) { this.harvestDate = harvestDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
