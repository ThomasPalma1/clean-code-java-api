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


    public void request(AdoptionRequestDto adoptionRequestDto) {

        Pet pet = petRepository.getReferenceById(adoptionRequestDto.idPet());
        PetOwner petOwner = petOwnerRepository.getReferenceById(adoptionRequestDto.idPetOwner());

        validations.forEach(v -> v.validate(adoptionRequestDto));

        Adoption adoption = new Adoption();
        adoptionRepository.save(adoption);

        emailService.sendEmail(
                adoption.getPet().getShelter().getShelterEmail(),
                "Adoption request",
                "Hello, " + adoption.getPet().getShelter().getShelterName() + "!\n\n" +
                        "An adoption request has been submitted today for the pet: " +
                        adoption.getPet().getPetName() + ".\n" +
                        "Please review it for approval or rejection."
        );

    }

    public void approve(AdoptionApprovalDto dto) {
        Adoption adoption = adoptionRepository.getReferenceById(dto.idAdoption());
        adoption.MarkAsApproved();

        emailService.sendEmail(
                adoption.getPetOwner().getPetOwnerEmail(),
                "Adoption approved",
                "Congratulations " + adoption.getPetOwner().getPetOwnerName() + "!\n\n" +
                        "Your adoption of the pet " + adoption.getPet().getPetName() + ", requested on " +
                        adoption.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", has been approved.\n" +
                        "Please contact the shelter " + adoption.getPet().getShelter().getShelterName() +
                        " to schedule the pickup of your pet."
        );

    }

    public void disapprove(AdoptionDisapprovalDto dto) {
        Adoption adoption = adoptionRepository.getReferenceById(dto.idAdoption());
        adoption.MarkAsDisapproved(dto.reason());

        emailService.sendEmail(
                adoption.getPetOwner().getPetOwnerEmail(),
                "Adoption rejected",
                "Hello " + adoption.getPetOwner().getPetOwnerName() + "!\n\n" +
                        "Unfortunately, your adoption request for the pet " + adoption.getPet().getPetName() +
                        ", submitted on " + adoption.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", was rejected by the shelter " + adoption.getPet().getShelter().getShelterName() +
                        " with the following justification: " + adoption.getJustificationStatus()
        );

    }

}

