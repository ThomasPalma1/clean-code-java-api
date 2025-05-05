package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetOwnerUpdateDto;
import br.com.alura.adopet.api.dto.RegisterOwnerDto;
import br.com.alura.adopet.api.service.PetOwnerService;
import br.com.alura.adopet.api.exception.ValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet-owners")
public class PetOwnerController {

    @Autowired
    private PetOwnerService petOwnerService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid RegisterOwnerDto registerOwnerDto) {
        try {
            petOwnerService.register(registerOwnerDto);
            return ResponseEntity.ok().build();
        } catch (ValidationException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> update(@RequestBody @Valid PetOwnerUpdateDto petOwnerUpdateDto) {
        try {
            petOwnerService.update(petOwnerUpdateDto);
            return ResponseEntity.ok().build();
        } catch (ValidationException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
