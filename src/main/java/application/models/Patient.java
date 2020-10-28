package application.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Surname", nullable = false)
    private String surName;
    @Column(name = "Patronymic", nullable = false)
    private String patronymic;
    @Column(name = "Phone", nullable = false, unique = true)
    private String phone;

    public Patient() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return getId().equals(patient.getId()) &&
                getName().equals(patient.getName()) &&
                getSurName().equals(patient.getSurName()) &&
                getPatronymic().equals(patient.getPatronymic()) &&
                getPhone().equals(patient.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurName(), getPatronymic(), getPhone());
    }

    @Override
    public String toString() {
        return name;
    }
}

