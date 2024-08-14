package com.example.marsamaroc.dao.repositories;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByNomDemandeur(String nomDemandeur);

    List<Demande> findByDateSortieBetween(Date startDate, Date endDate);

    List<Demande> findByUserId(Long userId);
    List<Demande> findByUser(User user);

    List<Demande> findByAffecteFalse();
}

