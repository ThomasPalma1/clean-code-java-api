package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.Adocao;

import br.com.alura.adopet.api.service.AdoptionService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/adoptions")
public class AdocaoController {

    @Autowired
    private AdoptionService adoptionService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> requestAdoption(@RequestBody @Valid Adocao adocao) {

        try {
            this.adoptionService.request(adocao);
            return ResponseEntity.ok("Adoption requested successfully!");
        } catch (ValidationException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/approve")
    @Transactional
    public ResponseEntity<String> approveAdoption(@RequestBody @Valid Adocao adocao) {
        this.adoptionService.approve(adocao);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/disapprove")
    @Transactional
    public ResponseEntity<String> disapproveAdoption(@RequestBody @Valid Adocao adocao) {
        this.adoptionService.disapprove(adocao);
        return ResponseEntity.ok().build();

    }
}

