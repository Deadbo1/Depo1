package application.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "Recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "Doctor_Id", referencedColumnName = "id", nullable = false)
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "Patient_Id", referencedColumnName = "id", nullable = false)
    private Patient patient;
    @Column(name = "Description", nullable = false)
    private String description;
    @Column(name = "Date", nullable = false)
    private LocalDate date;
    @Column(name = "Validity", nullable = false)
    private Double validity;
    @Column(name = "Priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    public Recipe() {
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patientId) {
        this.patient = patientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValidity() {
        return validity;
    }

    public void setValidity(Double validity) {
        this.validity = validity;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", doctorId=" + doctor +
                ", patientId=" + patient +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", validity=" + validity +
                ", priority=" + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return getId().equals(recipe.getId()) &&
                getDoctor().equals(recipe.getDoctor()) &&
                getPatient().equals(recipe.getPatient()) &&
                getDescription().equals(recipe.getDescription()) &&
                getDate().equals(recipe.getDate()) &&
                getValidity().equals(recipe.getValidity()) &&
                getPriority() == recipe.getPriority();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDoctor(), getPatient(), getDescription(), getDate(), getValidity(), getPriority());
    }
}

