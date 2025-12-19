package com.agri.platform.service.advice;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {
    @Value("${llm.api.url:}")
    private String llmApiUrl;


    public String generateSuggestion(String landId, Double area, String soilType) {
// 这里保留接入点：如果 llmApiUrl 配置了真实接口，可在此调用。
        if (llmApiUrl == null || llmApiUrl.isBlank()) {
// 返回示例建议
            return String.format("示例建议：土地 %s（面积 %.2f 平方米，土壤：%s）——建议种植耐旱作物，施入有机肥提高土壤肥力。", landId, area == null ? 0.0 : area, soilType);
        }
// 否则可使用 RestTemplate 或 WebClient 调用外部大模型 API。
        return "已调用外部大模型生成建议（占位）。";
    }
}
