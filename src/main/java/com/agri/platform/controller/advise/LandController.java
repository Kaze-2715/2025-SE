package com.agri.platform.controller.advise;

import com.agri.platform.model.Land;
import com.agri.platform.repository.LandRepository;
import com.agri.platform.service.advice.StorageService;
import com.agri.platform.service.advice.SuggestionService;
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


    @GetMapping
    public List<Land> all() { return landRepository.findAll(); }


    @PostMapping
    public ResponseEntity<Land> create(@RequestParam String landId,
                                       @RequestParam Double area,
                                       @RequestParam String soilType,
                                       @RequestParam(required = false) MultipartFile attachment) throws IOException {
        Land l = new Land();
        l.setLandId(landId);
        l.setArea(area);
        l.setSoilType(soilType);
        if (attachment != null) {
            String path = storageService.store(attachment);
            l.setAttachmentPath(path);
        }
        Land saved = landRepository.save(l);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Land> update(@PathVariable Long id,
                                       @RequestParam String landId,
                                       @RequestParam Double area,
                                       @RequestParam String soilType) {
        Optional<Land> o = landRepository.findById(id);
        if (o.isEmpty()) return ResponseEntity.notFound().build();
        Land l = o.get();
        l.setLandId(landId);
        l.setArea(area);
        l.setSoilType(soilType);
        return ResponseEntity.ok(landRepository.save(l));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Land> get(@PathVariable Long id) {
        return landRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        landRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/suggestion")
    public ResponseEntity<String> suggestion(@PathVariable Long id) {
        return landRepository.findById(id).map(l -> ResponseEntity.ok(suggestionService.generateSuggestion(l.getLandId(), l.getArea(), l.getSoilType())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
