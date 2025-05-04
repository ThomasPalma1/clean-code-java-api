package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterShelterDto(
        @NotBlank
        String shelterName,

        @NotBlank
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String shelterPhoneNumber,

        @NotBlank
        @Email
        String shelterEmail) {
}
