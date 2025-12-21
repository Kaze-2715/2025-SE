package com.agri.platform.controller.analysis;

import com.agri.platform.entity.analysis.YieldPrediction;
import com.agri.platform.service.analysis.YieldPredictionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class YieldPredictionControllerTest {

    @Mock
    private YieldPredictionService yieldPredictionService;

    @InjectMocks
    private YieldPredictionController yieldPredictionController;

    // 测试产量预测接口
    @Test
    void testPredictYield() {
        // 1. 模拟依赖返回
        Long planId = 1L;
        Long modelId = 1L;
        YieldPrediction prediction = new YieldPrediction();
        prediction.setCropType("小麦");
        prediction.setPredictedYield(new BigDecimal("500.0"));
        prediction.setConfidence(new BigDecimal("0.95"));

        when(yieldPredictionService.predictYield(planId, modelId)).thenReturn(prediction);

        // 2. 执行测试方法（修正返回类型）
        YieldPrediction response = yieldPredictionController.predictYield(planId, modelId);

        // 3. 断言结果
        assertEquals("小麦", response.getCropType());
        assertEquals(new BigDecimal("500.0"), response.getPredictedYield());
        assertEquals(new BigDecimal("0.95"), response.getConfidence());
    }

    // 测试异常情况
    @Test
    void testPredictYieldWithException() {
        // 1. 模拟依赖抛出异常
        Long planId = 1L;
        Long modelId = 1L;
        when(yieldPredictionService.predictYield(planId, modelId)).thenThrow(new RuntimeException("模型不存在"));

        // 2. 执行测试方法并验证异常
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            yieldPredictionController.predictYield(planId, modelId);
        });
    }
}