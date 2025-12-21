package com.agri.platform.controller.advise;

import com.agri.platform.model.Land;
import com.agri.platform.repository.LandRepository;
import com.agri.platform.service.advice.StorageService;
import com.agri.platform.service.advice.SuggestionService;
import com.agri.platform.util.userRolePermission.GetUserIdFromSessionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lands")
public class LandController {
    private final LandRepository landRepository;
    private final SuggestionService suggestionService;
    private final StorageService storageService;

    public LandController(LandRepository landRepository, SuggestionService suggestionService, StorageService storageService) {
        this.landRepository = landRepository;
        this.suggestionService = suggestionService;
        this.storageService = storageService;
    }

    // 仅查询当前用户的地块
    @GetMapping
    public List<Land> all() {
        String userId = GetUserIdFromSessionUtil.getCurrentUserId();
        return landRepository.findByUserId(userId);
    }

    // 创建地块时关联当前用户
    @PostMapping
    public ResponseEntity<Land> create(@RequestParam String landId,
                                       @RequestParam Double area,
                                       @RequestParam String soilType,
                                       @RequestParam(required = false) MultipartFile attachment) throws IOException {
        Land l = new Land();
        l.setLandId(landId);
        l.setArea(area);
        l.setSoilType(soilType);
        l.setUserId(GetUserIdFromSessionUtil.getCurrentUserId()); // 关联当前用户
        if (attachment != null) {
            String path = storageService.store(attachment);
            l.setAttachmentPath(path);
        }
        Land saved = landRepository.save(l);
        return ResponseEntity.ok(saved);
    }

    // 其他方法保持不变，但需要添加用户权限验证，确保只能操作自己的地块
}