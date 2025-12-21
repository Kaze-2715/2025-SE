package com.agri.platform.DTO.report;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.agri.platform.enums.ReportTypeEnum;

/**
 * 报告查询条件
 * 对应功能-10
 * 注意：
 * - 当前登录用户ID请由调用方传入 currentUserId
 * - 系统管理员查询全量时，可通过 isAdmin 标识放开权限
 */
public class ReportQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前用户，用于权限过滤 */
    private Long currentUserId;

    /** 是否管理员（是：可查全量） */
    private boolean isAdmin;

    /** 是否仅查询分享给我的 */
    private Boolean onlySharedToMe;

    /** 报告类型过滤 */
    private List<ReportTypeEnum> types;

    /** 报告标题模糊搜索关键字 */
    private String titleKeyword;

    /** 生成时间开始 */
    private LocalDateTime createdFrom;

    /** 生成时间结束 */
    private LocalDateTime createdTo;

    /** 关联土地ID过滤 */
    private Long relatedLandId;

    /** 关联用户ID过滤（如技术服务对象） */
    private Long relatedUserId;

    /** 生成人ID过滤 */
    private Long creatorId;

    /** 分享对象精准查询 */
    private Long shareTargetId;

    /** 页码 */
    private Integer pageNo = 1;

    /** 每页大小 */
    private Integer pageSize = 20;

    public Long getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(Long currentUserId) { this.currentUserId = currentUserId; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
    public Boolean getOnlySharedToMe() { return onlySharedToMe; }
    public void setOnlySharedToMe(Boolean onlySharedToMe) { this.onlySharedToMe = onlySharedToMe; }
    public List<ReportTypeEnum> getTypes() { return types; }
    public void setTypes(List<ReportTypeEnum> types) { this.types = types; }
    public String getTitleKeyword() { return titleKeyword; }
    public void setTitleKeyword(String titleKeyword) { this.titleKeyword = titleKeyword; }
    public LocalDateTime getCreatedFrom() { return createdFrom; }
    public void setCreatedFrom(LocalDateTime createdFrom) { this.createdFrom = createdFrom; }
    public LocalDateTime getCreatedTo() { return createdTo; }
    public void setCreatedTo(LocalDateTime createdTo) { this.createdTo = createdTo; }
    public Long getRelatedLandId() { return relatedLandId; }
    public void setRelatedLandId(Long relatedLandId) { this.relatedLandId = relatedLandId; }
    public Long getRelatedUserId() { return relatedUserId; }
    public void setRelatedUserId(Long relatedUserId) { this.relatedUserId = relatedUserId; }
    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    public Long getShareTargetId() { return shareTargetId; }
    public void setShareTargetId(Long shareTargetId) { this.shareTargetId = shareTargetId; }
    public Integer getPageNo() { return pageNo; }
    public void setPageNo(Integer pageNo) { this.pageNo = pageNo; }
    public Integer getPageSize() { return pageSize; }
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }
}
