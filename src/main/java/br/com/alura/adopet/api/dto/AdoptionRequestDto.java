package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdoptionRequestDto(@NotNull Long idPet,
                                 @NotNull Long idPetOwner,
                                 @NotBlank String reason) {
}
