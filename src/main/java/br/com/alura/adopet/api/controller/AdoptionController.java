package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AdoptionApprovalDto;
import br.com.alura.adopet.api.dto.AdoptionDisapprovalDto;
import br.com.alura.adopet.api.dto.AdoptionRequestDto;
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
    public ResponseEntity<String> requestAdoption(@RequestBody @Valid AdoptionRequestDto dto) {

        try {
            this.adoptionService.request(dto);
            return ResponseEntity.ok("Adoption requested successfully!");
        } catch (ValidationException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/approve")
    @Transactional
    public ResponseEntity<String> approveAdoption(@RequestBody @Valid AdoptionApprovalDto dto) {
        this.adoptionService.approve(dto);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/disapprove")
    @Transactional
    public ResponseEntity<String> disapproveAdoption(@RequestBody @Valid AdoptionDisapprovalDto dto) {
        this.adoptionService.disapprove(dto);
        return ResponseEntity.ok().build();

    }
}

