package application.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Surname", nullable = false)
    private String surName;
    @Column(name = "Patronymic", nullable = false)
    private String patronymic;
    @Column(name = "Specialization", nullable = false)
    private String specialization;

    public Doctor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return getId().equals(doctor.getId()) &&
                getName().equals(doctor.getName()) &&
                getSurName().equals(doctor.getSurName()) &&
                getPatronymic().equals(doctor.getPatronymic()) &&
                getSpecialization().equals(doctor.getSpecialization());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurName(), getPatronymic(), getSpecialization());
    }

    @Override
    public String toString() {
        return name;
    }
}
