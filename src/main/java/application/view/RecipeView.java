package application.view;

import application.component.RecipeAdd;
import application.component.RecipeEditor;
import application.models.Patient;
import application.models.Priority;
import application.models.Recipe;
import application.service.impl.DoctorServiceImpl;
import application.service.impl.PatientServiceImpl;
import application.service.impl.RecipeServiceImpl;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route("recipe")
public class RecipeView extends VerticalLayout {
    private final RecipeServiceImpl recipeServiceImpl;
    private final DoctorServiceImpl doctorServiceImpl;
    private final PatientServiceImpl patientServiceImpl;
    private final RecipeAdd recipeAdd;
    private final RecipeEditor recipeEditor;
    private final AccordionPanel panel = new AccordionPanel();
    private final TextField filterByDescription = new TextField();
    private final ComboBox<Priority> filterByPriority = new ComboBox<>();
    private final ComboBox<Patient> filterByPatient = new ComboBox<>();
    private final Grid<Recipe> grid = new Grid<>(Recipe.class);
    private Binder<Recipe> binder = new Binder<>(Recipe.class);
    private final Button addNewBtn = new Button("Add new Recipe");
    private final Button edit = new Button("Edit Recipe");
    private final Button delete = new Button("Delete Recipe");
    private final Button cancel = new Button("Cancel");
    private final Button apply = new Button("Apply");
    private final HorizontalLayout horizontalLayout = new HorizontalLayout(addNewBtn, edit, delete);


    @Autowired
    public RecipeView(RecipeServiceImpl recipeServiceImpl, DoctorServiceImpl doctorServiceImpl, PatientServiceImpl patientServiceImpl, RecipeAdd recipeAdd, RecipeEditor recipeEditor) {
        this.recipeServiceImpl = recipeServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
        this.recipeAdd = recipeAdd;
        this.recipeEditor = recipeEditor;

        RouterLink link = new RouterLink("Main menu", MainView.class);
        filterByPriority.setItems(Priority.values());
        binder.bind(filterByPriority, Recipe::getPriority, Recipe::setPriority);
        filterByPatient.setItems(patientServiceImpl.findAll());
        filterByDescription.setPlaceholder("Filter by description");
        filterByPriority.setPlaceholder("Filter by priority");
        filterByPatient.setPlaceholder("Filter by patient");
        apply.addClickListener(e -> {
            if (!filterByDescription.getValue().equals("")) {
                showRecipeFilteredByDescription();
            } else if(!filterByPatient.isEmpty()) {
                showRecipeFilteredByPatient();
            } else {
                showRecipeFilteredByPriority();
            }
        });
        cancel.addClickListener(e -> {filterByDescription.clear();
            filterByPriority.setItems(Priority.values());
            filterByPatient.setItems(patientServiceImpl.findAll());
            showRecipe(recipeServiceImpl);});
        filterByDescription.setValueChangeMode(ValueChangeMode.LAZY);
        panel.addContent(filterByDescription, filterByPriority, filterByPatient, apply, cancel);

        add(horizontalLayout, grid, recipeEditor, recipeAdd, link, panel);

        grid.asSingleSelect().addValueChangeListener(e -> {
            recipeEditor.editRecipe(e.getValue());
        });

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(recipe -> {
                delete.addClickListener(c -> {
                    recipeServiceImpl.deleteRecipe(recipe);
                    showRecipe(recipeServiceImpl);
                });
            });
        });


        add();

        edit.addClickListener(e -> {
            recipeEditor.open();
        });

        recipeEditor.setChangeHandler(() -> {
            showRecipe(recipeServiceImpl);
        });

        recipeAdd.setChangeHandler(() -> {
            showRecipe(recipeServiceImpl);
        });

        showRecipe(recipeServiceImpl);
    }

    private void add() {
        addNewBtn.addClickListener(e -> {
            RecipeAdd recipeAdd1 = new RecipeAdd(recipeServiceImpl, doctorServiceImpl, patientServiceImpl);
            recipeAdd1.open();
            recipeAdd1.setChangeHandler(() -> {
                showRecipe(recipeServiceImpl);
            });
        });
    }


    private void showRecipe(RecipeServiceImpl recipeServiceImpl) {
        grid.setItems(recipeServiceImpl.findAll());
    }

    private void showRecipeFilteredByDescription() {
        grid.setItems(recipeServiceImpl.findRecipeByDescription(filterByDescription.getValue()));
    }

    private void showRecipeFilteredByPriority() {
        grid.setItems(recipeServiceImpl.findRecipeByPriority(filterByPriority.getValue()));
    }

    private void showRecipeFilteredByPatient() {
        grid.setItems(recipeServiceImpl.findRecipeByPatient(filterByPatient.getValue()));
    }

}
//
