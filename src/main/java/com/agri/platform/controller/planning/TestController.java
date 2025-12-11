package com.agri.platform.controller.planning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agri.platform.annotation.RequiredPermission;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/farm")
    @RequiredPermission("农场-查看")
    public String listFarm() {
        return "farm list";
    }

    @PostMapping("/device/control")
    @RequiredPermission("设备-控制")
    public String controlDevice() {
        return "device controlled";
    }
}
