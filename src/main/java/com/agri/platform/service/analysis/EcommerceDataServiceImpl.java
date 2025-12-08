// src/main/java/com/agri/platform/service/analysis/EcommerceDataServiceImpl.java
package com.agri.platform.service.analysis;

import com.agri.platform.entity.analysis.EcommerceData;
import com.agri.platform.mapper.analysis.EcommerceDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EcommerceDataServiceImpl implements EcommerceDataService {

    @Autowired
    private EcommerceDataMapper ecommerceDataMapper;

    // 支持的电商平台
    private static final List<String> PLATFORMS = List.of("淘宝", "京东", "拼多多", "美团优选");

    @Override
    @Scheduled(cron = "0 0 4 * * ?") // 每天凌晨4点抓取电商数据
    public void fetchAndSaveEcommerceData() {
        // 实际项目中这里会调用各电商平台API或爬虫获取数据
        for (String platform : PLATFORMS) {
            List<EcommerceData> dataList = mockFetchData(platform);
            ecommerceDataMapper.batchInsert(dataList);
        }

        // 删除7天前的旧数据
        ecommerceDataMapper.deleteOlderThan(LocalDateTime.now().minusDays(7));
    }

    @Override
    public List<EcommerceData> getLatestEcommerceData(String cropType) {
        return ecommerceDataMapper.selectByCropType(cropType);
    }

    @Override
    public List<EcommerceData> comparePlatforms(String cropType) {
        return ecommerceDataMapper.selectByCropType(cropType);
    }

    // 模拟抓取数据
    private List<EcommerceData> mockFetchData(String platform) {
        // 实际实现中会替换为真实的API调用
        return List.of();
    }
}