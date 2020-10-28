package application.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    public MainView() {
        RouterLink link = new RouterLink("Patient panel", PatientView.class);
        RouterLink link1 = new RouterLink("Doctor panel", DoctorView.class);
        RouterLink link2 = new RouterLink("Recipe panel", RecipeView.class);
        add(link, link1, link2);

    }
}
