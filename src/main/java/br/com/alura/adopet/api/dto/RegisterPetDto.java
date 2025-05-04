package br.com.alura.adopet.api.dto;


import br.com.alura.adopet.api.model.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterPetDto(
        @NotNull
        PetType petType,

        @NotBlank
        String petName,

        @NotBlank
        String petBreed,

        @NotNull
        Integer petAge,

        @NotBlank
        String petColor,

        @NotNull
        Float petWeight) {
}
