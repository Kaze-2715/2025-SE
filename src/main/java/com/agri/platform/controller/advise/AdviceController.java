package com.agri.platform.controller.advise;

import com.agri.platform.model.Advice;
import com.agri.platform.model.Message;
import com.agri.platform.repository.AdviceRepository;
import com.agri.platform.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/advice")
public class AdviceController {
    private final AdviceRepository adviceRepository;
    private final MessageRepository messageRepository;


    public AdviceController(AdviceRepository adviceRepository, MessageRepository messageRepository) {
        this.adviceRepository = adviceRepository;
        this.messageRepository = messageRepository;
    }


    @PostMapping
    public ResponseEntity<Advice> submitAdvice(@RequestBody Advice advice) {
        advice.setCreatedAt(LocalDateTime.now());
        Advice saved = adviceRepository.save(advice);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/land/{landDbId}")
    public List<Advice> listByLand(@PathVariable Long landDbId) {
        return adviceRepository.findByLandDbId(landDbId);
    }


    @PostMapping("/message")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        message.setCreatedAt(LocalDateTime.now());
        Message saved = messageRepository.save(message);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/messages/{landDbId}")
    public List<Message> getMessages(@PathVariable Long landDbId) {
        return messageRepository.findByLandDbIdOrderByCreatedAtAsc(landDbId);
    }
}
