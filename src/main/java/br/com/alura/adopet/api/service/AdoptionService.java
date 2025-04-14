package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidationException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdoptionService {


    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private JavaMailSender emailSender;


    public void request(Adocao adocao) {
        if (adocao.getPet().getAdotado() == true) {
            throw new ValidationException("Pet has already been adopted!");

        } else {
            List<Adocao> adocoes = repository.findAll();
            for (Adocao a : adocoes) {
                if (a.getTutor() == adocao.getTutor() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidationException("Tutor already has another adoption awaiting evaluation!");
                }
            }
            for (Adocao a : adocoes) {
                if (a.getPet() == adocao.getPet() && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidationException("Pet is now awaiting evaluation to be adopted!");
                }
            }
            for (Adocao a : adocoes) {
                int contador = 0;
                if (a.getTutor() == adocao.getTutor() && a.getStatus() == StatusAdocao.APROVADO) {
                    contador = contador + 1;
                }
                if (contador == 5) {
                    throw new ValidationException("Tutor has reached the maximum limit of 5 adoptions!");
                }
            }
        }

        adocao.setData(LocalDateTime.now());
        adocao.setStatus(StatusAdocao.AGUARDANDO_AVALIACAO);
        repository.save(adocao);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adocao.getPet().getAbrigo().getEmail());
        email.setSubject("Solicitação de adoção");
        email.setText("Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.");
        emailSender.send(email);
    }

    public void approve() {

    }

    public void disapprove() {

    }
}
