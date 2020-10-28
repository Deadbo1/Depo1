package application.view;

import application.component.PatientAdd;
import application.component.PatientEditor;
import application.models.Patient;
import application.service.impl.PatientServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;



@Route("patient")
public class PatientView extends VerticalLayout {
    private final PatientServiceImpl patientServiceImpl;
    private final PatientEditor patientEditor;
    private final PatientAdd patientAdd;
    private final Grid<Patient> grid = new Grid<>(Patient.class);
    private final Button addNewBtn = new Button("Add new Patient");
    private final Button edit = new Button("Edit Patient");
    private final Button delete = new Button("Delete Patient");
    private final HorizontalLayout horizontalLayout = new HorizontalLayout(addNewBtn, edit, delete);

    @Autowired
    public PatientView(PatientServiceImpl patientServiceImpl, PatientEditor patientEditor, PatientAdd patientAdd) {
        this.patientServiceImpl = patientServiceImpl;
        this.patientEditor = patientEditor;
        this.patientAdd = patientAdd;

        RouterLink link = new RouterLink("Main menu", MainView.class);

        add(horizontalLayout, grid, patientEditor, patientAdd, link);

        grid.asSingleSelect().addValueChangeListener(e -> {
            patientEditor.editPatient(e.getValue());
        });

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(patient -> {
                delete.addClickListener(c -> {
                    patientServiceImpl.deletePatient(patient);
                    showPatient(patientServiceImpl);
                });
            });
        });


        add();

        edit.addClickListener(e -> {
            patientEditor.open();
        });

        patientEditor.setChangeHandler(() -> {
            showPatient(patientServiceImpl);
        });

        patientAdd.setChangeHandler(() -> {
            showPatient(patientServiceImpl);
        });

        showPatient(patientServiceImpl);
    }

    private void add() {
        addNewBtn.addClickListener(e -> {
            PatientAdd patientAdd1 = new PatientAdd(patientServiceImpl);
            patientAdd1.open();
            patientAdd1.setChangeHandler(() -> {
                showPatient(patientServiceImpl);
            });
        });
    }


    private void showPatient(PatientServiceImpl patientServiceImpl) {
        grid.setItems(patientServiceImpl.findAll());
    }

}

