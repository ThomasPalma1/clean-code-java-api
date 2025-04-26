package br.com.alura.adopet.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "adoptions")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    @ManyToOne
    private PetOwner petOwner;

    @OneToOne
    private Pet pet;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status;

    private String justificationStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adoption adoption = (Adoption) o;
        return Objects.equals(id, adoption.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
