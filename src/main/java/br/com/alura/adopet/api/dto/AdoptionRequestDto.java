package br.com.alura.adopet.api.dto;

public record AdoptionRequestDto(Long idPet, Long idTutor, String reason) {
}
