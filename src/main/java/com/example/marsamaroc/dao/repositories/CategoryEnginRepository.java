package com.example.marsamaroc.dao.repositories;


import com.example.marsamaroc.dao.entities.CategorieEngin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryEnginRepository extends JpaRepository<CategorieEngin, Long> {
}
