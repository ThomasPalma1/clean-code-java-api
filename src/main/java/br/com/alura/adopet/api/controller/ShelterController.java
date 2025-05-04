package br.com.alura.adopet.api.controller;


import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.dto.RegisterPetDto;
import br.com.alura.adopet.api.dto.RegisterShelterDto;
import br.com.alura.adopet.api.dto.ShelterDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Shelter;
import br.com.alura.adopet.api.service.PetService;
import br.com.alura.adopet.api.service.ShelterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelters")
public class ShelterController {

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<ShelterDto>> list() {
        List<ShelterDto> shelters = shelterService.list();
        return ResponseEntity.ok(shelters);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterShelterDto registerShelterDto) {
        try {
            shelterService.register(registerShelterDto);
            return ResponseEntity.ok().build();
        } catch (ValidationException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/{idOrName}/pets")
    public ResponseEntity<List<PetDto>> listPets(@PathVariable String idOrName) {
        try {
            List<PetDto> shelterPets = shelterService.listShelterPets(idOrName);
            return ResponseEntity.ok(shelterPets);
        } catch (ValidationException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idOrName}/pets")
    @Transactional
    public ResponseEntity<String> registerPet(
            @PathVariable String idOrName,
            @RequestBody @Valid RegisterPetDto registerPetDto) {
        try {
            Shelter shelter = shelterService.loadShelter(idOrName);
            petService.registerPet(shelter, registerPetDto);
            return ResponseEntity.ok().build();
        } catch (ValidationException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}
