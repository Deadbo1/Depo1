package application.service;

import application.models.Doctor;
import application.models.DoctorWr;

import java.util.List;

public interface DoctorService {

    List<DoctorWr> castAllDoctorToDoctorWr(List<Doctor> doctors);

    Doctor getDoctorById(Long id);

    void createDoctor(Doctor doctor);

    void updateDoctor(Long id, Doctor doctor);

    void deleteDoctor(Doctor doctor);

    List<Doctor> findAll();

}

