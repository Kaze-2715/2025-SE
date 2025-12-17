package com.agri.platform.controller;

import com.agri.platform.common.Result;
import com.agri.platform.entity.Land;
import com.agri.platform.service.LandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/land")
public class LandController {

    @Resource
    private LandService landService;

    // 前端新增种植计划时，获取当前农场主的所有地块（下拉选择用）
    @GetMapping("/farmer/{farmerId}")
    public Result<List<Land>> getLandByFarmerId(@PathVariable Long farmerId) {
        List<Land> lands = landService.getByFarmerId(farmerId);
        return Result.success(lands);
    }

    // 根据地块ID查询地块详情（预警、传感器数据关联用）
    @GetMapping("/{landId}")
    public Result<Land> getLandDetail(@PathVariable Long landId) {
        Land land = landService.getLandById(landId);
        return Result.success(land);
    }
}