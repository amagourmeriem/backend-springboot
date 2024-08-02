package com.example.marsamaroc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnginDto {

    private Long id;
    private String code;
    private String matricule;
    private String compteurHoraire;
    private String image;
    private String etatFrein;
    private String etatBatterie;
    private String etatEclairage;
    private String etatEssuieGlace;
    private String etatTracteur;
    private String etatPneumatique;
    private String etatTransmission;
    private String etatFreinService;
    private String etatFreinParking;
    private String etatKlaxon;
    private String etatCablage;
    private String etatVitesse;
    private String observationsGenerales;

    // Ajouter ces nouveaux attributs
    private Long categorieEnginId;
    private String categorieEnginNom;

    // Getters et Setters pour les attributs
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCompteurHoraire() {
        return compteurHoraire;
    }

    public void setCompteurHoraire(String compteurHoraire) {
        this.compteurHoraire = compteurHoraire;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEtatFrein() {
        return etatFrein;
    }

    public void setEtatFrein(String etatFrein) {
        this.etatFrein = etatFrein;
    }

    public String getEtatBatterie() {
        return etatBatterie;
    }

    public void setEtatBatterie(String etatBatterie) {
        this.etatBatterie = etatBatterie;
    }

    public String getEtatEclairage() {
        return etatEclairage;
    }

    public void setEtatEclairage(String etatEclairage) {
        this.etatEclairage = etatEclairage;
    }

    public String getEtatEssuieGlace() {
        return etatEssuieGlace;
    }

    public void setEtatEssuieGlace(String etatEssuieGlace) {
        this.etatEssuieGlace = etatEssuieGlace;
    }

    public String getEtatTracteur() {
        return etatTracteur;
    }

    public void setEtatTracteur(String etatTracteur) {
        this.etatTracteur = etatTracteur;
    }

    public String getEtatPneumatique() {
        return etatPneumatique;
    }

    public void setEtatPneumatique(String etatPneumatique) {
        this.etatPneumatique = etatPneumatique;
    }

    public String getEtatTransmission() {
        return etatTransmission;
    }

    public void setEtatTransmission(String etatTransmission) {
        this.etatTransmission = etatTransmission;
    }

    public String getEtatFreinService() {
        return etatFreinService;
    }

    public void setEtatFreinService(String etatFreinService) {
        this.etatFreinService = etatFreinService;
    }

    public String getEtatFreinParking() {
        return etatFreinParking;
    }

    public void setEtatFreinParking(String etatFreinParking) {
        this.etatFreinParking = etatFreinParking;
    }

    public String getEtatKlaxon() {
        return etatKlaxon;
    }

    public void setEtatKlaxon(String etatKlaxon) {
        this.etatKlaxon = etatKlaxon;
    }

    public String getEtatCablage() {
        return etatCablage;
    }

    public void setEtatCablage(String etatCablage) {
        this.etatCablage = etatCablage;
    }

    public String getEtatVitesse() {
        return etatVitesse;
    }

    public void setEtatVitesse(String etatVitesse) {
        this.etatVitesse = etatVitesse;
    }

    public String getObservationsGenerales() {
        return observationsGenerales;
    }

    public void setObservationsGenerales(String observationsGenerales) {
        this.observationsGenerales = observationsGenerales;
    }

    public Long getCategorieEnginId() {
        return categorieEnginId;
    }

    public void setCategorieEnginId(Long categorieEnginId) {
        this.categorieEnginId = categorieEnginId;
    }

    public String getCategorieEnginNom() {
        return categorieEnginNom;
    }

    public void setCategorieEnginNom(String categorieEnginNom) {
        this.categorieEnginNom = categorieEnginNom;
    }
}

