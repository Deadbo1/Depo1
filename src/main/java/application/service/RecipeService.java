package application.service;

import application.models.Doctor;
import application.models.Patient;
import application.models.Priority;
import application.models.Recipe;

import java.util.List;

public interface RecipeService {

    Long countRecipeByDoctor(Doctor doctor);

    Recipe getRecipeById(Long id);

    List<Recipe> findRecipeByPriority(Priority priority);

    List<Recipe> findRecipeByPatient(Patient patientId);

    List<Recipe> findRecipeByDescription(String description);

    void createRecipe(Recipe recipe);

    void updateRecipe(Long id, Recipe recipe);

    void deleteRecipe(Recipe recipe);

    List<Recipe> findAll();
}
