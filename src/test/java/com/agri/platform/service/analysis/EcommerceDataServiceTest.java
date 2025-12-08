package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.EcommerceData;
import com.agri.platform.mapper.analysis.EcommerceDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EcommerceDataServiceTest {

    @Mock
    private EcommerceDataMapper ecommerceDataMapper;

    @InjectMocks
    private EcommerceDataServiceImpl ecommerceDataService;

    // 测试获取最新电商数据
    @Test
    void testGetLatestEcommerceData() {
        // 1. 模拟依赖返回
        String cropType = "小麦";
        EcommerceData ecommerceData = new EcommerceData();
        ecommerceData.setCropType("小麦");
        ecommerceData.setPlatformName("淘宝");
        ecommerceData.setPrice(new BigDecimal(2.6));
        ecommerceData.setSalesVolume(1000);
        ecommerceData.setCommentCount(500);
        ecommerceData.setPositiveRate(new BigDecimal(0.95));
        ecommerceData.setCrawlTime(LocalDateTime.now());

        List<EcommerceData> expectedData = List.of(ecommerceData);
        when(ecommerceDataMapper.selectByCropType(cropType)).thenReturn(expectedData);

        // 2. 执行测试方法
        List<EcommerceData> result = ecommerceDataService.getLatestEcommerceData(cropType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("小麦", result.get(0).getCropType());
        assertEquals("淘宝", result.get(0).getPlatformName());
        assertEquals(new BigDecimal(2.6), result.get(0).getPrice());

        // 4. 验证方法调用
        verify(ecommerceDataMapper, times(1)).selectByCropType(cropType);
    }

    // 测试平台数据对比
    @Test
    void testComparePlatforms() {
        // 1. 模拟依赖返回
        String cropType = "小麦";
        EcommerceData taobaoData = new EcommerceData();
        taobaoData.setCropType("小麦");
        taobaoData.setPlatformName("淘宝");
        taobaoData.setPrice(new BigDecimal(2.6));

        EcommerceData jdData = new EcommerceData();
        jdData.setCropType("小麦");
        jdData.setPlatformName("京东");
        jdData.setPrice(new BigDecimal(2.7));

        List<EcommerceData> expectedData = List.of(taobaoData, jdData);
        when(ecommerceDataMapper.selectByCropType(cropType)).thenReturn(expectedData);

        // 2. 执行测试方法
        List<EcommerceData> result = ecommerceDataService.comparePlatforms(cropType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("淘宝", result.get(0).getPlatformName());
        assertEquals("京东", result.get(1).getPlatformName());

        // 4. 验证方法调用
        verify(ecommerceDataMapper, times(1)).selectByCropType(cropType);
    }

    // 测试定时抓取电商数据
    @Test
    void testFetchAndSaveEcommerceData() {
        // 1. 设置模拟行为 - 使用doReturn()而不是doNothing()，因为这些方法返回int类型
        doReturn(1).when(ecommerceDataMapper).batchInsert(any());
        doReturn(1).when(ecommerceDataMapper).deleteOlderThan(any());

        // 2. 执行测试方法
        ecommerceDataService.fetchAndSaveEcommerceData();

        // 3. 验证方法调用
        verify(ecommerceDataMapper, atLeastOnce()).batchInsert(any());
        verify(ecommerceDataMapper, times(1)).deleteOlderThan(any());
    }
}