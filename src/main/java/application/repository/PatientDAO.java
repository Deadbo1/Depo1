package application.repository;

import application.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientDAO extends JpaRepository<Patient,Long> {
    List<Patient> findAll();

    Patient getPatientById(Long id);
}