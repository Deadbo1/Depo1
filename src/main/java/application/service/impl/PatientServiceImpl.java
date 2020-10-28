package application.service.impl;

import application.models.Patient;
import application.repository.PatientDAO;
import application.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientDAO patientDAO;

    public PatientServiceImpl(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientDAO.getPatientById(id);
    }

    @Override
    public void createPatient(Patient patient) {
        patientDAO.save(patient);
    }

    @Override
    public void updatePatient(Long id, Patient patient) {
        Patient patient1 = getPatientById(id);
        patient1.setName(patient.getName());
        patient1.setSurName(patient.getSurName());
        patient1.setPatronymic(patient.getPatronymic());
        patient1.setPhone(patient.getPhone());
        patientDAO.save(patient1);
    }

    @Override
    public void deletePatient(Patient patient) {
        patientDAO.delete(patient);
    }

    @Override
    public List<Patient> findAll() {
        return patientDAO.findAll();
    }
}


