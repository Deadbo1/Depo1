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
public class DoctorAdd extends Dialog implements KeyNotifier {
    private final DoctorServiceImpl doctorServiceImpl;

    private Doctor doctor = new Doctor();

    private TextField name = new TextField("Name");
    private TextField surName = new TextField("Surname");
    private TextField patronymic = new TextField("Patronymic");
    private TextField specialization = new TextField("Specialization");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button closeButton = new Button("Close");
    private HorizontalLayout actions = new HorizontalLayout(save,closeButton);

    private Binder<Doctor> binder = new Binder<>(Doctor.class);
    private ChangeHandler changeHandler;

    public interface ChangeHandler{
        void onChange();
    }

    @Autowired
    public DoctorAdd(DoctorServiceImpl doctorServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        add(name,surName,patronymic,specialization,actions);

        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");


        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        closeButton.addClickListener(e -> {close();});
        binder.setBean(doctor);
        name.focus();
    }


    public void save() {
        doctorServiceImpl.createDoctor(doctor);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}


