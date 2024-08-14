package com.example.marsamaroc.dao.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Affectations")
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_entree", nullable = false)
    private Date dateEntree;

    @ManyToOne
    @JoinColumn(name = "engin_id", nullable = false)
    private Engin engin;

    @OneToOne
    @JoinColumn(name = "demande_id", nullable = false)
    private Demande demande;

    @Column(name = "observation_entree")
    private String observationEntree;


}
