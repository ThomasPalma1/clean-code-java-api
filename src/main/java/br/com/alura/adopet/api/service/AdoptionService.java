package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AdoptionApprovalDto;
import br.com.alura.adopet.api.dto.AdoptionDisapprovalDto;
import br.com.alura.adopet.api.dto.AdoptionRequestDto;
import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Adoption;
import br.com.alura.adopet.api.model.AdoptionStatus;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdoptionService {


    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private EmailService emailService;


    public void request(AdoptionRequestDto dto) {
        if (adoption.getPet().getAdotado() == true) {
            throw new ValidationException("Pet has already been adopted!");

        } else {
            List<Adoption> adocoes = repository.findAll();
            for (Adoption a : adocoes) {
                if (a.getTutor() == adoption.getTutor() && a.getStatus() == AdoptionStatus.PENDING_REVIEW) {
                    throw new ValidationException("Tutor already has another adoption awaiting evaluation!");
                }
            }
            for (Adoption a : adocoes) {
                if (a.getPet() == adoption.getPet() && a.getStatus() == AdoptionStatus.PENDING_REVIEW) {
                    throw new ValidationException("Pet is now awaiting evaluation to be adopted!");
                }
            }
            for (Adoption a : adocoes) {
                int contador = 0;
                if (a.getTutor() == adoption.getTutor() && a.getStatus() == AdoptionStatus.APPROVED) {
                    contador = contador + 1;
                }
                if (contador == 5) {
                    throw new ValidationException("Tutor has reached the maximum limit of 5 adoptions!");
                }
            }
        }

        adoption.setData(LocalDateTime.now());
        adoption.setStatus(AdoptionStatus.PENDING_REVIEW);
        repository.save(adoption);

        emailService.sendEmail(
                adoption.getPet().getAbrigo().getEmail(),
                "Adoption request",
                "Hello, " + adoption.getPet().getAbrigo().getNome() + "!\n\n" +
                        "An adoption request has been submitted today for the pet: " +
                        adoption.getPet().getNome() + ".\n" +
                        "Please review it for approval or rejection."
        );

    }

    public void approve(AdoptionApprovalDto dto) {
        adoption.setStatus(AdoptionStatus.APPROVED);
        repository.save(adoption);

        emailService.sendEmail(
                adoption.getTutor().getEmail(),
                "Adoption approved",
                "Congratulations " + adoption.getTutor().getNome() + "!\n\n" +
                        "Your adoption of the pet " + adoption.getPet().getNome() + ", requested on " +
                        adoption.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", has been approved.\n" +
                        "Please contact the shelter " + adoption.getPet().getAbrigo().getNome() +
                        " to schedule the pickup of your pet."
        );

    }

    public void disapprove(AdoptionDisapprovalDto dto) {
        adoption.setStatus(AdoptionStatus.REJECTED);
        repository.save(adoption);

        emailService.sendEmail(
                adoption.getTutor().getEmail(),
                "Adoption rejected",
                "Hello " + adoption.getTutor().getNome() + "!\n\n" +
                        "Unfortunately, your adoption request for the pet " + adoption.getPet().getNome() +
                        ", submitted on " + adoption.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", was rejected by the shelter " + adoption.getPet().getAbrigo().getNome() +
                        " with the following justification: " + adoption.getJustificativaStatus()
        );

    }

}

