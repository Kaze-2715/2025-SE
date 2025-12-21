package com.agri.platform.controller.analysis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agri.platform.service.analysis.PriceFluctuationService;

import lombok.RequiredArgsConstructor;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class PriceFluctuationController {

    /* -------------- 内部 DTO -------------- */
    @Data
    @Builder
    public static class PriceCurrentResp {
        private BigDecimal wholesale;
        private BigDecimal retail;
    }

    @Data
    @Builder
    public static class PriceTrendResp {
        private LocalDate date;
        private BigDecimal price;
    }

    @Data
    @Builder
    public static class AlertResp {
        private String crop;
        private double rate;
        private boolean alert;
        private BigDecimal currentPrice;
        private BigDecimal avgPrice;
        private String time;
    }

    /* -------------- 接口 -------------- */
    private final PriceFluctuationService priceService;

    /* ① 今日价格 */
    @GetMapping("/price/current")
    public PriceCurrentResp currentPrice(@RequestParam String crop) {
        return priceService.currentPrice(crop);
    }

    /* ② 价格趋势（默认 30 天，可改） */
    @GetMapping("/price/trend")
    public List<PriceTrendResp> trend(@RequestParam String crop,
            @RequestParam(defaultValue = "30") int days) {
        return priceService.trend(crop, days);
    }

    /* ③ 价格波动报警 */
    @GetMapping("/alert")
    public List<AlertResp> alert(@RequestParam(defaultValue = "小麦") String crop,
            @RequestParam(defaultValue = "20") int threshold) {
        return List.of(priceService.alert(crop, threshold));
    }
}
