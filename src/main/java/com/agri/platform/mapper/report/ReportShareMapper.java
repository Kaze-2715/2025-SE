package com.agri.platform.mapper.report;

import com.agri.platform.entity.report.Report;
import com.agri.platform.entity.report.ReportShare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报告分享记录 Mapper
 * 对应数据库表: report_share
 */
@Mapper
public interface ReportShareMapper {

    /**
     * 新增分享记录 (单个)
     * 对应功能：8 报告分享
     */
    int insert(ReportShare reportShare);

    /**
     * 批量新增分享记录
     * 对应功能：8 报告分享 (一次分享给多人)
     */
    int insertBatch(@Param("shareList") List<ReportShare> shareList);

    /**
     * 查询某人收到的所有报告
     * 对应功能：8 分享后在“我的分享”中查看
     * 实现提示：需要 join report 表
     */
    List<Report> selectReportsByTargetUserId(@Param("targetUserId") Long targetUserId);

    /**
     * 校验权限 / 查询具体的分享记录
     * 对应功能：判断是否有权查看或导出
     * @return 如果返回 null，说明未被分享给该用户
     */
    ReportShare selectByReportAndUser(@Param("reportId") Long reportId, 
            @Param("userId") Long userId);
                                      
    void upsertBatch(List<ReportShare> list);

    void deleteByReportAndTarget(Long reportId, Long toUserId);

    List<Long> selectTargets(Long reportId);

    void updatePermission(Long reportId, Long toUserId, String permission);
}
