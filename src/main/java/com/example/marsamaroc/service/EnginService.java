package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.*;
import com.example.marsamaroc.dao.repositories.AffectationRepository;
import com.example.marsamaroc.dao.repositories.CategoryEnginRepository;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dtos.EnginDto;
import com.example.marsamaroc.dao.repositories.EnginRepository;
import com.example.marsamaroc.exception.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.marsamaroc.exception.ResourceNotFoundException;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnginService {

    private final EnginRepository enginRepository;
    private final CategoryEnginRepository categoryEnginRepository;
    private  final DemandeRepository demandeRepository;
    private final AffectationRepository affectationRepository;


    public EnginDto addEngin(EnginDto enginDto) {
        // Convertir le DTO en entité
        Engin engin = convertToEntity(enginDto);

        // Vérifier si la catégorie de l'engin est nouvelle et a besoin d'être sauvegardée
        CategorieEngin categorieEngin = engin.getCategorieEngin();
        if (categorieEngin != null && categorieEngin.getId() == null) {
            // Sauvegarder la catégorie d'engin
            categorieEngin = categoryEnginRepository.save(categorieEngin);
            engin.setCategorieEngin(categorieEngin); // Mettre à jour la référence de l'entité
        }

        // Sauvegarder l'engin
        Engin savedEngin = enginRepository.save(engin);

        // Convertir l'entité sauvegardée en DTO
        return convertToDto(savedEngin);
    }
    public EnginDto getEnginById(Long id) {
        Engin engin = enginRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engin not found"));
        return mapToDto(engin);
    }

    public List<EnginDto> getAllEngins() {
        List<Engin> engins = enginRepository.findAll();
        return engins.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    // Exemple de méthode mapToDto
    private EnginDto mapToDto(Engin engin) {
        EnginDto enginDto = new EnginDto();
        enginDto.setId(engin.getId());
        enginDto.setCode(engin.getCode());
        enginDto.setMatricule(engin.getMatricule());
        enginDto.setCompteurHoraire(engin.getCompteurHoraire());
        enginDto.setEtatFrein(engin.getEtatFrein());
        enginDto.setEtatBatterie(engin.getEtatBatterie());
        enginDto.setEtatEclairage(engin.getEtatEclairage());
        enginDto.setEtatEssuieGlace(engin.getEtatEssuieGlace());
        enginDto.setEtatTracteur(engin.getEtatTracteur());
        enginDto.setEtatPneumatique(engin.getEtatPneumatique());
        enginDto.setEtatTransmission(engin.getEtatTransmission());
        enginDto.setEtatFreinService(engin.getEtatFreinService());
        enginDto.setEtatFreinParking(engin.getEtatFreinParking());
        enginDto.setEtatKlaxon(engin.getEtatKlaxon());
        enginDto.setEtatCablage(engin.getEtatCablage());
        enginDto.setEtatVitesse(engin.getEtatVitesse());
        enginDto.setObservationsGenerales(engin.getObservationsGenerales());
        enginDto.setCategorieEnginId(engin.getCategorieEngin() != null ? engin.getCategorieEngin().getId() : null);
        enginDto.setCategorieEnginNom(engin.getCategorieEngin() != null ? engin.getCategorieEngin().getNom() : null);
        enginDto.setImage(engin.getImage());
        return enginDto;
    }
    public EnginDto updateEngin(Long id, EnginDto enginDto) {
        Engin engin = enginRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engin not found"));

        engin.setCode(enginDto.getCode());
        engin.setMatricule(enginDto.getMatricule());
        engin.setCompteurHoraire(enginDto.getCompteurHoraire());
        engin.setEtatFrein(enginDto.getEtatFrein());
        engin.setEtatBatterie(enginDto.getEtatBatterie());
        engin.setEtatEclairage(enginDto.getEtatEclairage());
        engin.setEtatEssuieGlace(enginDto.getEtatEssuieGlace());
        engin.setEtatTracteur(enginDto.getEtatTracteur());
        engin.setEtatPneumatique(enginDto.getEtatPneumatique());
        engin.setEtatTransmission(enginDto.getEtatTransmission());
        engin.setEtatFreinService(enginDto.getEtatFreinService());
        engin.setEtatFreinParking(enginDto.getEtatFreinParking());
        engin.setEtatKlaxon(enginDto.getEtatKlaxon());
        engin.setEtatCablage(enginDto.getEtatCablage());
        engin.setEtatVitesse(enginDto.getEtatVitesse());
        engin.setObservationsGenerales(enginDto.getObservationsGenerales());

        if (enginDto.getCategorieEnginId() != null) {
            CategorieEngin categorie = categoryEnginRepository.findById(enginDto.getCategorieEnginId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            engin.setCategorieEngin(categorie);
        }

        if (enginDto.getImage() != null) {
            engin.setImage(enginDto.getImage());
        }

        engin = enginRepository.save(engin);
        return mapToDto(engin);
    }



    public void deleteEngin(Long id) {
        enginRepository.deleteById(id);
    }

    public Engin findById(Long id) {
        return enginRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Engin not found for id: " + id));
    }

    public EnginDto convertToDto(Engin engin) {
        EnginDto dto = new EnginDto();
        dto.setId(engin.getId());
        dto.setCode(engin.getCode());
        dto.setMatricule(engin.getMatricule());

        // Assurez-vous que compteurHoraire est converti en Integer
        try {
            dto.setCompteurHoraire(engin.getCompteurHoraire());
        } catch (NumberFormatException e) {
            dto.setCompteurHoraire(null); // ou une valeur par défaut appropriée
        }
        if (engin.getCategorieEngin() != null) {
            dto.setCategorieEnginNom(engin.getCategorieEngin().getNom());
            System.out.println("Categorie Engin Nom: " + engin.getCategorieEngin().getNom());
        } else {
            dto.setCategorieEnginNom("N/A");
        }
        dto.setImage(engin.getImage());
        dto.setEtatFrein(engin.getEtatFrein());
        dto.setEtatBatterie(engin.getEtatBatterie());
        dto.setEtatEclairage(engin.getEtatEclairage());
        dto.setEtatEssuieGlace(engin.getEtatEssuieGlace());
        dto.setEtatTracteur(engin.getEtatTracteur());
        dto.setEtatPneumatique(engin.getEtatPneumatique());
        dto.setEtatTransmission(engin.getEtatTransmission());
        dto.setEtatFreinService(engin.getEtatFreinService());
        dto.setEtatFreinParking(engin.getEtatFreinParking());
        dto.setEtatKlaxon(engin.getEtatKlaxon());
        dto.setEtatCablage(engin.getEtatCablage());
        dto.setEtatVitesse(engin.getEtatVitesse());
        dto.setObservationsGenerales(engin.getObservationsGenerales());

        return dto;
    }


    private Engin convertToEntity(EnginDto enginDto) {
        Engin engin = new Engin();
        engin.setCode(enginDto.getCode());
        engin.setMatricule(enginDto.getMatricule());
        engin.setCompteurHoraire(enginDto.getCompteurHoraire());
        engin.setEtatFrein(enginDto.getEtatFrein());
        engin.setEtatBatterie(enginDto.getEtatBatterie());
        engin.setEtatEclairage(enginDto.getEtatEclairage());
        engin.setEtatEssuieGlace(enginDto.getEtatEssuieGlace());
        engin.setEtatTracteur(enginDto.getEtatTracteur());
        engin.setEtatPneumatique(enginDto.getEtatPneumatique());
        engin.setEtatTransmission(enginDto.getEtatTransmission());
        engin.setEtatFreinService(enginDto.getEtatFreinService());
        engin.setEtatFreinParking(enginDto.getEtatFreinParking());
        engin.setEtatKlaxon(enginDto.getEtatKlaxon());
        engin.setEtatCablage(enginDto.getEtatCablage());
        engin.setEtatVitesse(enginDto.getEtatVitesse());
        engin.setObservationsGenerales(enginDto.getObservationsGenerales());
        // Vous devez récupérer la catégorie d'engin avec l'ID
        engin.setCategorieEngin(getCategorieEnginById(enginDto.getCategorieEnginId()));
        engin.setImage(enginDto.getImage());
        return engin;
    }

    private void updateEntityFromDto(Engin engin, EnginDto enginDto) {
        engin.setCode(enginDto.getCode());
        engin.setMatricule(enginDto.getMatricule());
        engin.setCompteurHoraire(enginDto.getCompteurHoraire());
        engin.setEtatFrein(enginDto.getEtatFrein());
        engin.setEtatBatterie(enginDto.getEtatBatterie());
        engin.setEtatEclairage(enginDto.getEtatEclairage());
        engin.setEtatEssuieGlace(enginDto.getEtatEssuieGlace());
        engin.setEtatTracteur(enginDto.getEtatTracteur());
        engin.setEtatPneumatique(enginDto.getEtatPneumatique());
        engin.setEtatTransmission(enginDto.getEtatTransmission());
        engin.setEtatFreinService(enginDto.getEtatFreinService());
        engin.setEtatFreinParking(enginDto.getEtatFreinParking());
        engin.setEtatKlaxon(enginDto.getEtatKlaxon());
        engin.setEtatCablage(enginDto.getEtatCablage());
        engin.setEtatVitesse(enginDto.getEtatVitesse());
        engin.setObservationsGenerales(enginDto.getObservationsGenerales());
        engin.setCategorieEngin(getCategorieEnginById(enginDto.getCategorieEnginId()));
        engin.setImage(enginDto.getImage());
    }

    private CategorieEngin getCategorieEnginById(Long id) {
        return categoryEnginRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategorieEngin not found with id " + id));
    }

    public void markEnginAsAvailable(Long enginId) {
        Engin engin = enginRepository.findById(enginId)
                .orElseThrow(() -> new ResourceNotFoundException("Engin non trouvé pour ID : " + enginId));

        engin.setStatus(EnginStatus.DISPONIBLE);  // Rendre l'engin disponible
        enginRepository.save(engin);
    }
    @Scheduled(cron = "0 0 0 * * ?")  // Exemple: tâche quotidienne à minuit
    public void checkAndUpdateEnginStatus() {
        List<Demande> demandes = demandeRepository.findAll();

        for (Demande demande : demandes) {
            LocalDate dateSortie = DateConverter.convertToLocalDate(demande.getDateSortie());
            if (dateSortie.isBefore(LocalDate.now())) {
                Engin engin = demande.getEngin();
                engin.setStatus(EnginStatus.DISPONIBLE);
                enginRepository.save(engin);
            }
        }
    }

    public List<EnginDto> findEnginsDisponibles() {
        List<Affectation> affectations = affectationRepository.findAll();
        Set<Long> enginsAffectes = affectations.stream()
                .map(a -> a.getEngin().getId())
                .collect(Collectors.toSet());

        return enginRepository.findAll().stream()
                .filter(e -> !enginsAffectes.contains(e.getId()))
                .map(e -> new EnginDto(
                        e.getId(),
                        e.getCode(),
                        e.getMatricule(),
                        e.getCompteurHoraire(),
                        e.getImage(),
                        e.getEtatFrein(),
                        e.getEtatBatterie(),
                        e.getEtatEclairage(),
                        e.getEtatEssuieGlace(),
                        e.getEtatTracteur(),
                        e.getEtatPneumatique(),
                        e.getEtatTransmission(),
                        e.getEtatFreinService(),
                        e.getEtatFreinParking(),
                        e.getEtatKlaxon(),
                        e.getEtatCablage(),
                        e.getEtatVitesse(),
                        e.getObservationsGenerales(),
                        e.getCategorieEngin().getId(),
                        e.getCategorieEngin().getNom()
                ))
                .collect(Collectors.toList());
    }

    }