// src/main/java/com/agri/platform/service/analysis/MarketAnalysisServiceImpl.java
package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.MarketAnalysis;
import com.agri.platform.mapper.analysis.MarketAnalysisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MarketAnalysisServiceImpl implements MarketAnalysisService {

    @Autowired
    private MarketAnalysisMapper marketAnalysisMapper;
    
    @Autowired
    private ReportVersionService versionService;

    // 支持的作物类型
    private static final List<String> SUPPORTED_CROPS = List.of("小麦", "水稻", "玉米", "大豆", "棉花");

    @Override
    public MarketAnalysis generateMarketAnalysis(String cropType) {
        // 验证作物类型
        if (!SUPPORTED_CROPS.contains(cropType)) {
            throw new IllegalArgumentException("不支持的作物类型: " + cropType);
        }

        // 生成市场分析报告（实际项目中这里会对接真实的市场数据API）
        MarketAnalysis analysis = new MarketAnalysis();
        analysis.setCropType(cropType);
        analysis.setAnalysisDate(LocalDate.now());

        // 模拟当前价格
        BigDecimal currentPrice = getBasePrice(cropType).add(
                new BigDecimal((new Random().nextDouble() - 0.5) * 2)
        ).setScale(2, BigDecimal.ROUND_HALF_UP);
        analysis.setCurrentPrice(currentPrice);

        // 预测未来价格
        double trendFactor = getTrendFactor();
        BigDecimal predictedPrice = currentPrice.multiply(new BigDecimal(1 + trendFactor))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        analysis.setPredictedPrice(predictedPrice);

        // 价格趋势
        analysis.setPriceTrend(trendFactor > 0 ? "上涨" : (trendFactor < 0 ? "下跌" : "平稳"));

        // 市场需求
        analysis.setMarketDemand(getRandomDemand());

        // 主要市场区域
        analysis.setMainRegions(getMainRegions(cropType));

        // 影响因素
        analysis.setInfluencingFactors(getInfluencingFactors());

        analysis.setCreateTime(LocalDateTime.now());

        // 保存分析结果
        marketAnalysisMapper.insert(analysis);

        // 创建新版本记录
        versionService.createNewVersion(
                analysis.getAnalysisId(),
                "MARKET_ANALYSIS",
                "自动生成市场分析报告",
                "system"
        );
        return analysis;
    }

    @Override
    public List<MarketAnalysis> getMarketAnalyses(String cropType) {
        return marketAnalysisMapper.selectByCropType(cropType);
    }

    @Override
    public MarketAnalysis getLatestMarketAnalysis(String cropType) {
        List<MarketAnalysis> analyses = marketAnalysisMapper.selectLatestByCropType(cropType);
        return analyses.isEmpty() ? null : analyses.get(0);
    }

    // 每天凌晨3点更新市场数据
    @Scheduled(cron = "0 0 3 * * ?")
    @Override
    public void scheduledUpdateMarketData() {
        // 为所有支持的作物生成市场分析报告
        for (String crop : SUPPORTED_CROPS) {
            generateMarketAnalysis(crop);
        }
    }

    // 获取作物基础价格
    private BigDecimal getBasePrice(String cropType) {
        switch (cropType) {
            case "小麦": return new BigDecimal("2.85");
            case "水稻": return new BigDecimal("3.12");
            case "玉米": return new BigDecimal("2.45");
            case "大豆": return new BigDecimal("4.80");
            case "棉花": return new BigDecimal("15.60");
            default: return new BigDecimal("3.50");
        }
    }

    // 获取价格趋势因子
    private double getTrendFactor() {
        Random random = new Random();
        return (random.nextDouble() - 0.3) * 0.1; // -3% 到 +7%
    }

    // 获取随机市场需求
    private String getRandomDemand() {
        String[] demands = {"高", "中", "低"};
        return demands[new Random().nextInt(demands.length)];
    }

    // 获取主要市场区域
    private String getMainRegions(String cropType) {
        switch (cropType) {
            case "小麦": return "华北地区, 西北地区, 黄淮地区";
            case "水稻": return "长江流域, 华南地区, 东北地区";
            case "玉米": return "东北地区, 黄淮地区, 西南地区";
            default: return "全国市场";
        }
    }

    // 获取影响因素
    private String getInfluencingFactors() {
        List<String> factors = new ArrayList<>();
        factors.add("季节性需求变化");
        factors.add("种植面积变化");
        factors.add("天气情况");
        factors.add("政策调整");
        factors.add("国际市场价格波动");

        // 随机选择3-5个因素
        Random random = new Random();
        int count = 3 + random.nextInt(3);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < count; i++) {
            int index = random.nextInt(factors.size());
            if (i > 0) sb.append(", ");
            sb.append(factors.get(index));
            factors.remove(index);
        }

        return sb.toString();
    }
}