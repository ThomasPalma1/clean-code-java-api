package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.Adoption;

import br.com.alura.adopet.api.service.AdoptionService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/adoptions")
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> requestAdoption(@RequestBody @Valid Adoption adoption) {

        try {
            this.adoptionService.request(adoption);
            return ResponseEntity.ok("Adoption requested successfully!");
        } catch (ValidationException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/approve")
    @Transactional
    public ResponseEntity<String> approveAdoption(@RequestBody @Valid Adoption adoption) {
        this.adoptionService.approve(adoption);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/disapprove")
    @Transactional
    public ResponseEntity<String> disapproveAdoption(@RequestBody @Valid Adoption adoption) {
        this.adoptionService.disapprove(adoption);
        return ResponseEntity.ok().build();

    }
}

