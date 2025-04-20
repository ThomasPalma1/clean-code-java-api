package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.PetOwner;
import br.com.alura.adopet.api.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid PetOwner petOwner) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(petOwner.getTelefone());
        boolean emailJaCadastrado = repository.existsByEmail(petOwner.getEmail());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            return ResponseEntity.badRequest().body("Dados já cadastrados para outro tutor!");
        } else {
            repository.save(petOwner);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid PetOwner petOwner) {
        repository.save(petOwner);
        return ResponseEntity.ok().build();
    }

}
