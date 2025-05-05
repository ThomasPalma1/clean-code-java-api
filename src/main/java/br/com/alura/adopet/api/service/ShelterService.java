package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.dto.RegisterShelterDto;
import br.com.alura.adopet.api.dto.ShelterDto;
import br.com.alura.adopet.api.model.Shelter;
import br.com.alura.adopet.api.repository.ShelterRepository;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private PetRepository petRepository;

    public List<ShelterDto> list() {
        return shelterRepository
                .findAll()
                .stream()
                .map(ShelterDto::new)
                .toList();
    }

    public void register(RegisterShelterDto registerShelterDto) {
        boolean alreadyRegistered = shelterRepository.existsByShelterNameOrShelterPhoneNumberOrShelterEmail(
                registerShelterDto.shelterName(),
                registerShelterDto.shelterPhoneNumber(),
                registerShelterDto.shelterEmail()
        );

        if (alreadyRegistered) {
            throw new ValidationException("Information already registered for another shelter!");
        }

        shelterRepository.save(
                new Shelter(registerShelterDto));
    }

    public List<PetDto> listShelterPets(String idOrName) {
        Shelter shelter = loadShelter(idOrName);

        return petRepository
                .findByShelter(shelter)
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public Shelter loadShelter(String idOuNome) {
        Optional<Shelter> optional;
        try {
            Long id = Long.parseLong(idOuNome);
            optional = shelterRepository.findById(id);
        } catch (NumberFormatException exception) {
            optional = shelterRepository.findByShelterName(idOuNome);
        }

        return optional.orElseThrow(() -> new ValidationException("Shelter not found."));
    }

}