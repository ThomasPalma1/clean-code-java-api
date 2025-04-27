package br.com.alura.adopet.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "shelters")
public class Shelter {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String shelterName;

    @NotBlank
    @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
    private String shelterPhoneNumber;

    @NotBlank
    @Email
    private String shelterEmail;

    @OneToMany(mappedBy = "shelter",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Pet> pets;

}
