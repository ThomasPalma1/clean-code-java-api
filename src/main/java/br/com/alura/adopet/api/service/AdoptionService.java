package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AdoptionApprovalDto;
import br.com.alura.adopet.api.dto.AdoptionDisapprovalDto;
import br.com.alura.adopet.api.dto.AdoptionRequestDto;

import br.com.alura.adopet.api.model.Adoption;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.PetOwner;
import br.com.alura.adopet.api.repository.AdoptionRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.PetOwnerRepository;
import br.com.alura.adopet.api.validation.AdoptionRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class AdoptionService {


    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<AdoptionRequestValidation> validations;


    public void request(AdoptionRequestDto dto) {

        Pet pet = petRepository.getReferenceById(dto.idPet());
        PetOwner petOwner = petOwnerRepository.getReferenceById(dto.idPetOwner());

        validations.forEach(v -> v.validate(dto));

        Adoption adoption = new Adoption();

        adoptionRepository.save(adoption);

        emailService.sendEmail(
                adoption.getPet().getAbrigo().getEmail(),
                "Adoption request",
                "Hello, " + adoption.getPet().getAbrigo().getName() + "!\n\n" +
                        "An adoption request has been submitted today for the pet: " +
                        adoption.getPet().getPetName() + ".\n" +
                        "Please review it for approval or rejection."
        );

    }

    public void approve(AdoptionApprovalDto dto) {
        Adoption adoption = adoptionRepository.getReferenceById(dto.idAdoption());
        adoption.MarkAsApproved();

        emailService.sendEmail(
                adoption.getPetOwner().getEmail(),
                "Adoption approved",
                "Congratulations " + adoption.getPetOwner().getNome() + "!\n\n" +
                        "Your adoption of the pet " + adoption.getPet().getPetName() + ", requested on " +
                        adoption.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", has been approved.\n" +
                        "Please contact the shelter " + adoption.getPet().getAbrigo().getName() +
                        " to schedule the pickup of your pet."
        );

    }

    public void disapprove(AdoptionDisapprovalDto dto) {
        Adoption adoption = adoptionRepository.getReferenceById(dto.idAdoption());
        adoption.MarkAsDisapproved(dto.reason());

        emailService.sendEmail(
                adoption.getPetOwner().getEmail(),
                "Adoption rejected",
                "Hello " + adoption.getPetOwner().getNome() + "!\n\n" +
                        "Unfortunately, your adoption request for the pet " + adoption.getPet().getPetName() +
                        ", submitted on " + adoption.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", was rejected by the shelter " + adoption.getPet().getAbrigo().getName() +
                        " with the following justification: " + adoption.getJustificationStatus()
        );

    }

}

