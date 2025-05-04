package br.com.alura.adopet.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "adoptions")
public class Adoption {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private PetOwner petOwner;

    @OneToOne(fetch = FetchType.LAZY)
    private Pet pet;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status = AdoptionStatus.PENDING_REVIEW;

    private String justificationStatus;

    public void MarkAsApproved() {
        this.status = AdoptionStatus.APPROVED;
    }

    public void MarkAsDisapproved(@NotBlank String reason) {
        this.status = AdoptionStatus.REJECTED;
        this.justificationStatus = reason;
    }

}
