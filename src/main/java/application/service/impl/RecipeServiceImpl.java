package application.service.impl;

import application.models.Doctor;
import application.models.Patient;
import application.models.Priority;
import application.models.Recipe;
import application.repository.DoctorDAO;
import application.repository.PatientDAO;
import application.repository.RecipeDAO;
import application.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeDAO recipeDAO;
    private final DoctorDAO doctorDAO;
    private final PatientDAO patientDAO;


    public RecipeServiceImpl(RecipeDAO recipeDAO, DoctorDAO doctorDAO, PatientDAO patientDAO) {
        this.recipeDAO = recipeDAO;
        this.doctorDAO = doctorDAO;
        this.patientDAO = patientDAO;
    }

    @Override
    public Long countRecipeByDoctor(Doctor doctor) {
        return recipeDAO.countRecipeByDoctor(doctor);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeDAO.getRecipeById(id);
    }

    @Override
    public List<Recipe> findRecipeByPriority(Priority priority) {
        return recipeDAO.findRecipeByPriority(priority);
    }

    @Override
    public List<Recipe> findRecipeByPatient(Patient patient) {
        return recipeDAO.findRecipeByPatient(patient);
    }

    @Override
    public List<Recipe> findRecipeByDescription(String description) {
        List<Recipe> recipeList = findAll();
        List<Recipe> recipeList1 = new ArrayList<>();
        for (Recipe recipe : recipeList){
            if(recipe.getDescription().equalsIgnoreCase(description) || recipe.getDescription().toLowerCase().contains(description) || recipe.getDescription().contains(description))
            {
                recipeList1.add(recipe);
            }
        }
        return recipeList1;
    }

    @Override
    public void createRecipe(Recipe recipe) {
        Recipe recipe1 = new Recipe();
        recipe1.setId(recipe.getId());
        recipe1.setDoctor(recipe.getDoctor());
        recipe1.setPatient(recipe.getPatient());
        recipe1.setDescription(recipe.getDescription());
        recipe1.setDate(recipe.getDate());
        recipe1.setValidity(recipe.getValidity());
        recipe1.setPriority(recipe.getPriority());
        recipeDAO.save(recipe1);
    }

    @Override
    public void updateRecipe(Long id, Recipe recipe) {
        Recipe recipe1 = getRecipeById(id);
        recipe1.setDoctor(recipe.getDoctor());
        recipe1.setPatient(recipe.getPatient());
        recipe1.setDescription(recipe.getDescription());
        recipe1.setValidity(recipe.getValidity());
        recipe1.setPriority(recipe.getPriority());
        recipeDAO.save(recipe1);
    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        recipeDAO.delete(recipe);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeDAO.findAll();
    }
}

