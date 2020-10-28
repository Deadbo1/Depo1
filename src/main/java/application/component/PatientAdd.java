package application.component;

import application.models.Patient;
import application.service.impl.PatientServiceImpl;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class PatientAdd extends Dialog implements KeyNotifier {
    private final PatientServiceImpl patientServiceImpl;

    private Patient patient = new Patient();

    private TextField name = new TextField("Name");
    private TextField surName = new TextField("Surname");
    private TextField patronymic = new TextField("Patronymic");
    private TextField phone = new TextField("Phone");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button closeButton = new Button("Close");
    private HorizontalLayout actions = new HorizontalLayout(save,closeButton);

    private Binder<Patient> binder = new Binder<>(Patient.class);
    private ChangeHandler changeHandler;

    public interface ChangeHandler{
        void onChange();
    }

    @Autowired
    public PatientAdd(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        add(name,surName,patronymic,phone,actions);

        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        closeButton.addClickListener(e -> {close();});
        binder.setBean(patient);
        name.focus();
    }

    public void save() {
        patientServiceImpl.createPatient(patient);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}


