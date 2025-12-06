package com.agri.platform.controller.planning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 确保这个类在Spring Boot的扫描范围内（比如和启动类同包/子包）
@RestController
public class TestController {

    // 定义根路径接口
    @GetMapping("/")
    public String index() {
        return "项目启动成功！这是根路径的返回内容";
    }

    // 可选：新增一个测试接口
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}