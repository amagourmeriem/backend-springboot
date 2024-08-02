package com.example.marsamaroc.service;

import com.example.marsamaroc.dtos.EnginDto;
import java.util.List;
import java.util.Optional;

public interface EnginManager {
    List<EnginDto> getAllEngins();
    EnginDto addEngin(EnginDto enginDto);
    EnginDto updateEngin(EnginDto enginDto, Long id);
    void deleteEngin(Long id);
    Optional<EnginDto> getEnginById(Long id);
    EnginDto saveEngin(EnginDto enginDto);
}
