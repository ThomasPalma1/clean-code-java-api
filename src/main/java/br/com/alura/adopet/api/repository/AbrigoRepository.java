package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbrigoRepository extends JpaRepository<Shelter, Long> {
    boolean existsByNome(String nome);

    boolean existsByTelefone(String telefone);

    boolean existsByEmail(String email);

    Shelter findByNome(String nome);
}
