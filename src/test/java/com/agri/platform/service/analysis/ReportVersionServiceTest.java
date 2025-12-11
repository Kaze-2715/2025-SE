package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.ReportVersion;
import com.agri.platform.mapper.analysis.ReportVersionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportVersionServiceTest {

    @Mock
    private ReportVersionMapper versionMapper;

    @InjectMocks
    private ReportVersionServiceImpl versionService;

    // 测试创建新版本
    @Test
    void testCreateNewVersion() {
        // 1. 模拟依赖返回
        Long reportId = 1L;
        String reportType = "MARKET_ANALYSIS";
        String changeLog = "更新价格预测模型";
        String operator = "admin";

        ReportVersion newVersion = new ReportVersion();
        newVersion.setReportId(reportId);
        newVersion.setReportType(reportType);
        newVersion.setVersionNumber(1);
        newVersion.setChangeLog(changeLog);
        newVersion.setCreatedBy(operator);
        newVersion.setCreateTime(LocalDateTime.now());
        newVersion.setIsCurrent(true);

        when(versionMapper.selectCurrentVersion(reportId, reportType)).thenReturn(null);
        when(versionMapper.insert(any(ReportVersion.class))).thenReturn(1);

        // 2. 执行测试方法
        ReportVersion result = versionService.createNewVersion(reportId, reportType, changeLog, operator);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(reportId, result.getReportId());
        assertEquals(reportType, result.getReportType());
        assertEquals(1, result.getVersionNumber());
        assertEquals(changeLog, result.getChangeLog());
        assertTrue(result.getIsCurrent());

        // 4. 验证方法调用
        verify(versionMapper, times(1)).selectCurrentVersion(reportId, reportType);
        verify(versionMapper, times(0)).updateCurrentStatus(anyLong(), anyString(), anyBoolean());
        verify(versionMapper, times(1)).insert(any(ReportVersion.class));
    }

    // 测试获取报告版本列表
    @Test
    void testGetReportVersions() {
        // 1. 模拟依赖返回
        Long reportId = 1L;
        String reportType = "MARKET_ANALYSIS";

        ReportVersion version1 = new ReportVersion();
        version1.setVersionId(1L);
        version1.setReportId(reportId);
        version1.setReportType(reportType);
        version1.setVersionNumber(1);

        ReportVersion version2 = new ReportVersion();
        version2.setVersionId(2L);
        version2.setReportId(reportId);
        version2.setReportType(reportType);
        version2.setVersionNumber(2);

        List<ReportVersion> expectedVersions = List.of(version1, version2);
        when(versionMapper.selectByReport(reportId, reportType)).thenReturn(expectedVersions);

        // 2. 执行测试方法
        List<ReportVersion> result = versionService.getReportVersions(reportId, reportType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getVersionNumber());
        assertEquals(2, result.get(1).getVersionNumber());

        // 4. 验证方法调用
        verify(versionMapper, times(1)).selectByReport(reportId, reportType);
    }

    // 测试获取当前版本
    @Test
    void testGetCurrentVersion() {
        // 1. 模拟依赖返回
        Long reportId = 1L;
        String reportType = "MARKET_ANALYSIS";

        ReportVersion currentVersion = new ReportVersion();
        currentVersion.setVersionId(2L);
        currentVersion.setReportId(reportId);
        currentVersion.setReportType(reportType);
        currentVersion.setVersionNumber(2);
        currentVersion.setIsCurrent(true);

        when(versionMapper.selectCurrentVersion(reportId, reportType)).thenReturn(currentVersion);

        // 2. 执行测试方法
        ReportVersion result = versionService.getCurrentVersion(reportId, reportType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(reportId, result.getReportId());
        assertEquals(reportType, result.getReportType());
        assertEquals(2, result.getVersionNumber());
        assertTrue(result.getIsCurrent());

        // 4. 验证方法调用
        verify(versionMapper, times(1)).selectCurrentVersion(reportId, reportType);
    }

    // 测试版本切换
    @Test
    void testSwitchVersion() {
        // 1. 模拟依赖返回
        Long versionId = 1L;
        String operator = "admin";

        ReportVersion targetVersion = new ReportVersion();
        targetVersion.setVersionId(versionId);
        targetVersion.setReportId(1L);
        targetVersion.setReportType("MARKET_ANALYSIS");
        targetVersion.setVersionNumber(1);

        when(versionMapper.selectById(versionId)).thenReturn(targetVersion);
        when(versionMapper.updateCurrentStatus(anyLong(), anyString(), anyBoolean())).thenReturn(1);
        when(versionMapper.update(any(ReportVersion.class))).thenReturn(1);

        // 2. 执行测试方法
        boolean result = versionService.switchVersion(versionId, operator);

        // 3. 断言结果
        assertTrue(result);

        // 4. 验证方法调用
        verify(versionMapper, times(1)).selectById(versionId);
        verify(versionMapper, times(1)).updateCurrentStatus(anyLong(), anyString(), anyBoolean());
        verify(versionMapper, times(1)).update(any(ReportVersion.class));
    }

    // 测试切换不存在的版本
    @Test
    void testSwitchVersionNotFound() {
        // 1. 模拟依赖返回
        Long versionId = 999L;
        String operator = "admin";

        when(versionMapper.selectById(versionId)).thenReturn(null);

        // 2. 执行测试方法
        boolean result = versionService.switchVersion(versionId, operator);

        // 3. 断言结果
        assertFalse(result);

        // 4. 验证方法调用
        verify(versionMapper, times(1)).selectById(versionId);
        verify(versionMapper, times(0)).updateCurrentStatus(anyLong(), anyString(), anyBoolean());
        verify(versionMapper, times(0)).update(any(ReportVersion.class));
    }
}