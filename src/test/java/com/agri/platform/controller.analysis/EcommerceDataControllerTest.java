package com.agri.platform.controller.analysis;

import com.agri.platform.entity.analysis.EcommerceData;
import com.agri.platform.service.analysis.EcommerceDataService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EcommerceDataControllerTest {

    @Mock
    private EcommerceDataService ecommerceDataService;

    @InjectMocks
    private EcommerceDataController ecommerceDataController;

    // 测试获取最新电商数据接口
    @Test
    void testGetLatestData() {
        // 1. 模拟依赖返回
        String cropType = "小麦";
        EcommerceData ecommerceData = new EcommerceData();
        ecommerceData.setCropType("小麦");
        ecommerceData.setPlatformName("淘宝");
        ecommerceData.setPrice(new BigDecimal(2.6));

        List<EcommerceData> expectedData = List.of(ecommerceData);
        when(ecommerceDataService.getLatestEcommerceData(cropType)).thenReturn(expectedData);

        // 2. 执行测试方法
        List<EcommerceData> result = ecommerceDataController.getLatestData(cropType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("小麦", result.get(0).getCropType());
        assertEquals("淘宝", result.get(0).getPlatformName());
        assertEquals(new BigDecimal(2.6), result.get(0).getPrice());
    }

    // 测试平台数据对比接口
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
        when(ecommerceDataService.comparePlatforms(cropType)).thenReturn(expectedData);

        // 2. 执行测试方法
        List<EcommerceData> result = ecommerceDataController.comparePlatforms(cropType);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("淘宝", result.get(0).getPlatformName());
        assertEquals("京东", result.get(1).getPlatformName());
    }
}