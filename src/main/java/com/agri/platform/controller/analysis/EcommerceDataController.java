// src/main/java/com/agri/platform/controller/analysis/EcommerceDataController.java
package com.agri.platform.controller.analysis;

import com.agri.platform.entity.analysis.EcommerceData;
import com.agri.platform.service.analysis.EcommerceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/analysis/ecommerce")
public class EcommerceDataController {

    @Autowired
    private EcommerceDataService ecommerceDataService;

    @GetMapping("/latest/{cropType}")
    public List<EcommerceData> getLatestData(@PathVariable String cropType) {
        return ecommerceDataService.getLatestEcommerceData(cropType);
    }

    @GetMapping("/compare/{cropType}")
    public List<EcommerceData> comparePlatforms(@PathVariable String cropType) {
        return ecommerceDataService.comparePlatforms(cropType);
    }
}