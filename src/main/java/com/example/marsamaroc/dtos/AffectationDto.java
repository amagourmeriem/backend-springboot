package com.example.marsamaroc.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AffectationDto {

    private Long id;
    private Date dateEntree;
    private Long enginId;
    private String enginCode;
    private String observationEntree;
    private Long demandeId;
    private boolean affecte;

}
