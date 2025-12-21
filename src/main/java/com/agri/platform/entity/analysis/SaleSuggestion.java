package com.agri.platform.entity.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleSuggestion {
    private String bestTime;
    private String priceRange;
    private String channel;
    private String reason;
}