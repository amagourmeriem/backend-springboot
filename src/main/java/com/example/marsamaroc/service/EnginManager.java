package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.Engin;
import java.util.List;
import java.util.Optional;

public interface EnginManager {
    List<Engin> getAllEngins();
    Engin addEngin(Engin engin);
    Engin updateEngin(Engin engin, Long id);
    void deleteEngin(Long id);
    Optional<Engin> getEnginById(Long id);
    Engin saveEngin(Engin engin);

}
