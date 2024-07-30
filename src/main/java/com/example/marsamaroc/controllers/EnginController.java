package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dao.entities.Engin;
import com.example.marsamaroc.service.EnginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000") // allowing client application to consume the backend
@RestController
@RequestMapping("/engins")
@RequiredArgsConstructor
public class EnginController {

    private final EnginService enginService;
    private static final Logger logger = LoggerFactory.getLogger(EnginController.class);
    private static final String UPLOADED_FOLDER = "C:/uploads/";
    private final Path fileStorageLocation = Paths.get("C:/uploads").toAbsolutePath().normalize();

    @GetMapping
    public ResponseEntity<List<Engin>> getAllEngins() {
        List<Engin> engins = enginService.getAllEngins();
        return ResponseEntity.ok(engins);
    }

    @GetMapping("/engin/{id}")
    public ResponseEntity<Engin> getEnginById(@PathVariable Long id) {
        Optional<Engin> engin = enginService.getEnginById(id);
        return engin.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Engin> addEngin(
            @RequestParam("image") MultipartFile file,
            @RequestParam("code") String code,
            @RequestParam("matricule") String matricule,
            @RequestParam("compteurHoraire") String compteurHoraire,
            @RequestParam("etatFrein") String etatFrein,
            @RequestParam("etatBatterie") String etatBatterie,
            @RequestParam("etatEclairage") String etatEclairage,
            @RequestParam("etatEssuieGlace") String etatEssuieGlace,
            @RequestParam("etatTracteur") String etatTracteur,
            @RequestParam("etatPneumatique") String etatPneumatique,
            @RequestParam("etatTransmission") String etatTransmission,
            @RequestParam("etatFreinService") String etatFreinService,
            @RequestParam("etatFreinParking") String etatFreinParking,
            @RequestParam("etatKlaxon") String etatKlaxon,
            @RequestParam("etatCablage") String etatCablage,
            @RequestParam("etatVitesse") String etatVitesse,
            @RequestParam("observationsGenerales") String observationsGenerales,
            @RequestParam("categorieEnginId") String categorieEnginId) {

        Engin engin = new Engin();
        engin.setCode(code);
        engin.setMatricule(matricule);
        engin.setCompteurHoraire(compteurHoraire);
        engin.setEtatFrein(etatFrein);
        engin.setEtatBatterie(etatBatterie);
        engin.setEtatEclairage(etatEclairage);
        engin.setEtatEssuieGlace(etatEssuieGlace);
        engin.setEtatTracteur(etatTracteur);
        engin.setEtatPneumatique(etatPneumatique);
        engin.setEtatTransmission(etatTransmission);
        engin.setEtatFreinService(etatFreinService);
        engin.setEtatFreinParking(etatFreinParking);
        engin.setEtatKlaxon(etatKlaxon);
        engin.setEtatCablage(etatCablage);
        engin.setEtatVitesse(etatVitesse);
        engin.setObservationsGenerales(observationsGenerales);
        engin.setCategorieEnginId(categorieEnginId);

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                engin.setImage(fileName);
                logger.info("File uploaded successfully: " + fileName);
            } catch (IOException e) {
                logger.error("Failed to upload file", e);
                return ResponseEntity.status(500).body(null);
            }
        } else {
            logger.warn("No file uploaded");
        }

        Engin savedEngin = enginService.addEngin(engin);
        return ResponseEntity.ok(savedEngin);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez sélectionner un fichier à télécharger.");
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
            return ResponseEntity.ok("Fichier téléchargé avec succès : " + file.getOriginalFilename());
        } catch (Exception e) {
            logger.error("Erreur lors du téléchargement du fichier : ", e);
            return ResponseEntity.status(500).body("Erreur lors du téléchargement du fichier.");
        }
    }
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        logger.info("Request to download file: " + filename);
        try {
            Path filePath = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                logger.info("File found: " + filename);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                logger.error("File not found or not readable: " + filename);
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            logger.error("Error: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
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