package com.agri.platform.DTO.report;

import java.io.Serializable;
import java.util.List;

import com.agri.platform.enums.SharePermissionEnum; // 引入正确的枚举类

/**
 * 报告分享请求
 * 对应功能-8
 * 说明：
 * - 支持将单个报告分享给多个用户
 * - permission 控制分享权限（仅查看/可导出）
 */
public class ReportShareDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 被分享的报告ID */
    private Long reportId;

    /** 分享发起人用户ID */
    private Long fromUserId;

    /** 接收方用户ID列表 */
    private List<Long> toUserIds;

    /** 分享权限：仅查看/可导出 */
    private SharePermissionEnum permission;

    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }

    public Long getFromUserId() { return fromUserId; }
    public void setFromUserId(Long fromUserId) { this.fromUserId = fromUserId; }

    public List<Long> getToUserIds() { return toUserIds; }
    public void setToUserIds(List<Long> toUserIds) { this.toUserIds = toUserIds; }

    public SharePermissionEnum getPermission() { return permission; }
    public void setPermission(SharePermissionEnum permission) { this.permission = permission; }
}
