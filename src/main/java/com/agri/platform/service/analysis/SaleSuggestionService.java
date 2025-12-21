package com.agri.platform.service.analysis;

import org.springframework.stereotype.Service;
import com.agri.platform.entity.analysis.SaleSuggestion;
import com.agri.platform.mapper.analysis.SaleSuggestionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleSuggestionService {
    private final SaleSuggestionMapper saleSuggestionMapper;
    public SaleSuggestion getSuggestion(String cropType, String channel) {
        return saleSuggestionMapper.getSuggestion(cropType);
    }
}
