package br.com.alura.adopet.api.dto;


import br.com.alura.adopet.api.model.PetType;

public record PetDto(
        Long id,
        PetType petType,
        String petName,
        String petBreed,
        Integer petAge) {
}
