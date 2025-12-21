package com.agri.platform.service.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.agri.platform.DTO.report.PlantingReportCreateDTO;
import com.agri.platform.DTO.report.ReportQueryDTO;
import com.agri.platform.DTO.report.YieldReportCreateDTO;
import com.agri.platform.enums.ReportTypeEnum;
import com.agri.platform.VO.report.ReportDetailVO;
import com.agri.platform.VO.report.ReportListVO;
import com.agri.platform.VO.report.VersionDiffVO;

/**
 * 报告生成与管理接口（核心）
 * - 聚合各类报告生成
 * - 提供编辑、版本管理、查询能力
 * - 其它模块仅依赖此接口即可
 */
public interface ReportService {

    // 生成：种植记录报告（功能-1）
    Long generatePlantingRecordReport(PlantingReportCreateDTO dto);

    // 生成：资源利用报告（功能-2），periodType: MONTH/QUARTER/CYCLE
    Long generateResourceUsageReport(Long creatorId, Long farmId, String periodType,
            LocalDate periodStart, LocalDate periodEnd, String title);

    // 生成：产量报告（功能-3）
    Long generateYieldReport(YieldReportCreateDTO dto);

    // 生成：技术支持记录报告（功能-4）
    Long generateTechSupportReport(Long advisorId, Long farmerUserId, LocalDateTime serviceTime,
            String title, String contentMarkdown, List<String> attachmentUrls);

    // 生成：市场分析报告（功能-5）
    Long generateMarketAnalysisReport(Long analystId, String periodType,
            LocalDate periodStart, LocalDate periodEnd, String topicTitle);

    // 生成：平台统计报告（功能-6）
    Long generatePlatformStatisticsReport(Long adminId, String periodType,
            LocalDate periodStart, LocalDate periodEnd, String title);

    // 编辑并产生新版本（功能-7），返回新版本号
    Integer editReportAndNewVersion(Long reportId, Long editorId, String newContentJson, String changeSummary);

    // 获取报告详情（最新版本内容）
    ReportDetailVO getReportDetail(Long reportId, Long currentUserId);

    // 查询报告列表（功能-10，含权限过滤）
    List<ReportListVO> queryReports(ReportQueryDTO query);

    // 查看版本差异（功能-7）
    VersionDiffVO diffBetweenVersions(Long reportId, Integer fromVersion, Integer toVersion);

    // 获取可在线查看的报告正文（如前端直接渲染），此处直接返回 JSON 字符串
    String getReportContentJson(Long reportId, Integer version);

    // 归档（与导出/分享相对，主要用于状态标记）
    boolean archiveReport(Long reportId, Long operatorId);

    // 通用命名工具（生成标题/文件名时可用）
    String buildReportTitle(ReportTypeEnum type, String base, LocalDateTime at);
}
