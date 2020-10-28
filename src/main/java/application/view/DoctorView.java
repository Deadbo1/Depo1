package application.view;

import application.component.DoctorAdd;
import application.component.DoctorEditor;
import application.models.Doctor;
import application.service.impl.DoctorServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route("doctor")
public class DoctorView extends VerticalLayout {
    private final DoctorServiceImpl doctorServiceImpl;
    private final DoctorEditor doctorEditor;
    private final DoctorAdd doctorAdd;
    private Grid<Doctor> grid = new Grid<>(Doctor.class);
    private final Button addNewBtn = new Button("Add new Doctor");
    private final Button edit = new Button("Edit Doctor");
    private final Button delete = new Button("Delete Doctor");
    private final HorizontalLayout horizontalLayout = new HorizontalLayout(addNewBtn, edit, delete);

    @Autowired
    public DoctorView(DoctorServiceImpl doctorServiceImpl, DoctorEditor doctorEditor, DoctorAdd doctorAdd) {
        this.doctorServiceImpl = doctorServiceImpl;
        this.doctorEditor = doctorEditor;
        this.doctorAdd = doctorAdd;

        RouterLink link = new RouterLink("Main menu", MainView.class);
        RouterLink link1 = new RouterLink("Info recipe", InfoRecipeView.class);

        add(horizontalLayout, grid, doctorEditor, doctorAdd, link, link1);

        grid.asSingleSelect().addValueChangeListener(e -> {
            doctorEditor.editDoctor(e.getValue());
        });
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(doctor -> {
                delete.addClickListener(c -> {
                    doctorServiceImpl.deleteDoctor(doctor);
                    showDoctor(doctorServiceImpl);
                });
            });
        });

        add();


        edit.addClickListener(e -> {
            doctorEditor.open();
        });

        doctorEditor.setChangeHandler(() -> {
            showDoctor(doctorServiceImpl);
        });

        doctorAdd.setChangeHandler(() -> {
            showDoctor(doctorServiceImpl);
        });


        showDoctor(doctorServiceImpl);
    }


    private void add() {
        addNewBtn.addClickListener(e -> {
            DoctorAdd doctorAdd1 = new DoctorAdd(doctorServiceImpl);
            doctorAdd1.open();
            doctorAdd1.setChangeHandler(() -> {
                showDoctor(doctorServiceImpl);
            });
        });
    }

    private void showDoctor(DoctorServiceImpl doctorServiceImpl) {
        grid.setItems(doctorServiceImpl.findAll());
    }
}

