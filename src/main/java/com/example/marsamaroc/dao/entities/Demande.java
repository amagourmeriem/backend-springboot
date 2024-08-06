package com.example.marsamaroc.dao.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroBCI;

    @Column(nullable = false)
    private String nomDepartement;

    @Column(nullable = false)
    private String nomDemandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_engin_id")
    private CategorieEngin categorieEngin;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateSortie;

    @Column(nullable = false)
    private String shift;

    @Column(nullable = false)
    private String saul;

    @Column(length = 1000) // Exemple pour la longueur des observations
    private String observations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engin_id")
    private Engin engin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status= Status.PENDING;
}

