package application.service;

import application.models.Patient;

import java.util.List;

public interface PatientService {

    Patient getPatientById(Long id);

    void createPatient(Patient patient);

    void updatePatient(Long id, Patient patient);

    void deletePatient(Patient patient);

    List<Patient> findAll();
}
