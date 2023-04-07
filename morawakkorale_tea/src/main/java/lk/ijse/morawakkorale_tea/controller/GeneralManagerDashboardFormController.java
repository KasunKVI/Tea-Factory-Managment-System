package lk.ijse.morawakkorale_tea.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GeneralManagerDashboardFormController {

    @FXML
    public Pane menuBarPanel;


    public void hideMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.showMenuBar(menuBarPanel);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.hideMenuBar(menuBarPanel);

    }

    public void exitSystem(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
