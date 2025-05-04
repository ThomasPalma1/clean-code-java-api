package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    Optional<Shelter> findByName(String nome);

    boolean existsByNameOrPhoneOrEmail(
            String shelterName,
            String shelterPhoneNumber,
            String shelterEmail);

}
