package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.morawakkorale_tea.model.OrderModel;
import lk.ijse.morawakkorale_tea.model.ProductModel;
import lk.ijse.morawakkorale_tea.model.SupplierModel;
import lk.ijse.morawakkorale_tea.model.TransporterModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class AccountantDashboardFormController implements Initializable {

    @FXML
    public AnchorPane bgPane;

    @FXML
    private Label lblSupplierCount;
    @FXML
    private Label lblTransportersCount;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private AreaChart<String,Number> orderSummeryAreaChart;
    @FXML
    private BarChart<String,Number> productSummeryBarChart;
    @FXML
    public Pane menuBarPanel;
    @FXML
    private Button btnLogOut;

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

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {

            initializeAreaChart();
            initializeBarChart();
            lblSupplierCount.setText(String.valueOf(SupplierModel.getSuppliersCount()));
            lblTransportersCount.setText(String.valueOf(TransporterModel.getTransportersCount()));
            initializeTimeLabel();

    }

    private void initializeTimeLabel() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(format.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime time = LocalTime.now();
            lblTime.setText(time.getHour()+":"+time.getMinute()+":"+time.getSecond());
        }), new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void initializeBarChart() throws SQLException {


        XYChart.Series<String, Number>[] series = new XYChart.Series[3];

        series[0] = new XYChart.Series<>();
        series[0].setName("English Afternoon");
        series[0].getData().add(new XYChart.Data<>("", ProductModel.getProductCount("English Afternoon - Green Tea")));

        series[1] = new XYChart.Series<>();
        series[1].setName("English Breakfast");
        series[1].getData().add(new XYChart.Data<>("", ProductModel.getProductCount("English Breakfast - Black Tea")));

        series[2] = new XYChart.Series<>();
        series[2].setName("Earl Grey");
        series[2].getData().add(new XYChart.Data<>("", ProductModel.getProductCount("Earl Grey Tea - Black Tea")));


        productSummeryBarChart.getData().addAll(series);

    }

    private void initializeAreaChart() throws SQLException {

        XYChart.Series [] series = new XYChart.Series[12];

        for (int i = 0; i < 12; i++) {

            series[i] = new XYChart.Series<>();
            series[i].setName(String.valueOf(Month.of(i+1)));
            series[i].getData().add(new XYChart.Data<>(String.valueOf(Month.of(i+1)), OrderModel.getMonthlyOrderCount(i+1)));

        }

        orderSummeryAreaChart.getData().addAll(series);

    }

    public void loadTransporterBills(ActionEvent actionEvent) throws IOException {

        bgPane.getChildren().clear();
        bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/transporters_payment_get_form.fxml")));

    }

    public void loadOrderAdder(ActionEvent actionEvent) throws IOException {

        bgPane.getChildren().clear();
        bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/orders_payment_add_form.fxml")));

    }

    public void openSettingForm(MouseEvent mouseEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/setting_form.fxml"));
        stage.setTitle("Setting Window");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();

    }

    public void showDashboard(MouseEvent mouseEvent) throws IOException {

        bgPane.getChildren().clear();
        bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/clone_accountant_dashboard_form.fxml")));
    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        SideBarOperations.logOut(btnLogOut);
    }
}
