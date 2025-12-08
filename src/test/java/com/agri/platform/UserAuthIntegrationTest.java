package com.agri.platform;

import com.agri.platform.DTO.UserLoginDTO;
import com.agri.platform.DTO.UserRegisterDTO;
import com.agri.platform.entity.user.VerifyCode;
import com.agri.platform.enums.UserLoginType;
import com.agri.platform.mapper.user.VerifyCodeMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
public class UserAuthIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private VerifyCodeMapper verifyCodeMapper;

        // 测试验证码发送接口
        @Test
        public void testSendVerifyCode() throws Exception {
                MvcResult result = mockMvc.perform(post("/api/verify-code/send")
                                .param("target", "1983517529@qq.com")
                                .param("type", "EMAIL")
                                .with(csrf()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").value("验证码发送成功"))
                                .andReturn();
                System.out.println("Status: " + result.getResponse().getStatus());
                // 打印响应内容
                System.out.println("Response Content: " + result.getResponse().getContentAsString());
        }

        @Test
        public void testSendVerifyCodeWithInvalidType() throws Exception {
                MvcResult result = mockMvc.perform(post("/api/verify-code/send")
                                .param("target", "13800138000")
                                .param("type", "PHONE_NUMBER")
                                .with(csrf()))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value("仅支持邮箱注册发送验证码")).andReturn();
                System.out.println("Status: " + result.getResponse().getStatus());
                // 打印响应内容
                System.out.println("Response Content: " + result.getResponse().getContentAsString());
        }

        // 测试用户注册接口
        @Test
        public void testUserRegister() throws Exception {
                String email = "1983517529@qq.com";

                // 1. 发验证码
                mockMvc.perform(post("/api/verify-code/send")
                                .param("target", email)
                                .param("type", "EMAIL")
                                .with(csrf()))
                                .andExpect(status().isOk());

                // 2. 从数据库抠出最新验证码
                VerifyCode vc = verifyCodeMapper.findLatestCode("REGISTER", email);
                String correctCode = vc.getCode();

                UserRegisterDTO registerDTO = new UserRegisterDTO(
                                UserLoginType.EMAIL,
                                "1983517529@qq.com",
                                "Test@123456", correctCode);

                MvcResult result = mockMvc.perform(post("/api/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerDTO)).with(csrf()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value("1983517529@qq.com")).andReturn();

                System.out.println("Status: " + result.getResponse().getStatus());
                // 打印响应内容
                System.out.println("Response Content: " + result.getResponse().getContentAsString());
        }

        @Test
        public void testRegisterWithoutPassword() throws Exception {
                String email = "1983517529@qq.com";

                // 1. 发验证码（仍需验证码，只是密码字段为空）
                mockMvc.perform(post("/api/verify-code/send")
                                .param("target", email)
                                .param("type", "EMAIL")
                                .with(csrf()))
                                .andExpect(status().isOk());

                // 2. 取出最新验证码
                VerifyCode vc = verifyCodeMapper.findLatestCode("REGISTER", email);
                String correctCode = vc.getCode();

                // 3. 组装 DTO：密码给空串
                UserRegisterDTO dtoMissingPwd = new UserRegisterDTO(
                                UserLoginType.EMAIL,
                                email,
                                "", // 缺少密码
                                correctCode);

                // 4. 发起注册请求，断言 400
                MvcResult result = mockMvc.perform(post("/api/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dtoMissingPwd))
                                .with(csrf()))
                                .andExpect(status().isBadRequest())
                                .andReturn();

                System.out.println("Status: " + result.getResponse().getStatus());
                System.out.println("Response Content: " + result.getResponse().getContentAsString());
        }

        // 测试用户登录接口
        @Test
        public void testLoginSuccess() throws Exception {
                // 事先准备一个已注册用户（复用注册逻辑）
                String email = "1983517529@qq.com";
                String rawPwd = "Test@123456";

                // 正式登录
                UserLoginDTO loginDTO = new UserLoginDTO(email, rawPwd, UserLoginType.EMAIL, null);
                MvcResult result = mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDTO))
                                .with(csrf()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value(email))
                                .andReturn();

                System.out.println("Login Success -> Status: " + result.getResponse().getStatus());
                System.out.println("Login Success -> Response: " + result.getResponse().getContentAsString());
        }

        /**
         * 2. 邮箱写错（用户不存在）
         */
        @Test
        public void testLoginWithWrongEmail() throws Exception {
                // 事先准备一个已注册用户（复用注册逻辑）
                String email = "1983517529@qq.com";
                String rawPwd = "Test@123456";

                UserLoginDTO dto = new UserLoginDTO(
                                "wrong_email@example.com",
                                "Whatever@123",
                                UserLoginType.EMAIL,
                                null);

                MvcResult result = mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .with(csrf()))
                                .andExpect(status().isBadRequest()) // 400
                                .andReturn();

                System.out.println("Wrong Email -> Status: " + result.getResponse().getStatus());
                System.out.println("Wrong Email -> Response: " + result.getResponse().getContentAsString());
        }

        /**
         * 3. 邮箱对但密码错
         */
        @Test
        public void testLoginWithWrongPassword() throws Exception {
                String email = "1983517529@qq.com";
                String rightPwd = "Test@123456";

                // 用错误密码登录
                UserLoginDTO dto = new UserLoginDTO(email, "Wrong@000", UserLoginType.EMAIL, null);
                MvcResult result = mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .with(csrf()))
                                .andExpect(status().isUnauthorized())
                                .andReturn();

                System.out.println("Wrong Password -> Status: " + result.getResponse().getStatus());
                System.out.println("Wrong Password -> Response: " + result.getResponse().getContentAsString());
        }

        /**
         * 4. 用户未绑定手机号，却用 PHONE_NUMBER 方式登录
         */
        @Test
        public void testLoginViaPhoneWhenNoPhoneBound() throws Exception {
                String email = "1983517529@qq.com";
                String pwd = "Test@123456";
                // 尝试用手机号登录（数据库里没有手机号）
                UserLoginDTO dto = new UserLoginDTO(
                                "13800138000",
                                pwd, UserLoginType.PHONE_NUMBER, null);
                MvcResult result = mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .with(csrf()))
                                .andExpect(status().isBadRequest())
                                .andReturn();

                System.out.println("No Phone Bound -> Status: " + result.getResponse().getStatus());
                System.out.println("No Phone Bound -> Response: " + result.getResponse().getContentAsString());
        }
}
