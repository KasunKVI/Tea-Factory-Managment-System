package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ManagerDashboardFormController  {

    @FXML
    public Pane menuBarPanel;
    public AnchorPane backgroundPane;

    public void hideMenuBar(MouseEvent mouseEvent) {

        MenuBarOperation.fadeMenuBar(menuBarPanel,1,0,-88);

        menuBarPanel.setDisable(true);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

        menuBarPanel.setDisable(false );
        MenuBarOperation.fadeMenuBar(menuBarPanel,0,1,+88);
        menuBarPanel.setVisible(true);

    }


    public void showSupplierManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/suppliers_manage_form.fxml")));

    }

    public void logout(ActionEvent actionEvent) {
    }

    public void showBuyersManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customers_manage_form.fxml")));

    }

    public void showTransportersManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/transporters_manage_form.fxml")));

    }

    public void showProductManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/product_manage_form.fxml")));

    }
}
