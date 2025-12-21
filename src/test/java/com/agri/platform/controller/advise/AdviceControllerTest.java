package com.agri.platform.controller.advise;

import com.agri.platform.model.Advice;
import com.agri.platform.model.Message;
import com.agri.platform.repository.AdviceRepository;
import com.agri.platform.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdviceControllerTest {

    @Mock
    private AdviceRepository adviceRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private AdviceController adviceController;

    private Advice testAdvice;
    private Message testMessage;
    private Long testLandDbId = 1L;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        testAdvice = new Advice();
        testAdvice.setId(1L);
        testAdvice.setLandDbId(testLandDbId);
        testAdvice.setAdvisorName("测试顾问");
        testAdvice.setContent("这是一条测试建议");
        testAdvice.setCreatedAt(LocalDateTime.now());

        testMessage = new Message();
        testMessage.setId(1L);
        testMessage.setLandDbId(testLandDbId);
        testMessage.setSender("advisor");
        testMessage.setText("这是一条测试消息");
        testMessage.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testSubmitAdvice() {
        // 模拟仓库行为
        when(adviceRepository.save(any(Advice.class))).thenReturn(testAdvice);

        // 执行请求并验证结果
        ResponseEntity<Advice> response = adviceController.submitAdvice(testAdvice);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testAdvice.getId(), response.getBody().getId());
        assertEquals(testAdvice.getLandDbId(), response.getBody().getLandDbId());
        assertEquals(testAdvice.getAdvisorName(), response.getBody().getAdvisorName());
        assertEquals(testAdvice.getContent(), response.getBody().getContent());
        assertNotNull(response.getBody().getCreatedAt());

        // 验证仓库方法是否被调用
        verify(adviceRepository, times(1)).save(any(Advice.class));
    }

    @Test
    void testListByLand() {
        // 模拟仓库行为
        List<Advice> adviceList = Collections.singletonList(testAdvice);
        when(adviceRepository.findByLandDbId(testLandDbId)).thenReturn(adviceList);

        // 执行请求并验证结果
        List<Advice> response = adviceController.listByLand(testLandDbId);
        
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(testAdvice.getId(), response.get(0).getId());
        assertEquals(testAdvice.getLandDbId(), response.get(0).getLandDbId());

        // 验证仓库方法是否被调用
        verify(adviceRepository, times(1)).findByLandDbId(testLandDbId);
    }

    @Test
    void testPostMessage() {
        // 模拟仓库行为
        when(messageRepository.save(any(Message.class))).thenReturn(testMessage);

        // 执行请求并验证结果
        ResponseEntity<Message> response = adviceController.postMessage(testMessage);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testMessage.getId(), response.getBody().getId());
        assertEquals(testMessage.getLandDbId(), response.getBody().getLandDbId());
        assertEquals(testMessage.getSender(), response.getBody().getSender());
        assertEquals(testMessage.getText(), response.getBody().getText());
        assertNotNull(response.getBody().getCreatedAt());

        // 验证仓库方法是否被调用
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void testGetMessages() {
        // 模拟仓库行为
        List<Message> messageList = Collections.singletonList(testMessage);
        when(messageRepository.findByLandDbIdOrderByCreatedAtAsc(testLandDbId)).thenReturn(messageList);

        // 执行请求并验证结果
        List<Message> response = adviceController.getMessages(testLandDbId);
        
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(testMessage.getId(), response.get(0).getId());
        assertEquals(testMessage.getLandDbId(), response.get(0).getLandDbId());
        assertEquals(testMessage.getSender(), response.get(0).getSender());
        assertEquals(testMessage.getText(), response.get(0).getText());

        // 验证仓库方法是否被调用
        verify(messageRepository, times(1)).findByLandDbIdOrderByCreatedAtAsc(testLandDbId);
    }
}