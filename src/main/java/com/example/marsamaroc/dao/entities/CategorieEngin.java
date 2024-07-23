package com.example.marsamaroc.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories_engins")
public class CategorieEngin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String toString() {
        return "CategorieEngin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", engins=" + engins +
                '}';
    }

    private String nom;

    @OneToMany(mappedBy = "categorieEngin")
    private List<Engin> engins;
}
