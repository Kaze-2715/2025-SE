package com.agri.platform.controller.planning;

import com.agri.platform.entity.planning.PestWarning;
import com.agri.platform.mapper.planning.PestWarningMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PestWarningControllerTest {

    @Mock
    private PestWarningMapper pestWarningMapper;

    @InjectMocks
    private PestWarningController pestWarningController;

    private List<PestWarning> mockWarnings;
    private PestWarning warning1;
    private PestWarning warning2;

    @BeforeEach
    void setUp() {
        // 创建测试数据
        warning1 = new PestWarning();
        warning1.setWarningId(1L);
        warning1.setLandId(1L);
        warning1.setWarningType("小麦锈病");
        warning1.setWarningLevel(3); // 红色预警
        warning1.setHandleStatus(0); // 未处理
        warning1.setDescription("土壤温度过高且湿度较大，容易引发小麦锈病");
        warning1.setWarningReason("土壤温度：32℃，土壤湿度：85%，符合高风险条件");
        warning1.setCreateTime(LocalDateTime.now());
        warning1.setUpdateTime(LocalDateTime.now());

        warning2 = new PestWarning();
        warning2.setWarningId(2L);
        warning2.setLandId(2L);
        warning2.setWarningType("蚜虫");
        warning2.setWarningLevel(2); // 橙色预警
        warning2.setHandleStatus(1); // 处理中
        warning2.setDescription("空气温度和湿度适宜蚜虫繁殖");
        warning2.setWarningReason("空气温度：29℃，空气湿度：78%，符合中风险条件");
        warning2.setCreateTime(LocalDateTime.now());
        warning2.setUpdateTime(LocalDateTime.now());

        mockWarnings = Arrays.asList(warning1, warning2);
    }

    @Test
    void testGetByStatus_Unprocessed() {
        // 模拟按状态查询
        when(pestWarningMapper.selectByHandleStatus(0)).thenReturn(Arrays.asList(warning1));

        List<PestWarning> result = pestWarningController.getByStatus(0);

        // 验证结果
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getHandleStatus());
        verify(pestWarningMapper, times(1)).selectByHandleStatus(0);
    }

    @Test
    void testGetByStatus_Processing() {
        // 模拟按状态查询
        when(pestWarningMapper.selectByHandleStatus(1)).thenReturn(Arrays.asList(warning2));

        List<PestWarning> result = pestWarningController.getByStatus(1);

        // 验证结果
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getHandleStatus());
        verify(pestWarningMapper, times(1)).selectByHandleStatus(1);
    }

    @Test
    void testGetByStatus_Processed() {
        // 模拟按状态查询（无结果）
        when(pestWarningMapper.selectByHandleStatus(2)).thenReturn(Arrays.asList());

        List<PestWarning> result = pestWarningController.getByStatus(2);

        // 验证结果
        assertEquals(0, result.size());
        verify(pestWarningMapper, times(1)).selectByHandleStatus(2);
    }

    @Test
    void testGetByLevel_RedWarning() {
        // 模拟按等级查询
        when(pestWarningMapper.selectByWarningLevel(3)).thenReturn(Arrays.asList(warning1));

        List<PestWarning> result = pestWarningController.getByLevel(3);

        // 验证结果
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getWarningLevel());
        verify(pestWarningMapper, times(1)).selectByWarningLevel(3);
    }

    @Test
    void testGetByLevel_OrangeWarning() {
        // 模拟按等级查询
        when(pestWarningMapper.selectByWarningLevel(2)).thenReturn(Arrays.asList(warning2));

        List<PestWarning> result = pestWarningController.getByLevel(2);

        // 验证结果
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getWarningLevel());
        verify(pestWarningMapper, times(1)).selectByWarningLevel(2);
    }

    @Test
    void testGetByLevel_YellowWarning() {
        // 模拟按等级查询（无结果）
        when(pestWarningMapper.selectByWarningLevel(1)).thenReturn(Arrays.asList());

        List<PestWarning> result = pestWarningController.getByLevel(1);

        // 验证结果
        assertEquals(0, result.size());
        verify(pestWarningMapper, times(1)).selectByWarningLevel(1);
    }

    @Test
    void testGetByLevel_InvalidLevel() {
        // 模拟按无效等级查询
        when(pestWarningMapper.selectByWarningLevel(4)).thenReturn(Arrays.asList());

        List<PestWarning> result = pestWarningController.getByLevel(4);

        // 验证结果
        assertEquals(0, result.size());
        verify(pestWarningMapper, times(1)).selectByWarningLevel(4);
    }
}