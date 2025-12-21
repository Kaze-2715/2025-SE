package com.agri.platform.controller.planning;

import com.agri.platform.entity.planning.PestWarning;
import com.agri.platform.mapper.planning.PestWarningMapper;
import com.agri.platform.util.userRolePermission.GetUserIdFromSessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/pest-warning")
public class PestWarningController {
    @Autowired
    private PestWarningMapper pestWarningMapper;

    // 按处理状态查询预警（0=未处理，1=处理中，2=已处理）
    @GetMapping("/status/{status}")
    public List<PestWarning> getByStatus(@PathVariable Integer status) {
        String userId = GetUserIdFromSessionUtil.getCurrentUserId();
        return pestWarningMapper.selectByUserIdAndHandleStatus(userId, status);
    }

    // 按预警等级查询（3=红色预警）
    @GetMapping("/level/{level}")
    public List<PestWarning> getByLevel(@PathVariable Integer level) {
        String userId = GetUserIdFromSessionUtil.getCurrentUserId();
        return pestWarningMapper.selectByUserIdAndWarningLevel(userId, level);
    }
    
    // 查询所有预警数据
    @GetMapping("/list")
    public List<PestWarning> getAllWarnings() {
        String userId = GetUserIdFromSessionUtil.getCurrentUserId();
        return pestWarningMapper.selectByUserId(userId);
    }
}