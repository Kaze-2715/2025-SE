package com.agri.platform.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 咨询表单提交的DTO（前端→后端）
 */
@Data  // Lombok注解，自动生成getter/setter/toString，无需手动写
public class ContactDTO {

    // 姓名：非空（@NotBlank），长度1-50
    @NotBlank(message = "姓名不能为空")
    private String name;

    // 电话：非空，且符合手机号格式（正则校验）
    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    // 邮箱：非空，且符合邮箱格式
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    private String email;

    // 咨询主题：可为空（前端选填）
    @NotBlank(message = "请选择咨询主题")
    private String subject;

    // 留言内容：可为空（前端选填）
    private String message;
}