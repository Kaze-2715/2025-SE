package com.agri.platform.service.report;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agri.platform.interfaces.UserClient;
import com.agri.platform.mapper.report.ReportShareMapper;
import com.agri.platform.entity.report.ReportShare;
import com.agri.platform.DTO.report.ReportShareDTO;
import com.agri.platform.enums.SharePermissionEnum;

/**
 * 分享服务实现
 * - 仅进行写入/修改/删除 ReportShare 记录
 * - 发送站内信/通知可通过 UserClient 或消息服务完成
 */
@Service
public class ReportShareServiceImpl implements ReportShareService {

    private final ReportShareMapper reportShareMapper;
    private final UserClient userClient;

    public ReportShareServiceImpl(ReportShareMapper reportShareMapper, UserClient userClient) {
        this.reportShareMapper = reportShareMapper;
        this.userClient = userClient;
    }

    @Override
    public void share(ReportShareDTO dto) {
        if (dto == null || dto.getToUserIds() == null || dto.getToUserIds().isEmpty()) {
            return;
        }
        // 统一权限编码（为空则给默认值）
        final String permissionCode = dto.getPermission() == null
                ? SharePermissionEnum.VIEW_ONLY.name()
                : dto.getPermission().name();

        List<ReportShare> batch = new ArrayList<>(dto.getToUserIds().size());
        for (Long uid : dto.getToUserIds()) {
            if (uid == null) continue;
            ReportShare rs = new ReportShare();
            rs.setReportId(dto.getReportId());
            rs.setFromUserId(dto.getFromUserId());
            rs.setToUserId(uid);
            rs.setPermission(permissionCode);
            rs.setCreatedAt(LocalDateTime.now());
            batch.add(rs);
        }
        if (!batch.isEmpty()) {
            reportShareMapper.upsertBatch(batch);
        }

        // 通知接收方（站内信）
        for (Long uid : dto.getToUserIds()) {
            if (uid == null) continue;
            userClient.sendNotification(uid.toString(), "您收到一份共享报告，报告ID：" + dto.getReportId());
        }
    }

    @Override
    public void cancelShare(Long reportId, Long toUserId, Long operatorId) {
        reportShareMapper.deleteByReportAndTarget(reportId, toUserId);
    }

    @Override
    public List<Long> listShareTargets(Long reportId) {
        return reportShareMapper.selectTargets(reportId);
    }

    @Override
    public void changePermission(Long reportId, Long toUserId, SharePermissionEnum permission, Long operatorId) {
        final String permissionCode = permission == null ? SharePermissionEnum.VIEW_ONLY.name() : permission.name();
        reportShareMapper.updatePermission(reportId, toUserId, permissionCode);
    }
}
