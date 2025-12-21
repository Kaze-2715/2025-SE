package com.agri.platform.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agri.platform.DTO.report.ReportShareDTO;
import com.agri.platform.enums.SharePermissionEnum;

/**
 * 报告分享接口（功能-8）
 */
@Service
public interface ReportShareService {

    // 批量分享（创建或更新分享权限）
    void share(ReportShareDTO dto);

    // 取消对某接收人的分享
    void cancelShare(Long reportId, Long toUserId, Long operatorId);

    // 查询报告的分享对象列表（用户ID）
    List<Long> listShareTargets(Long reportId);
  
    void changePermission(Long reportId, Long toUserId, SharePermissionEnum permission, Long operatorId);
}
