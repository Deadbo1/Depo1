package application.component;

import application.models.Doctor;
import application.models.Patient;
import application.models.Priority;
import application.models.Recipe;
import application.service.impl.DoctorServiceImpl;
import application.service.impl.PatientServiceImpl;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class RecipeAdd extends Dialog implements KeyNotifier {
    private final RecipeServiceImpl recipeServiceImpl;
    private final DoctorServiceImpl doctorServiceImpl;
    private final PatientServiceImpl patientServiceImpl;

    private static final Logger log = Logger.getLogger(RecipeAdd.class);


    private Recipe recipe = new Recipe();

    private ComboBox<Doctor> doctor = new ComboBox("Doctor");
    private ComboBox<Patient> patient = new ComboBox("Patient");
    private TextField description = new TextField("Description");
    private DatePicker date = new DatePicker("Date");
    private NumberField validity = new NumberField("Validity");
    private ComboBox<Priority> priorityComboBox = new ComboBox<>("Priority");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button closeButton = new Button("Close");
    private HorizontalLayout actions = new HorizontalLayout(save, closeButton);

    private Binder<Recipe> binder = new Binder<>(Recipe.class);
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public RecipeAdd(RecipeServiceImpl recipeServiceImpl, DoctorServiceImpl doctorServiceImpl, PatientServiceImpl patientServiceImpl) {
        this.recipeServiceImpl = recipeServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        priorityComboBox.setItems(Priority.values());
        doctor.setItems(getDoctorIdList());
        patient.setItems(getPatientIdList());
        add(doctor, patient, description, date, validity, priorityComboBox, actions);

        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        closeButton.addClickListener(e -> {
            close();
        });
        binder.setBean(recipe);
        binder.bind(priorityComboBox, Recipe::getPriority, Recipe::setPriority);
        patient.focus();
    }

    private List<Doctor> getDoctorIdList() {
        List<Doctor> doctors = doctorServiceImpl.findAll();
        return doctors;
    }

    private List<Patient> getPatientIdList() {
        List<Patient> patients = patientServiceImpl.findAll();
        return patients;
    }


    public void save() {
        log.info(recipe.getDate());
        recipeServiceImpl.createRecipe(recipe);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
