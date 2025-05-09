package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.AdoptionRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AvailablePetValidation implements AdoptionRequestValidation {

    @Autowired
    private PetRepository petRepository;

    public void validate(AdoptionRequestDto dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());
        if (pet.getPetIsAdopted()) {
            throw new ValidationException("Pet has already been adopted!");

        }
    }
}
