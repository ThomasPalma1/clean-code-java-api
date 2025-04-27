package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.Shelter;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.ShelterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class ShelterController {

    @Autowired
    private ShelterRepository repository;

    @GetMapping
    public ResponseEntity<List<Shelter>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid Shelter shelter) {
        boolean nomeJaCadastrado = repository.existsByName(shelter.getNome());
        boolean telefoneJaCadastrado = repository.existsByTelephone(shelter.getTelefone());
        boolean emailJaCadastrado = repository.existsByEmail(shelter.getEmail());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            return ResponseEntity.badRequest().body("Dados já cadastrados para outro abrigo!");
        } else {
            repository.save(shelter);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<Pet>> listarPets(@PathVariable String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            List<Pet> pets = repository.getReferenceById(id).getPets();
            return ResponseEntity.ok(pets);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = repository.findByName(idOuNome).getPets();
                return ResponseEntity.ok(pets);
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid Pet pet) {
        try {
            Long id = Long.parseLong(idOuNome);
            Shelter shelter = repository.getReferenceById(id);
            pet.setAbrigo(shelter);
            pet.setAdotado(false);
            shelter.getPets().add(pet);
            repository.save(shelter);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException nfe) {
            try {
                Shelter shelter = repository.findByName(idOuNome);
                pet.setAbrigo(shelter);
                pet.setAdotado(false);
                shelter.getPets().add(pet);
                repository.save(shelter);
                return ResponseEntity.ok().build();
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

}
