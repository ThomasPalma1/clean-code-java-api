package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.PetOwnerUpdateDto;
import br.com.alura.adopet.api.dto.RegisterOwnerDto;
import br.com.alura.adopet.api.exception.ValidationException;

import br.com.alura.adopet.api.model.PetOwner;
import br.com.alura.adopet.api.repository.PetOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetOwnerService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public void register(RegisterOwnerDto registerOwnerDto) {
        boolean alreadyRegistered = petOwnerRepository
                .existsByPetOwnerPhoneNumberOrPetOwnerEmail(
                        registerOwnerDto.petOwnerPhoneNumber(),
                        registerOwnerDto.petOwnerEmail());

        if (alreadyRegistered) {
            throw new ValidationException("Data already registered for another pet owner!");
        }

        petOwnerRepository.save(new PetOwner(registerOwnerDto));
    }

    public void update(PetOwnerUpdateDto petOwnerUpdateDto) {
        PetOwner petOwner = petOwnerRepository.getReferenceById(petOwnerUpdateDto.id());
        petOwner.updateData(petOwnerUpdateDto);
    }

}