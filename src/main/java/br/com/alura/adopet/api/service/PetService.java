package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.dto.RegisterPetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Shelter;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;


    public List<PetDto> searchAvailablePet() {
        return repository
                .findAllByAdoptedFalse()
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public void registerPet(Shelter shelter, RegisterPetDto dto){
        repository.save(new Pet(dto, shelter));
    }
}
