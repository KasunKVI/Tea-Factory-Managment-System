package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.*;
import lk.ijse.morawakkorale_tea.model.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManagerDashboardFormController implements Initializable {


    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private Pane menuBarPanel;
    @FXML
    private BarChart<String,Number> suppliersChart;

    //available stock pane components
    @FXML
    private Label lblEnglishAfternoonCount;
    @FXML
    private Label lblEarlGreyTeaCount;
    @FXML
    private Label lblEnglishBreakfastTeaCount;

    //company summary pane components
    @FXML
    private Label lblSuppliersCount;
    @FXML
    private Label lblTransportersCount;
    @FXML
    private Label lblCustomersCount;

    @FXML
    private ImageView imgHome;
    @FXML
    private Button btnSupplier;
    @FXML
    private Button btnTransporter;
    @FXML
    private Button btnCustomer;
    @FXML
    private Button btnProduct;


    public void hideMenuBar(MouseEvent mouseEvent) {

      SideBarOperations.hideMenuBar(menuBarPanel);

    }

    public void showMenuBar(MouseEvent mouseEvent) {

       SideBarOperations.showMenuBar(menuBarPanel);

    }


    public void showSupplierManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/suppliers_manage_form.fxml")));

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

    public void showDashboard(MouseEvent mouseEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/clone_manager_dashboard_form.fxml")));

    }



    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updatePanes();
        updateBarChart();

    }

    private void updateBarChart() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("New Suppliers");
        series.getData().add(new XYChart.Data<>("January", 10));
        series.getData().add(new XYChart.Data<>("February",20));
        series.getData().add(new XYChart.Data<>("March",20));
        series.getData().add(new XYChart.Data<>("April",0));
        series.getData().add(new XYChart.Data<>("May",3));
        series.getData().add(new XYChart.Data<>("June",30));
        series.getData().add(new XYChart.Data<>("July",10));
        series.getData().add(new XYChart.Data<>("August",25));
        series.getData().add(new XYChart.Data<>("September",50));
        series.getData().add(new XYChart.Data<>("October",8));
        series.getData().add(new XYChart.Data<>("November",16));
        series.getData().add(new XYChart.Data<>("December",24));


        suppliersChart.getData().add(series);


    }


    public void exitSystem(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void updatePanes() throws SQLException {

        int supplier_count = SupplierModel.getSuppliersCount();
        int transporters_count = TransporterModel.getTransportersCount();
        int customers_count = CustomerModel.getCustomerCount();

        lblSuppliersCount.setText(String.valueOf(supplier_count));
        lblTransportersCount.setText(String.valueOf(transporters_count));
        lblCustomersCount.setText(String.valueOf(customers_count));

        int englishAfternoonCount = ProductModel.getProductCountCount("English Afternoon");
        int earlGreyCount = ProductModel.getProductCountCount("Earl Grey");
        int englishBreakfastCount = ProductModel.getProductCountCount("English Breakfast");

        lblEnglishAfternoonCount.setText(String.valueOf(englishAfternoonCount));
        lblEarlGreyTeaCount.setText(String.valueOf(earlGreyCount));
        lblEnglishBreakfastTeaCount.setText(String.valueOf(englishBreakfastCount));



    }


}
