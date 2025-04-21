package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdoptionDisapprovalDto(@NotNull Long idAdoption,
                                     @NotBlank String reason) {
}
