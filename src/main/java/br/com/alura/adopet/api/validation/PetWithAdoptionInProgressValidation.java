package br.com.alura.adopet.api.validation;


import br.com.alura.adopet.api.dto.AdoptionRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Adoption;
import br.com.alura.adopet.api.model.AdoptionStatus;
import br.com.alura.adopet.api.model.Pet;

import br.com.alura.adopet.api.repository.AdoptionRepository;

import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetWithAdoptionInProgressValidation implements AdoptionRequestValidation {

    @Autowired
    private AdoptionRepository adoptionRepository;

    public void validate(AdoptionRequestDto dto) {

        boolean petHasAdoptionInProgress = adoptionRepository
                .existsByPetIdAndStatus(dto.idPet(),
                        AdoptionStatus.PENDING_REVIEW);
        if (petHasAdoptionInProgress) {
            throw new ValidationException("Pet is now awaiting evaluation to be adopted!");
        }
    }
}
