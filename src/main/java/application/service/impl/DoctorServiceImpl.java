package application.service.impl;

import application.models.Doctor;
import application.models.DoctorWr;
//import application.models.Recipe;
import application.models.Recipe;
import application.repository.DoctorDAO;
//import application.repository.RecipeDAO;
import application.repository.RecipeDAO;
import application.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorDAO doctorDAO;
    private final RecipeDAO recipeDAO;

    public DoctorServiceImpl(DoctorDAO doctorDAO, RecipeDAO recipeDAO) {
        this.doctorDAO = doctorDAO;
        this.recipeDAO = recipeDAO;
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorDAO.getDoctorById(id);
    }

    @Override
    public void createDoctor(Doctor doctor) {
        doctorDAO.save(doctor);
    }

    @Override
    public void updateDoctor(Long id, Doctor doctor) {
        Doctor doctor1 = getDoctorById(id);
        doctor1.setName(doctor.getName());
        doctor1.setSurName(doctor.getSurName());
        doctor1.setPatronymic(doctor.getPatronymic());
        doctor1.setSpecialization(doctor.getSpecialization());
        doctorDAO.save(doctor1);
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
        doctorDAO.delete(doctor);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorDAO.findAll();
    }

    @Override
    public List<DoctorWr> castAllDoctorToDoctorWr(List<Doctor> doctors) {
        List<DoctorWr> doctorWrs = new ArrayList<>();
        List<Recipe> recipeList = recipeDAO.findAll();
        for (Doctor doctor : doctors) {
            DoctorWr doctorWr = new DoctorWr();
            for (Recipe recipe : recipeList) {
                if (recipe.getDoctor().equals(doctor)) {
                    doctorWr.setId(doctor.getId());
                    doctorWr.setName(doctor.getName());
                    doctorWr.setSurName(doctor.getSurName());
                    doctorWr.setPatronymic(doctor.getPatronymic());
                    doctorWr.setSpecialization(doctor.getSpecialization());
                    doctorWr.setCount(recipeDAO.countRecipeByDoctor(recipe.getDoctor()));
                    doctorWrs.add(doctorWr);
                }
            }
        }
        Set<DoctorWr> doctorWrSet = new HashSet<>(doctorWrs);
        doctorWrs.clear();
        doctorWrs.addAll(doctorWrSet);
        return doctorWrs;
    }
}


