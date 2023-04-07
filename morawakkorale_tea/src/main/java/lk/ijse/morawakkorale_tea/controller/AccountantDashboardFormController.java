package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountantDashboardFormController {

    @FXML
    public AnchorPane supplierBillPane;
    public Pane panel;
    public BorderPane pn;
    public AnchorPane bgPane;

    @FXML
    public Pane menuBarPanel;

    public void loadSupplierBills(ActionEvent actionEvent) throws IOException {

        bgPane.getChildren().clear();
        bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/suppliers_bill_get__form.fxml")));

    }


    public void hideMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.hideMenuBar(menuBarPanel);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

       SideBarOperations.showMenuBar(menuBarPanel);

    }


    public void exitSystem(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
