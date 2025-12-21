package com.agri.platform.controller.analysis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agri.platform.entity.analysis.SaleSuggestion;
import com.agri.platform.service.analysis.SaleSuggestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class SaleSuggestionController {
    private final SaleSuggestionService saleSuggestionService;
    @GetMapping("/suggestion")
    public SaleSuggestion Suggestion(@RequestParam String crop, @RequestParam String channel) {
        return saleSuggestionService.getSuggestion(crop, channel);
    }
}
