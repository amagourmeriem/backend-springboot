package com.example.marsamaroc.dao.repositories;

import com.example.marsamaroc.dao.entities.Engin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnginRepository extends JpaRepository<Engin, Long> {
}
