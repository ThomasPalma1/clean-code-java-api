package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {

    boolean existsByPhoneOrEmail(String petOwnerPhoneNumber, String petOwnerEmail);

}