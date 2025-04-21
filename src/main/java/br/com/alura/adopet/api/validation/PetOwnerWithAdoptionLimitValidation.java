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
public class PetOwnerWithAdoptionLimitValidation {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    private void validate(AdoptionRequestDto dto) {
        List<Adoption> adoptions = adoptionRepository.findAll();
        PetOwner petOwner = petOwnerRepository.getReferenceById(dto.idPetOwner());
        for (Adoption a : adoptions) {
            int contador = 0;
            if (a.getPetOwner() == petOwner && a.getStatus() == AdoptionStatus.APPROVED) {
                contador = contador + 1;
            }
            if (contador == 5) {
                throw new ValidationException("Tutor has reached the maximum limit of 5 adoptions!");
            }
        }
    }
}
