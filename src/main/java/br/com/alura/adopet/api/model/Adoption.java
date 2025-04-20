package br.com.alura.adopet.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "adoptions")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private LocalDateTime data;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    @JsonBackReference("pet_owner_adoptions")
    private PetOwner petOwner;

    @NotNull
    @OneToOne
    @JoinColumn(name = "pet_id")
    @JsonManagedReference("pet_adoption")
    private Pet pet;

    @NotBlank
    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AdoptionStatus status;

    @Column(name = "justification_status")
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

    public String getJustificationStatus() {
        return justificationStatus;
    }

    public void setJustificationStatus(String justificationStatus) {
        this.justificationStatus = justificationStatus;
    }

    public AdoptionStatus getStatus() {
        return status;
    }

    public void setStatus(AdoptionStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
