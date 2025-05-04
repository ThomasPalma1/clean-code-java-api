package br.com.alura.adopet.api.dto;


import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.PetType;

public record PetDto(
        Long id,
        PetType petType,
        String petName,
        String petBreed,
        Integer petAge) {

    public PetDto(Pet pet) {
        this(
                pet.getId(),
                pet.getPetType(),
                pet.getPetName(),
                pet.getPetBreed(),
                pet.getPetAge()
        );
    }
}
