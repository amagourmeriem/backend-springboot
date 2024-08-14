package com.example.marsamaroc.dao.repositories;

import com.example.marsamaroc.dao.entities.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffectationRepository extends JpaRepository<Affectation, Long> {
}
