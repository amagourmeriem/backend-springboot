package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dao.entities.Engin;
import com.example.marsamaroc.service.EnginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000") //allowing client application to consume the backed
@RestController
@RequestMapping("/engins")
@RequiredArgsConstructor
public class EnginController {

    private final EnginService enginService;

    private static final Logger logger = LoggerFactory.getLogger(EnginController.class);

    @GetMapping
    public ResponseEntity<List<Engin>> getAllEngins() {
        List<Engin> engins = enginService.getAllEngins();
        // logger.info("Fetched {} engins from the database", engins.size()); // Désactiver temporairement
        return ResponseEntity.ok(engins);
    }


    @GetMapping("/engin/{id}")
    public ResponseEntity<Engin> getEnginById(@PathVariable Long id) {
        Optional<Engin> engin = enginService.getEnginById(id);
        return engin.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Engin addEngin(@RequestBody Engin engin) {
     return enginService.addEngin(engin);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Engin> updateEngin(@RequestBody Engin engin, @PathVariable Long id) {
        Engin updatedEngin = enginService.updateEngin(engin, id);
        return ResponseEntity.ok(updatedEngin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEngin(@PathVariable Long id) {
        enginService.deleteEngin(id);
        return ResponseEntity.noContent().build();
    }
}
