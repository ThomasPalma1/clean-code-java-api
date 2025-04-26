package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adoption;
import br.com.alura.adopet.api.model.AdoptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    boolean existsByPetIdAndStatus(Long idPet, AdoptionStatus status);

}
