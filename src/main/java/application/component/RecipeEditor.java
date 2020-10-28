package application.component;

import application.models.Doctor;
import application.models.Patient;
import application.models.Priority;
import application.models.Recipe;
import application.service.impl.RecipeServiceImpl;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class RecipeEditor extends Dialog implements KeyNotifier {

    private final RecipeServiceImpl recipeServiceImpl;

    private Recipe recipe;

    private ComboBox<Doctor> doctorId = new ComboBox("DoctorId");
    private ComboBox<Patient> patientId = new ComboBox("PatientId");
    private TextField description = new TextField("Description");
    private DatePicker date = new DatePicker("Date");
    private NumberField validity = new NumberField("Validity");
    private ComboBox<Priority> priorityComboBox = new ComboBox<>("Priority");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button closeButton = new Button("Close");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, closeButton);

    private Binder<Recipe> binder = new Binder<>(Recipe.class);
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public RecipeEditor(RecipeServiceImpl recipeServiceImpl) {
        this.recipeServiceImpl = recipeServiceImpl;
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        add(doctorId,patientId,description,date,validity,priorityComboBox,actions);
        binder.bindInstanceFields(this);
        priorityComboBox.setItems(Priority.values());
        priorityComboBox.getDataProvider();

        save.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e -> update());

        save.addClickListener(e -> update());
        cancel.addClickListener(e -> editRecipe(recipe));
        closeButton.addClickListener(e -> close());

    }

    public void editRecipe(Recipe newRecipe) {
        if (newRecipe == null) {
            return;
        }
        if (newRecipe.getId() != null) {
            this.recipe = recipeServiceImpl.getRecipeById(newRecipe.getId());
        } else {
            this.recipe = newRecipe;
        }
        binder.setBean(recipe);
        binder.bind(priorityComboBox,Recipe::getPriority,Recipe::setPriority);
        doctorId.focus();
    }

    public void update() {
        recipeServiceImpl.updateRecipe(recipe.getId(), recipe);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler h) {
        this.changeHandler = h;
    }

}
