package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dtos.EnginDto;
import com.example.marsamaroc.service.EnginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/engins")
@RequiredArgsConstructor
public class EnginController {

    private final EnginService enginService;
    private static final Logger logger = LoggerFactory.getLogger(EnginController.class);
    private static final String UPLOADED_FOLDER = "C:/uploads/";

    private final Path fileStorageLocation = Paths.get("C:/uploads").toAbsolutePath().normalize();


    @GetMapping
    public ResponseEntity<List<EnginDto>> getAllEngins() {
        List<EnginDto> engins = enginService.getAllEngins();
        return ResponseEntity.ok(engins);
    }

    @GetMapping("/engin/{id}")
    public ResponseEntity<EnginDto> getEnginById(@PathVariable Long id) {
        EnginDto enginDto = enginService.getEnginById(id);
        return ResponseEntity.ok(enginDto);
    }

    @PostMapping
    public ResponseEntity<EnginDto> addEngin(
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
            @RequestParam("categorieEnginId") Long categorieEnginId) {

        EnginDto enginDto = new EnginDto();
        enginDto.setCode(code);
        enginDto.setMatricule(matricule);
        enginDto.setCompteurHoraire(compteurHoraire);
        enginDto.setEtatFrein(etatFrein);
        enginDto.setEtatBatterie(etatBatterie);
        enginDto.setEtatEclairage(etatEclairage);
        enginDto.setEtatEssuieGlace(etatEssuieGlace);
        enginDto.setEtatTracteur(etatTracteur);
        enginDto.setEtatPneumatique(etatPneumatique);
        enginDto.setEtatTransmission(etatTransmission);
        enginDto.setEtatFreinService(etatFreinService);
        enginDto.setEtatFreinParking(etatFreinParking);
        enginDto.setEtatKlaxon(etatKlaxon);
        enginDto.setEtatCablage(etatCablage);
        enginDto.setEtatVitesse(etatVitesse);
        enginDto.setObservationsGenerales(observationsGenerales);
        enginDto.setCategorieEnginId(categorieEnginId);

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                enginDto.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Ajoutez un message d'erreur approprié ici
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // Gérez le cas où le fichier est manquant ou vide
            enginDto.setImage(null);
        }

        EnginDto savedEngin = enginService.addEngin(enginDto);
        return ResponseEntity.ok(savedEngin);
    }




    @PutMapping("/update/{id}")
    public ResponseEntity<EnginDto> updateEngin(
            @PathVariable Long id,
            @RequestParam("code") String code,
            @RequestParam("matricule") String matricule,
            @RequestParam("compteurHoraire") String compteurHoraire,
            @RequestParam("categorieEnginId") Long categorieEnginId,
            @RequestParam(value = "image", required = false) MultipartFile file,
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
            @RequestParam("observationsGenerales") String observationsGenerales) {

        EnginDto enginDto = new EnginDto();
        enginDto.setCode(code);
        enginDto.setMatricule(matricule);
        enginDto.setCompteurHoraire(compteurHoraire);
        enginDto.setCategorieEnginId(categorieEnginId);
        enginDto.setEtatFrein(etatFrein);
        enginDto.setEtatBatterie(etatBatterie);
        enginDto.setEtatEclairage(etatEclairage);
        enginDto.setEtatEssuieGlace(etatEssuieGlace);
        enginDto.setEtatTracteur(etatTracteur);
        enginDto.setEtatPneumatique(etatPneumatique);
        enginDto.setEtatTransmission(etatTransmission);
        enginDto.setEtatFreinService(etatFreinService);
        enginDto.setEtatFreinParking(etatFreinParking);
        enginDto.setEtatKlaxon(etatKlaxon);
        enginDto.setEtatCablage(etatCablage);
        enginDto.setEtatVitesse(etatVitesse);
        enginDto.setObservationsGenerales(observationsGenerales);

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path path = Paths.get("uploads/" + fileName);
                Files.copy(file.getInputStream(), path);
                enginDto.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        EnginDto updatedEngin = enginService.updateEngin(id, enginDto);
        return ResponseEntity.ok(updatedEngin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEngin(@PathVariable Long id) {
        enginService.deleteEngin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
