package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.AdoptionRequestDto;

public interface AdoptionRequestValidation {

    void validate(AdoptionRequestDto dto);

}
