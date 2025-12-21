package com.agri.platform.util.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Excel 生成工具类
 * 对应需求：功能-9 报告导出功能
 */
public class ExcelGeneratorUtil {

    /**
     * 生成简单的 Excel 文件流
     * @param title 报告标题 (将作为Sheet名)
     * @param headers 表头列表 (如：["指标项", "数值", "单位"])
     * @param dataRows 数据行列表，每行是一个Map或List
     * @return ByteArrayInputStream 文件流
     */
    public static ByteArrayInputStream exportToExcel(String title, List<String> headers, List<List<Object>> dataRows) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sanitizeSheetName(title));

            // 1. 创建表头样式
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 2. 写入表头
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(headerStyle);
            }

            // 3. 写入数据
            int rowIdx = 1;
            if (dataRows != null) {
                for (List<Object> rowData : dataRows) {
                    Row row = sheet.createRow(rowIdx++);
                    for (int i = 0; i < rowData.size(); i++) {
                        Object value = rowData.get(i);
                        row.createCell(i).setCellValue(value == null ? "" : value.toString());
                    }
                }
            }

            // 4. 自动调整列宽
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Excel导出失败: " + e.getMessage(), e);
        }
    }

    /**
     * 清理Sheet名称中的非法字符
     */
    private static String sanitizeSheetName(String name) {
        // 1. 处理null和空字符串
        if (name == null || name.trim().isEmpty()) {
            return "Report";
        }
        
        // 2. 替换所有非法字符为下划线
        // 非法字符：: \ / ? * [ ]
        String sanitized = name.replaceAll("[:\\\\/*?\\[\\]]", "_");
        
        // 3. 处理以单引号开头的情况
        if (sanitized.startsWith("'")) {
            sanitized = "_" + sanitized.substring(1);
        }
        
        // 4. 限制长度（Excel工作表名称最大31个字符）
        return sanitized.substring(0, Math.min(sanitized.length(), 31));
    }
}
