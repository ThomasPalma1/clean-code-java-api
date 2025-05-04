package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Shelter;

public record ShelterDto(Long id, String shelterName) {

    public ShelterDto(Shelter shelter) {
        this(shelter.getId(), shelter.getShelterName());
    }

}
