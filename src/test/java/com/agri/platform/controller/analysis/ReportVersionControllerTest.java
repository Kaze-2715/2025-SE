package com.agri.platform.controller.analysis;

import com.agri.platform.entity.analysis.ReportVersion;
import com.agri.platform.service.analysis.ReportVersionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportVersionControllerTest {

    @Mock
    private ReportVersionService versionService;

    @InjectMocks
    private ReportVersionController versionController;

    // 测试创建版本接口
    @Test
    void testCreateVersion() {
        // 1. 模拟依赖返回
        Long reportId = 1L;
        String reportType = "MARKET_ANALYSIS";
        String changeLog = "更新价格预测模型";
        String operator = "admin";

        ReportVersion expectedVersion = new ReportVersion();
        expectedVersion.setReportId(reportId);
        expectedVersion.setReportType(reportType);
        expectedVersion.setVersionNumber(1);
        expectedVersion.setChangeLog(changeLog);
        expectedVersion.setIsCurrent(true);

        when(versionService.createNewVersion(reportId, reportType, changeLog, operator))
                .thenReturn(expectedVersion);

        // 2. 执行测试方法
        ReportVersion result = versionController.createVersion(reportId, reportType, changeLog, operator);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(reportId, result.getReportId());
        assertEquals(reportType, result.getReportType());
        assertEquals(changeLog, result.getChangeLog());
        assertTrue(result.getIsCurrent());
    }

    // 测试获取版本列表接口
    @Test
    void testGetVersions() {
        // 1. 模拟依赖返回
        Long reportId = 1L;
        String reportType = "MARKET_ANALYSIS";

        ReportVersion version1 = new ReportVersion();
        version1.setVersionId(1L);
        version1.setVersionNumber(1);

        ReportVersion version2 = new ReportVersion();
        version2.setVersionId(2L);
        version2.setVersionNumber(2);

        List<ReportVersion> expectedVersions = List.of(version1, version2);
        when(versionService.getReportVersions(reportId, reportType)).thenReturn(expectedVersions);

        // 2. 执行测试方法
        List<ReportVersion> result = versionController.getVersions(reportId, reportType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getVersionNumber());
        assertEquals(2, result.get(1).getVersionNumber());
    }

    // 测试获取当前版本接口
    @Test
    void testGetCurrentVersion() {
        // 1. 模拟依赖返回
        Long reportId = 1L;
        String reportType = "MARKET_ANALYSIS";

        ReportVersion currentVersion = new ReportVersion();
        currentVersion.setReportId(reportId);
        currentVersion.setReportType(reportType);
        currentVersion.setVersionNumber(2);
        currentVersion.setIsCurrent(true);

        when(versionService.getCurrentVersion(reportId, reportType)).thenReturn(currentVersion);

        // 2. 执行测试方法
        ReportVersion result = versionController.getCurrentVersion(reportId, reportType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(reportId, result.getReportId());
        assertEquals(reportType, result.getReportType());
        assertTrue(result.getIsCurrent());
    }

    // 测试版本切换接口
    @Test
    void testSwitchVersion() {
        // 1. 模拟依赖返回
        Long versionId = 1L;
        String operator = "admin";

        when(versionService.switchVersion(versionId, operator)).thenReturn(true);

        // 2. 执行测试方法
        boolean result = versionController.switchVersion(versionId, operator);

        // 3. 断言结果
        assertTrue(result);
    }
}