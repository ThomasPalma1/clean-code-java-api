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
@RequestMapping("/adocoes")
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

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid Adocao adocao) {
        return null;
    }


    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid Adocao adocao) {
        return null;

    }
}

