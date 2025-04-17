package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Adoption;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdoptionService {


    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private JavaMailSender emailSender;


    public void request(Adoption adoption) {
        if (adoption.getPet().getAdotado() == true) {
            throw new ValidationException("Pet has already been adopted!");

        } else {
            List<Adoption> adocoes = repository.findAll();
            for (Adoption a : adocoes) {
                if (a.getTutor() == adoption.getTutor() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidationException("Tutor already has another adoption awaiting evaluation!");
                }
            }
            for (Adoption a : adocoes) {
                if (a.getPet() == adoption.getPet() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidationException("Pet is now awaiting evaluation to be adopted!");
                }
            }
            for (Adoption a : adocoes) {
                int contador = 0;
                if (a.getTutor() == adoption.getTutor() && a.getStatus() == StatusAdocao.APROVADO) {
                    contador = contador + 1;
                }
                if (contador == 5) {
                    throw new ValidationException("Tutor has reached the maximum limit of 5 adoptions!");
                }
            }
        }

        adoption.setData(LocalDateTime.now());
        adoption.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adoption.getPet().getAbrigo().getEmail());
        email.setSubject("Solicitação de adoção");
        email.setText("Olá " + adoption.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adoption.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.");
        emailSender.send(email);
    }

    public void approve(Adoption adoption) {
        adoption.setStatus(StatusAdocao.APROVADO);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adoption.getTutor().getEmail());
        email.setSubject("Adoção aprovada");
        email.setText("Parabéns " + adoption.getTutor().getNome() + "!\n\nSua adoção do pet " + adoption.getPet().getNome() + ", solicitada em " + adoption.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi aprovada.\nFavor entrar em contato com o abrigo " + adoption.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet.");
        emailSender.send(email);

        return ResponseEntity.ok().build();

    }

    public void disapprove(Adoption adoption) {
        adoption.setStatus(StatusAdocao.REPROVADO);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adoption.getTutor().getEmail());
        email.setSubject("Adoção reprovada");
        email.setText("Olá " + adoption.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adoption.getPet().getNome() + ", solicitada em " + adoption.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adoption.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adoption.getJustificativaStatus());
        emailSender.send(email);

        return ResponseEntity.ok().build();
    }

}
}
