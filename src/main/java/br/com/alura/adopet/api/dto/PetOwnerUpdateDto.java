package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PetOwnerUpdateDto(
        @NotNull
        Long id,
        @NotBlank
        String petOwnerName,
        @NotBlank
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String petOwnerPhoneNumber,
        @NotBlank
        @Email
        String petOwnerEmail) {
}