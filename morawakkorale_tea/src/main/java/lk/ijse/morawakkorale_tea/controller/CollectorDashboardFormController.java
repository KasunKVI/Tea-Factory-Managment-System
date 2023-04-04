package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.morawakkorale_tea.dto.Stock;
import lk.ijse.morawakkorale_tea.model.StockModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class CollectorDashboardFormController implements Initializable {

    @FXML
    Button btnSetting;

    @FXML
    public Pane menuBarPanel;

    @FXML
    AnchorPane pane2;

    @FXML
    AnchorPane menu;

    @FXML
    private Label lblStockId;

    @FXML
    public Pane supplierValuesAddPane;

    public TextField txtTransporterIdStock;
    public DatePicker dtpStockDate;
    public TextField txtStockValue;


    String stock_id;
    LocalDate date;
    int stock_value;
    int transporter_id;




    public void btnSettingOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void btncolorchanger(MouseEvent mouseEvent) {

        btnSetting.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 15px;");

    }

    public void btncolorback(MouseEvent mouseEvent) {

        btnSetting.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 15px;");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generateNextStockId();

    }


    public void hideMenuBar(MouseEvent mouseEvent) {

        MenuBarOperation.fadeMenuBar(menuBarPanel,1,0,-88);

        menuBarPanel.setDisable(true);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

        menuBarPanel.setDisable(false );
        MenuBarOperation.fadeMenuBar(menuBarPanel,0,1,+88);
        menuBarPanel.setVisible(true);

    }

    public void SuppierValuesAddWindowShower(ActionEvent actionEvent) {

        supplierValuesAddPane.setVisible(true);
        FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),supplierValuesAddPane);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        fadeTransition1.play();

    }

    public void addStockToDatabase(ActionEvent actionEvent) {


        stock_id=lblStockId.getText();
        date=dtpStockDate.getValue();
        stock_value= Integer.parseInt(txtStockValue.getText());
        transporter_id= Integer.parseInt(txtTransporterIdStock.getText());

        Stock stock=new Stock(stock_id,date,stock_value,transporter_id);

        try {

            StockModel.addStockToDatabase(stock);

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }


    }

    public void generateNextStockId(){

        try {

            String stock_id = StockModel.generateStockId();
            lblStockId.setText(stock_id);

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

    }

    public void addSupplierValuesToDatabase(ActionEvent actionEvent) {



    }

    public void completeSupplierValuesAdd(ActionEvent actionEvent) {

        supplierValuesAddPane.setVisible(false);

    }
}
