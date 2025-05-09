package br.com.alura.adopet.api.model;

import br.com.alura.adopet.api.dto.RegisterPetDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PetType petType;

    @NotBlank
    private String petName;

    @NotBlank
    private String petBreed;

    @NotNull
    private Integer petAge;

    @NotBlank
    private String petColor;

    @NotNull
    private Float petWeight;

    @NotNull
    private Boolean petIsAdopted;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @OneToOne(mappedBy = "pet")
    private Adoption adoption;

    public Pet(RegisterPetDto registerPetDto, Shelter shelter) {
        this.petType = registerPetDto.petType();
        this.petName = registerPetDto.petName();
        this.petBreed = registerPetDto.petBreed();
        this.petAge = registerPetDto.petAge();
        this.petColor = registerPetDto.petColor();
        this.petWeight = registerPetDto.petWeight();
        this.shelter = shelter;
    }
}
