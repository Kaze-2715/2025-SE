package com.agri.platform.controller;

import com.agri.platform.dto.ContactDTO;  // 引入DTO（核心优化）
import com.agri.platform.entity.Contact;
import com.agri.platform.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;  // 引入参数校验注解（核心优化）
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class ContactController {

    private final ContactService contactService;

    // 1. 接口路径明确为 /api/contact（原@PostMapping无路径，容易和其他接口冲突）
    // 2. 用@Valid开启DTO参数校验，@RequestBody接收前端JSON
    @PostMapping("/contact")
    public ResponseEntity<String> submitContact(@Valid @RequestBody ContactDTO contactDTO) {
        try {
            // 3. 核心：DTO 转 Entity（前端传DTO，后端转成实体类存数据库）
            Contact contact = new Contact();
            contact.setName(contactDTO.getName());    // 姓名
            contact.setPhone(contactDTO.getPhone());  // 电话
            contact.setEmail(contactDTO.getEmail());  // 邮箱
            contact.setSubject(contactDTO.getSubject());  // 咨询主题
            contact.setMessage(contactDTO.getMessage());  // 留言内容
            contact.setCreateTime(LocalDateTime.now());  // 创建时间后端统一设置（避免前端篡改）

            // 调用Service保存
            boolean success = contactService.saveContact(contact);
            if (success) {
                return ResponseEntity.ok("咨询提交成功！我们将尽快与您联系");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("提交失败，请稍后重试");
            }
        } catch (Exception e) {
            // 异常捕获（仅返回友好提示，不暴露具体异常信息，更安全）
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("提交失败，请联系客服或稍后重试");
        }
    }
}