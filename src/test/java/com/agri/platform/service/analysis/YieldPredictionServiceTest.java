package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.YieldPrediction;
import com.agri.platform.entity.analysis.YieldPredictionModel;
import com.agri.platform.entity.planning.PlantingPlan;
import com.agri.platform.mapper.analysis.YieldPredictionMapper;
import com.agri.platform.mapper.analysis.YieldPredictionModelMapper;
import com.agri.platform.mapper.planning.PlantingPlanMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class YieldPredictionServiceTest {
    @Mock
    private YieldPredictionMapper yieldPredictionMapper;
    @Mock
    private PlantingPlanMapper plantingPlanMapper;
    @Mock
    private YieldPredictionModelMapper yieldPredictionModelMapper;
    @InjectMocks
    private YieldPredictionServiceImpl yieldPredictionService;

    // 测试正常产量预测
    @Test
    void testPredictYield() {
        // 1. 模拟依赖返回
        PlantingPlan plan = new PlantingPlan();
        plan.setPlanId(1L);
        plan.setCropType("小麦");
        plan.setExpectedYield(new BigDecimal("500"));
        plan.setPlantingTime(LocalDate.now().minusDays(100));
        when(plantingPlanMapper.selectById(1L)).thenReturn(plan);

        YieldPredictionModel model = new YieldPredictionModel();
        model.setModelId(1L);
        model.setAlgorithmType("线性回归");
        // 修正变量名为yieldPredictionModelMapper
        when(yieldPredictionModelMapper.selectById(1L)).thenReturn(model);

        // 2. 执行测试方法
        YieldPrediction result = yieldPredictionService.predictYield(1L, 1L);

        // 3. 断言结果
        assertNotNull(result);
        assertEquals("小麦", result.getCropType());
        verify(yieldPredictionMapper, org.mockito.Mockito.times(1)).insert(any(YieldPrediction.class));
    }
}