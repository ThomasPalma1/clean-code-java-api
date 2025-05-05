package br.com.alura.adopet.api.repository;


import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByPetIsAdoptedFalse();

    List<Pet> findByShelter(Shelter shelter);
}
