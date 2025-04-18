package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

}
