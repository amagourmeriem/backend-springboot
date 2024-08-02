package com.example.marsamaroc.dao.repositories;

import com.example.marsamaroc.dao.entities.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByNomDemandeur(String nomDemandeur);

    List<Demande> findByDateSortieBetween(Date startDate, Date endDate);
}
