// src/main/java/com/agri/platform/service/analysis/EcommerceDataService.java
package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.EcommerceData;
import java.util.List;

public interface EcommerceDataService {
    void fetchAndSaveEcommerceData();
    List<EcommerceData> getLatestEcommerceData(String cropType);
    List<EcommerceData> comparePlatforms(String cropType);
}