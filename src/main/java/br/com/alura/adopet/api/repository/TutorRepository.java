package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<PetOwner, Long> {

    boolean existsByTelefone(String telefone);

    boolean existsByEmail(String email);

}
