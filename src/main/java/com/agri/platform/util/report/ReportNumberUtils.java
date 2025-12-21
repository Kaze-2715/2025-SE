package com.agri.platform.util.report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 报告编号与版本号生成工具类
 */
public class ReportNumberUtils {

    private static final String REPORT_PREFIX = "RPT";
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 生成唯一的报告编号
     * 格式：RPT-yyyyMMddHHmmss-随机数
     * 示例：RPT-20231027103000-852
     */
    public static String generateReportNo() {
        String timestamp = LocalDateTime.now().format(DATE_FMT);
        int random = ThreadLocalRandom.current().nextInt(100, 999);
        return String.format("%s-%s-%d", REPORT_PREFIX, timestamp, random);
    }

    /**
     * 生成初始版本号
     * @return "V1.0"
     */
    public static String getInitialVersion() {
        return "V1.0";
    }

    /**
     * 计算下一个版本号
     * 逻辑：小数点后数字加1
     * 示例："V1.0" -> "V1.1", "V1.9" -> "V2.0"
     */
    public static String nextVersion(String currentVersion) {
        if (currentVersion == null || !currentVersion.startsWith("V")) {
            return getInitialVersion();
        }

        try {
            String versionNum = currentVersion.substring(1); // 去掉 'V'
            String[] parts = versionNum.split("\\.");
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts[1]);

            minor++;
            if (minor > 9) {
                major++;
                minor = 0;
            }

            return "V" + major + "." + minor;
        } catch (Exception e) {
            // 如果解析失败，默认返回初始版本
            return getInitialVersion();
        }
    }
}
