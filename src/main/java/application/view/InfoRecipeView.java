package application.view;

import application.models.Doctor;
import application.models.DoctorWr;
import application.service.impl.DoctorServiceImpl;
import application.service.impl.RecipeServiceImpl;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;



@Route("doctor/info")
public class InfoRecipeView extends VerticalLayout {
    private final DoctorServiceImpl doctorServiceImpl;
    private static final Logger log = Logger.getLogger(InfoRecipeView.class);

    private final Grid<DoctorWr> grid = new Grid<>(DoctorWr.class);


    @Autowired
    public InfoRecipeView(DoctorServiceImpl doctorServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;


        RouterLink link = new RouterLink("Doctor panel", DoctorView.class);

        log.info(doctorServiceImpl.castAllDoctorToDoctorWr(doctorServiceImpl.findAll()));
        showDoctor(doctorServiceImpl);
        add(grid,link);


    }

    private void showDoctor(DoctorServiceImpl doctorServiceImpl){
        grid.setItems(doctorServiceImpl.castAllDoctorToDoctorWr(doctorServiceImpl.findAll()));
    }

}

