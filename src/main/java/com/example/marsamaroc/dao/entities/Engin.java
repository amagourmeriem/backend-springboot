package com.example.marsamaroc.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "engins")
public class Engin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "matricule", nullable = false)
    private String matricule;

    @Column(name = "compteur_horaire")
    private int compteurHoraire;

    @Column(name = "etat_frein")
    private String etatFrein;

    @Column(name = "etat_batterie")
    private String etatBatterie;

    @Column(name = "etat_eclairage")
    private String etatEclairage;

    @Column(name = "etat_essuie_glace")
    private String etatEssuieGlace;

    @Column(name = "etat_tracteur")
    private String etatTracteur;

    @Column(name = "etat_pneumatique")
    private String etatPneumatique;

    @Column(name = "etat_transmission")
    private String etatTransmission;

    @Column(name = "etat_frein_service")
    private String etatFreinService;

    @Column(name = "etat_frein_parking")
    private String etatFreinParking;

    @Column(name = "etat_klaxon")
    private String etatKlaxon;

    @Column(name = "etat_cablage")
    private String etatCablage;

    @Column(name = "etat_vitesse")
    private String etatVitesse;

    @Column(name = "observations_generales")
    private String observationsGenerales;

    @ManyToOne
    @JoinColumn(name = "categorie_engin_id")
    private CategorieEngin categorieEngin;
}