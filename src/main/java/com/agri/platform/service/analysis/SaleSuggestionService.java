package com.agri.platform.service.analysis;

import org.springframework.stereotype.Service;
import com.agri.platform.entity.analysis.SaleSuggestion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleSuggestionService {
    SaleSuggestionMapper saleSuggestionMapper;
    public SaleSuggestion getSuggestion(String cropType, String channel) {
        return saleSuggestionMapper.getSuggestion(cropType, channel);
    }
}
