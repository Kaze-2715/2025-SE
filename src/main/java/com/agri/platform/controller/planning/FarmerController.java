package com.agri.platform.controller;

import com.agri.platform.common.Result;
import com.agri.platform.entity.Farmer;
import com.agri.platform.service.FarmerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource; // 修改这行，使用jakarta包

@RestController
@RequestMapping("/farmer")
public class FarmerController {

    @Resource
    private FarmerService farmerService;

    // 前端获取当前登录农场主信息（默认用farmerId=1，实际项目可对接登录态）
    @GetMapping("/info/{farmerId}")
    public Result<Farmer> getFarmerInfo(@PathVariable Long farmerId) {
        Farmer farmer = farmerService.getByFarmerId(farmerId);
        return Result.success(farmer);
    }
}