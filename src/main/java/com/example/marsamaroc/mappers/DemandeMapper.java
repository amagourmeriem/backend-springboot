package com.example.marsamaroc.mappers;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dtos.DemandeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DemandeMapper {
    DemandeMapper INSTANCE = Mappers.getMapper(DemandeMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "login")
    @Mapping(source = "categorieEngin.id", target = "categorieEnginId")
    DemandeDto demandeToDemandeDto(Demande demande);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "login", target = "user.login")
    @Mapping(source = "categorieEnginId", target = "categorieEngin.id")
    Demande demandeDtoToDemande(DemandeDto demandeDto);
}
