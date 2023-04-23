package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.morawakkorale_tea.model.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
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
    private ComboBox <Year> cmbSupplierYear;

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

    @FXML
    private Button btnLogOut;


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
        loadSupplierYears();

    }

    private void updateBarChart() throws SQLException {

        XYChart.Series<String, Number>[] series1 = new XYChart.Series[24];
        String year="";

        for (int i = 0; i < 24; i++) {

            if(i<10) {
                year="200"+i;
            }else {
                year="20"+i;
            }
            series1[i] = new XYChart.Series<>();
            series1[i].setName(year);
            series1[i].getData().add(new XYChart.Data<>("", SupplierModel.getNewSupplierCountYear(year)));
        }

        suppliersChart.getData().addAll(series1);

    }

    private void loadSupplierYears() {

            ObservableList<Year> obList = FXCollections.observableArrayList();
            List<Year> years = new ArrayList<>();

            for (int year = 2000; year <= 2023; year++) {

                years.add(Year.of(year));
            }

            for (Year year : years) {

                obList.add(year);
            }

            cmbSupplierYear.setItems(obList);

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

        int englishAfternoonCount = ProductModel.getProductCountCount("English Afternoon - Green Tea");
        int earlGreyCount = ProductModel.getProductCountCount("Earl Grey Tea - Black Tea");
        int englishBreakfastCount = ProductModel.getProductCountCount("English Breakfast - Black Tea");

        lblEnglishAfternoonCount.setText(String.valueOf(englishAfternoonCount));
        lblEarlGreyTeaCount.setText(String.valueOf(earlGreyCount));
        lblEnglishBreakfastTeaCount.setText(String.valueOf(englishBreakfastCount));

    }


    public void initializeChartUsingYear(ActionEvent actionEvent) throws SQLException {

        suppliersChart.getData().clear();

        Year year = cmbSupplierYear.getValue();
        int yr = year.getValue();

        XYChart.Series<String, Number>[] series = new XYChart.Series[12];

        for (int i = 0; i < 12; i++) {

            series[i] = new XYChart.Series<>();
            series[i].setName(String.valueOf(Month.of(i+1)));
            series[i].getData().add(new XYChart.Data<>("", SupplierModel.getNewSupplierCount(yr, i+1)));
        }

        suppliersChart.getData().addAll(series);


    }

    public void openSettingForm(MouseEvent mouseEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/setting_form.fxml"));
        stage.setTitle("Setting Window");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();

    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        SideBarOperations.logOut(btnLogOut);
    }
}
