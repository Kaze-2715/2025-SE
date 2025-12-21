package com.agri.platform.util.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import  com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * PDF 生成工具类
 * 对应需求：功能-9 报告导出功能
 */
public class PdfGeneratorUtil {

    /**
     * 生成包含文本内容的简单 PDF
     * @param title 报告标题
     * @param content 报告内容
     * @param creator 生成人姓名
     * @return ByteArrayInputStream 文件流
     */
    public static ByteArrayInputStream exportToPdf(String title, String content, String creator) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. 设置中文字体 (依赖 itext-asian)
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 18, Font.BOLD);
            Font contentFont = new Font(bfChinese, 12, Font.NORMAL);
            Font footerFont = new Font(bfChinese, 10, Font.ITALIC);

            // 2. 添加标题
            Paragraph titlePara = new Paragraph(title, titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            titlePara.setSpacingAfter(20);
            document.add(titlePara);

            // 3. 添加正文内容
            // 简单处理换行符
            if (content != null) {
                String[] lines = content.split("");
                for (String line : lines) {
                    Paragraph p = new Paragraph(line, contentFont);
                    p.setSpacingAfter(5);
                    document.add(p);
                }
            }

            // 4. 添加页脚信息
            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph("生成人: " + creator + " | 生成时间: " + java.time.LocalDateTime.now(), footerFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("PDF导出失败: " + e.getMessage(), e);
        }
    }
}
