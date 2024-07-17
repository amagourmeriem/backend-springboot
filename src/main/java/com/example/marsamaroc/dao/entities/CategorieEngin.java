package com.example.marsamaroc.dao.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories_engins")
public class CategorieEngin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "categorieEngin")
    private List<Engin> engins;
}
