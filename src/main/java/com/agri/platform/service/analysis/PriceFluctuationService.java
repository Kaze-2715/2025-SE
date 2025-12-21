package com.agri.platform.service.analysis;

import com.agri.platform.mapper.analysis.MarketDataMapper;
import com.agri.platform.mapper.analysis.EcommerceDataMapper;
import com.agri.platform.controller.analysis.PriceFluctuationController.AlertResp;
import com.agri.platform.controller.analysis.PriceFluctuationController.PriceCurrentResp;
import com.agri.platform.controller.analysis.PriceFluctuationController.PriceTrendResp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceFluctuationService {

    private final MarketDataMapper marketDataMapper;
    private final EcommerceDataMapper ecomDataMapper;

    /* ---------- 1. 今日价格 ---------- */
    public PriceCurrentResp currentPrice(String crop) {
        BigDecimal wholesale = marketDataMapper.selectLatestWholesale(crop);
        BigDecimal retail = ecomDataMapper.selectLatestRetail(crop);
        return PriceCurrentResp.builder()
                .wholesale(wholesale)
                .retail(retail == null ? wholesale : retail) // 电商无数据就 fallback
                .build();
    }

    /* ---------- 2. N 天价格趋势（折线） ---------- */
    public List<PriceTrendResp> trend(String crop, int days) {
        // 优先用批发价当主线；电商当补充
        List<PriceTrendResp> list = marketDataMapper.selectTrendWholesale(crop, days);
        if (list.isEmpty()) {
            // 万一批发没数据，用电商
            list = ecomDataMapper.selectTrendRetail(crop, days);
        }
        return list;
    }

    /* ---------- 3. 价格波动报警 ---------- */
    public AlertResp alert(String crop, double threshold) {
        BigDecimal avg30 = marketDataMapper.avg30Wholesale(crop);
        BigDecimal today = marketDataMapper.todayWholesale(crop);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if (avg30 == null || avg30.compareTo(BigDecimal.ZERO) == 0) {
            return AlertResp.builder().crop(crop).rate(0).alert(false).build();
        }
        double rate = today.subtract(avg30)
                .divide(avg30, 4, RoundingMode.HALF_UP)
                .doubleValue();
        boolean isAlert = Math.abs(rate) >= threshold;
        return AlertResp.builder()
                .crop(crop)
                .rate(rate)
                .alert(isAlert)
                .currentPrice(today)
                .avgPrice(avg30)
                .time(now)
                .build();
    }
}
