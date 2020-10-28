package application.repository;

import application.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorDAO extends JpaRepository<Doctor,Long> {
    List<Doctor> findAll();

    Doctor getDoctorById(Long id);

}
