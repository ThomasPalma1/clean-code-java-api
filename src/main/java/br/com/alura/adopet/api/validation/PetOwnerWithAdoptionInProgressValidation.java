package br.com.alura.adopet.api.validation;


import br.com.alura.adopet.api.dto.AdoptionRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Adoption;
import br.com.alura.adopet.api.model.AdoptionStatus;
import br.com.alura.adopet.api.model.PetOwner;
import br.com.alura.adopet.api.repository.AdoptionRepository;
import br.com.alura.adopet.api.repository.PetOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetOwnerWithAdoptionInProgressValidation implements AdoptionRequestValidation {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public void validate(AdoptionRequestDto dto) {
        List<Adoption> adoptions = adoptionRepository.findAll();

        PetOwner petOwner = petOwnerRepository.getReferenceById(dto.idPetOwner());

        for (Adoption a : adoptions) {
            if (a.getPetOwner() == petOwner && a.getStatus() == AdoptionStatus.PENDING_REVIEW) {
                throw new ValidationException("Tutor already has another adoption awaiting evaluation!");
            }
        }
    }
}
