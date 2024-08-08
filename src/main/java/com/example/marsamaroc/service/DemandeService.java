package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.entities.User;
import com.example.marsamaroc.dao.repositories.CategoryEnginRepository;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dao.repositories.UserRepository;
import com.example.marsamaroc.dtos.DemandeDto;
import com.example.marsamaroc.mappers.DemandeMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class DemandeService implements DemandeManager {

    private final DemandeMapperUtil demandeMapperUtil;
    private final DemandeRepository demandeRepository;
    private final UserRepository userRepository;

    private  final CategoryEnginRepository categoryEnginRepository;

    @Autowired
    public DemandeService(DemandeRepository demandeRepository, DemandeMapperUtil demandeMapperUtil, UserRepository userRepository, CategoryEnginRepository categoryEnginRepository) {
        this.demandeRepository = demandeRepository;
        this.demandeMapperUtil = demandeMapperUtil;
        this.userRepository = userRepository;
        this.categoryEnginRepository = categoryEnginRepository;
    }

    @Override
    public List<DemandeDto> getAllDemandes() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandeMapperUtil.toDemandeDtoList(demandes);
    }

    @Override
    public DemandeDto getDemandeById(Long id) {
        Optional<Demande> demande = demandeRepository.findById(id);
        return demande.map(demandeMapperUtil::toDemandeDto).orElse(null);
    }
    @Override
    public Demande createDemande(DemandeDto demandeDto) {
        // Obtenir l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginInfo = authentication.getName();
        System.out.println("Authentication Name (login): " + loginInfo);

// Extraire l'ID de la chaîne
        Long userId = extractUserIdFromLoginInfo(loginInfo);
        System.out.println("User ID: " + userId);

// Trouver l'utilisateur par son ID
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Trouver la catégorie d'engin par son ID
        CategorieEngin categorieEngin = categoryEnginRepository.findById(demandeDto.getCategorieEnginId())
                .orElseThrow(() -> new RuntimeException("CategorieEngin not found"));

        // Créer la demande
        Demande demande = new Demande();
        demande.setNumeroBCI(demandeDto.getNumeroBCI());
        demande.setNomDepartement(demandeDto.getNomDepartement());
        demande.setNomDemandeur(user.getFirstName() + " " + user.getLastName()); // Utiliser le nom de l'utilisateur connecté
        demande.setDateSortie(demandeDto.getDateSortie());
        demande.setShift(demandeDto.getShift());
        demande.setObservations(demandeDto.getObservations());
        demande.setCategorieEngin(categorieEngin); // Utiliser l'objet CategorieEngin
        demande.setSaul(demandeDto.getSaul());
        demande.setUser(user); // Associe l'utilisateur à la demande

        // Sauvegarder la demande
        return demandeRepository.save(demande);
    }

    private Long extractUserIdFromLoginInfo(String loginInfo) {
        // Rechercher la position de "id="
        int idStart = loginInfo.indexOf("id=") + 3;
        int idEnd = loginInfo.indexOf(",", idStart);
        String idString = loginInfo.substring(idStart, idEnd);
        return Long.parseLong(idString);
    }

    @Override
    public boolean deleteDemande(Long id) {
        if (demandeRepository.existsById(id)) {
            demandeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<DemandeDto> getDemandesByUser(Long userId) {
        List<Demande> demandes = demandeRepository.findByUserId(userId);
        return demandeMapperUtil.toDemandeDtoList(demandes);
    }

    public DemandeDto updateDemande(Long id, DemandeDto demandeDto) {
        // Vérifiez si la demande existe
        Demande existingDemande = demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande not found"));
        System.out.println("Valeur de dateSortie dans DemandeDto : " + demandeDto.getDateSortie());

        // Mettre à jour les champs de la demande existante avec les valeurs du DTO
        existingDemande.setNumeroBCI(demandeDto.getNumeroBCI());
        existingDemande.setNomDepartement(demandeDto.getNomDepartement());
        existingDemande.setNomDemandeur(demandeDto.getNomDemandeur());
        existingDemande.setDateSortie(demandeDto.getDateSortie());
        existingDemande.setShift(demandeDto.getShift());
        existingDemande.setObservations(demandeDto.getObservations());
        existingDemande.setStatus(demandeDto.getStatus());

        // Si le DTO contient des informations sur l'utilisateur, mettez-les à jour
        if (demandeDto.getUserId() != null) {
            User user = userRepository.findById(demandeDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingDemande.setUser(user);
        }

        // Sauvegarder la demande mise à jour
        Demande savedDemande = demandeRepository.save(existingDemande);

        // Retourner le DTO de la demande mise à jour
        return demandeMapperUtil.toDemandeDto(savedDemande);
    }
}