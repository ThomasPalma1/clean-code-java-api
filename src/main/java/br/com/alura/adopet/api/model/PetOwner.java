package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.PetOwnerUpdateDto;
import br.com.alura.adopet.api.dto.RegisterOwnerDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pet_owners")
public class PetOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String petOwnerName;

    private String petOwnerPhoneNumber;

    private String petOwnerEmail;

    @OneToMany(mappedBy = "petOwner")
    private List<Adoption> adoptions = new ArrayList<>();


    public PetOwner(RegisterOwnerDto registerOwnerDto) {
        this.petOwnerName = registerOwnerDto.petOwnerName();
        this.petOwnerPhoneNumber = registerOwnerDto.petOwnerPhoneNumber();
        this.petOwnerEmail = registerOwnerDto.petOwnerEmail();
    }

    public void updateData(PetOwnerUpdateDto petOwnerUpdateDto) {
        this.petOwnerName = petOwnerUpdateDto.petOwnerName();
        this.petOwnerPhoneNumber = petOwnerUpdateDto.petOwnerPhoneNumber();
        this.petOwnerEmail = petOwnerUpdateDto.petOwnerEmail();
    }


}