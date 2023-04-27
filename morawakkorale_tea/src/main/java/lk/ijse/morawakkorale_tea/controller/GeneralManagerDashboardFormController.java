package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.model.OrderModel;
import lk.ijse.morawakkorale_tea.model.SupplierModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class GeneralManagerDashboardFormController implements Initializable {

    @FXML
    public Pane menuBarPanel;

    @FXML
    private PieChart chartProductSales;

    @FXML
    private AnchorPane bgPane;

    @FXML
    private Button btnLogOut;


    public void hideMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.hideMenuBar(menuBarPanel);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.showMenuBar(menuBarPanel);

    }

    public void exitSystem(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void openSettingForm(MouseEvent mouseEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/setting_form.fxml"));
        stage.setTitle("Setting Window");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeLineChart();
    }

    private void initializeLineChart() throws SQLException {

        chartProductSales.getData().add(new PieChart.Data("English Afternoon", OrderModel.getSaleValue("English Afternoon - Green Tea")));
        chartProductSales.getData().add(new PieChart.Data("Earl Grey Tea", OrderModel.getSaleValue("Earl Grey Tea - Black Tea")));
        chartProductSales.getData().add(new PieChart.Data("English Breakfast", OrderModel.getSaleValue("English Breakfast - Black Tea")));

    }

    public void customerFormLoad(ActionEvent actionEvent) throws IOException {


            bgPane.getChildren().clear();
            bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customers_manage_form.fxml")));

    }

    public void transportersFormLoad(ActionEvent actionEvent) throws IOException {

        ButtonType manage = new ButtonType("Manage", ButtonBar.ButtonData.YES);
        ButtonType bill = new ButtonType("Account", ButtonBar.ButtonData.YES);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Did you want to load Manage section or Account section", manage, bill).showAndWait();

        if (result.orElse(null)==bill) {

            bgPane.getChildren().clear();
            bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/transporters_payment_get_form.fxml")));


        }else{
            bgPane.getChildren().clear();
            bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/transporters_manage_form.fxml")));


        }

    }

    public void supplierFormLoad(ActionEvent actionEvent) throws IOException {

        ButtonType manage = new ButtonType("Manage", ButtonBar.ButtonData.YES);
        ButtonType bill = new ButtonType("Account", ButtonBar.ButtonData.YES);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Did you want to load Manage section or Account section", manage, bill).showAndWait();

        if (result.orElse(null)==bill) {

            bgPane.getChildren().clear();
            bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/suppliers_bill_get__form.fxml")));


        }else{
            bgPane.getChildren().clear();
            bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/suppliers_manage_form.fxml")));


        }


    }

    public void productFormLoad(ActionEvent actionEvent) throws IOException {

        ButtonType manage = new ButtonType("Manage", ButtonBar.ButtonData.YES);
        ButtonType bill = new ButtonType("Order", ButtonBar.ButtonData.YES);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Did you want to load Manage section or Order section", manage, bill).showAndWait();

        if (result.orElse(null)==bill) {

            bgPane.getChildren().clear();
            bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/orders_payment_add_form.fxml")));


        }else{
            bgPane.getChildren().clear();
            bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/product_manage_form.fxml")));


        }
    }

    public void showDashboard(MouseEvent mouseEvent) throws IOException {

        bgPane.getChildren().clear();
        bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/clone_general_manager_dashboard_form.fxml")));
    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        SideBarOperations.logOut(btnLogOut);

    }
}
