package com.agri.platform.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 用户与权限服务远程调用客户端
 * 对应服务名: smartagri-user
 */
@FeignClient(name = "smartagri-user", path = "/user")
public interface UserClient {

    /**
     * 根据ID获取用户基础信息
     * (姓名、角色、头像URL)
     * 用于：报告展示生成人、分享记录展示操作人
     */
    @GetMapping("/{id}")
    Map<String, Object> getUserById(@PathVariable("id") Long userId);

    /**
     * 批量获取用户信息
     * 用于：列表页批量转换ID为姓名
     */
    @GetMapping("/batch")
    List<Map<String, Object>> getUserListByIds(@RequestParam("ids") List<Long> userIds);

    /**
     * 获取同组织下的所有用户列表
     * 用于：报告分享时选择接收人
     */
    @GetMapping("/list/organization")
    List<Map<String, Object>> getOrganizationUsers(@RequestParam("orgId") Long orgId);

    /**
     * 发送通知给用户
     * @param userId 用户ID
     * @param content 通知内容
     */
    @PostMapping("/notification")
    void sendNotification(@RequestParam("userId") String userId, @RequestParam("content") String content);
}