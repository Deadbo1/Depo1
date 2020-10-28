package application.repository;

import application.models.Doctor;
import application.models.Patient;
import application.models.Priority;
import application.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe,Long> {
    List<Recipe> findAll();

    Recipe getRecipeById(Long id);

    @Query("SELECT r FROM Recipe r WHERE r.patient=:patient")
    List<Recipe> findRecipeByPatient(@Param("patient") Patient patient);

    @Query("SELECT r FROM Recipe r WHERE r.priority=:priority")
    List<Recipe> findRecipeByPriority(@Param("priority") Priority priority);

    Long countRecipeByDoctor(Doctor doctor);
}


