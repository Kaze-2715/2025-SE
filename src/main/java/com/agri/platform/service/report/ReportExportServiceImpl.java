package com.agri.platform.service.report;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.agri.platform.interfaces.UserClient;
import com.agri.platform.mapper.report.ExportLogMapper;
import com.agri.platform.entity.report.ExportLog;
import com.agri.platform.enums.ExportFormatEnum;
import com.agri.platform.VO.report.ReportDetailVO;
import com.agri.platform.util.report.ReportApplication;
import com.agri.platform.util.report.ExcelGeneratorUtil;
import com.agri.platform.util.report.PdfGeneratorUtil;;

/**
 * 导出实现
 * - 真正的文件渲染交由外部导出组件，这里只做流程演示与导出日志记录
 */
@Service
public class ReportExportServiceImpl implements ReportExportService {

    private final ExportLogMapper exportLogMapper;
    private final ReportApplication reportApplication; // 用于生成文件名等工具
    private final ReportService reportService;
    private final UserClient userClient;

    public ReportExportServiceImpl(ExportLogMapper exportLogMapper, 
                                   ReportApplication reportApplication,
                                   ReportService reportService,
                                   UserClient userClient) {
        this.exportLogMapper = exportLogMapper;
        this.reportApplication = reportApplication;
        this.reportService = reportService;
        this.userClient = userClient;
    }

    @Override
    public String export(Long reportId, Long exporterId, ExportFormatEnum format) {
        // 生成文件名：报告类型-生成时间-生成人（示例中由工具类获取）
        String fileName = reportApplication.buildExportFileName(reportId, format);

        // 1. 获取报告详情
        ReportDetailVO reportDetail = reportService.getReportDetail(reportId, exporterId);
        if (reportDetail == null) {
            throw new IllegalArgumentException("报告不存在: " + reportId);
        }

        // 2. 根据格式调用导出工具
        ByteArrayInputStream fileStream = null;
        switch (format) {
            case PDF:
                // 获取导出人姓名
                Map<String, Object> exporterInfo = userClient.getUserById(exporterId);
                String exporterName = (String) exporterInfo.get("name");
                // 调用PDF生成工具
                fileStream = PdfGeneratorUtil.exportToPdf(
                    reportDetail.getTitle(),
                    reportDetail.getContentJson(),  // 实际项目中应解析contentJson并生成格式化内容
                    exporterName
                );
                break;
            case XLSX:
                // 调用Excel生成工具
                // 这里需要解析contentJson生成表格数据，实际项目中需要根据报告类型做不同处理
                List<String> headers = List.of("字段", "值");
                List<List<Object>> dataRows = List.of(
                    List.of("报告ID", reportDetail.getReportId()),
                    List.of("标题", reportDetail.getTitle()),
                    List.of("类型", reportDetail.getType().name()),
                    List.of("创建人", reportDetail.getCreatorName()),
                    List.of("创建时间", reportDetail.getCreatedAt()),
                    List.of("最新版本", reportDetail.getLatestVersion())
                );
                fileStream = ExcelGeneratorUtil.exportToExcel(
                    reportDetail.getTitle(),
                    headers,
                    dataRows
                );
                break;
            default:
                throw new IllegalArgumentException("不支持的导出格式: " + format);
        }

        // 3. 存储文件到文件服务（实际项目中需要调用文件服务API）
        // 这里简单模拟存储，返回文件名
        try {
            // 模拟文件存储操作
            System.out.println("存储文件: " + fileName + "，大小: " + fileStream.available() + " bytes");
            fileStream.close();
        } catch (IOException e) {
            throw new RuntimeException("文件存储失败: " + e.getMessage(), e);
        }

        // 4. 写导出日志
        ExportLog log = new ExportLog();
        log.setReportId(reportId);
        log.setExporterId(exporterId);
        log.setFormat(format.name());
        log.setFileName(fileName);
        log.setExportedAt(LocalDateTime.now());
        exportLogMapper.insert(log);

        return fileName;
    }
}