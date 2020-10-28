package application.component;

import application.models.Doctor;
import application.service.impl.DoctorServiceImpl;
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
public class DoctorEditor extends Dialog implements KeyNotifier {
    private final DoctorServiceImpl doctorServiceImpls;

    private Doctor doctor;

    private TextField name = new TextField("Name");
    private TextField surName = new TextField("Surname");
    private TextField patronymic = new TextField("Patronymic");
    private TextField specialization = new TextField("Specialization");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button closeButton = new Button("Close");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, closeButton);

    private Binder<Doctor> binder = new Binder<>(Doctor.class);
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public DoctorEditor(DoctorServiceImpl doctorServiceImpls) {
        this.doctorServiceImpls = doctorServiceImpls;
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        add(name, surName, patronymic, specialization, actions);
        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e -> update());

        save.addClickListener(e -> update());
        cancel.addClickListener(e -> editDoctor(doctor));
        closeButton.addClickListener(e -> close());

    }

    public void editDoctor(Doctor newDoctor) {
        if (newDoctor == null) {
            return;
        }
        if (newDoctor.getId() != null) {
            this.doctor = doctorServiceImpls.getDoctorById(newDoctor.getId());
        } else {
            this.doctor = newDoctor;
        }
        binder.setBean(doctor);
        name.focus();
    }

    public void update() {
        doctorServiceImpls.updateDoctor(doctor.getId(), doctor);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler h) {
        this.changeHandler = h;
    }

}

