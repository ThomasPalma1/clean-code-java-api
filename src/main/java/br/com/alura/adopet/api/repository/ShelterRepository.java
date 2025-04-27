package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    boolean existsByName(String name);

    boolean existsByTelephone(String telephone);

    boolean existsByEmail(String email);

    Shelter findByName(String name);
}
