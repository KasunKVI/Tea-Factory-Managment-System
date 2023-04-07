package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.morawakkorale_tea.dto.Stock;
import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;
import lk.ijse.morawakkorale_tea.dto.tm.SupplierTeaValuesTM;
import lk.ijse.morawakkorale_tea.model.StockModel;
import lk.ijse.morawakkorale_tea.model.Supplier_StockModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class CollectorDashboardFormController implements Initializable {

    @FXML
    private Pane menuBarPanel;

    @FXML
    private Label lblStockId;

    @FXML
    private Pane supplierValuesAddPane;

    @FXML
    private TextField txtTransporterIdStock;

    @FXML
    private DatePicker dtpStockDate;

    @FXML
    private TextField txtStockValue;

    @FXML
    private TextField txtSupTeaValue;

    @FXML
    private TextField txtSupIdStock;

    @FXML
    private TextField txtSupTeaBagCount;

    @FXML
    private TableView<SupplierTeaValuesTM> tableSupplierTeaValues;

    @FXML
    private TableColumn<?,?> clmSupplierId;

    @FXML
    private TableColumn<?,?> clmSupplierBagCount;

    @FXML
    private TableColumn<?,?> clmSupplierTeaValue;

    @FXML
    private AnchorPane mainPanel;


    private String stock_id;
    private LocalDate date;
    private int stock_value;
    private int transporter_id;
    private int supplier_id;
    private int supplier_tea_value;
    private int supplier_bag_count;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generateNextStockId();
        setCellValueFactory();

    }

    private void setCellValueFactory() {

        clmSupplierId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        clmSupplierBagCount.setCellValueFactory(new PropertyValueFactory<>("Bag"));
        clmSupplierTeaValue.setCellValueFactory(new PropertyValueFactory<>("Value"));

    }


    public void hideMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.hideMenuBar(menuBarPanel);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.showMenuBar(menuBarPanel);


    }

    public void supplierValuesAddWindowShower(ActionEvent actionEvent) {

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

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

        discardOrClearStockAddValues(actionEvent);

    }

    public void generateNextStockId(){

        try {

            String stock_id = StockModel.generateStockId();
            lblStockId.setText(stock_id);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

    }

    public void addSupplierValuesToDatabase(ActionEvent actionEvent)  {

        supplier_id= Integer.parseInt(txtSupIdStock.getText());
        stock_id=lblStockId.getText();
        supplier_tea_value= Integer.parseInt(txtSupTeaValue.getText());
        supplier_bag_count= Integer.parseInt(txtSupTeaBagCount.getText());

        Supplier_Stock supplier_stock=new Supplier_Stock(supplier_id,stock_id,supplier_tea_value,supplier_bag_count,null);

        try {

            Supplier_StockModel.addSupplierValuesToDatabase(supplier_stock);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

        discardOrClearSupplierValues(actionEvent);

        try {

        ObservableList <SupplierTeaValuesTM> observableList = FXCollections.observableArrayList();
        List<Supplier_Stock> supplierValueList = Supplier_StockModel.getSupplierValue(stock_id);


        for(Supplier_Stock stock:supplierValueList){

            observableList.add(new SupplierTeaValuesTM(
               stock.getSup_id(),
               stock.getBag_count(),
               stock.getValue()

            ));
        }

        tableSupplierTeaValues.setItems(observableList);
        tableSupplierTeaValues.refresh();

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

    }

    public void discardOrClearSupplierValues(ActionEvent actionEvent) {

        txtSupIdStock.setText("");
        txtSupTeaValue.setText("");
        txtSupTeaBagCount.setText("");

    }

    public void discardOrClearStockAddValues(ActionEvent actionEvent) {

        txtTransporterIdStock.setText("");
        dtpStockDate.setValue(null);
        txtStockValue.setText("");

    }

    public void exitSystem(MouseEvent mouseEvent) {
        System.exit(1);
    }

    public void filledDate(ActionEvent actionEvent) {
        txtStockValue.requestFocus();
    }

    public void filledTotalValue(ActionEvent actionEvent) {
        addStockToDatabase(actionEvent);
    }

    public void filledSupplierId(ActionEvent actionEvent) {
        txtSupTeaValue.requestFocus();
    }

    public void filledTeaValue(ActionEvent actionEvent) {
        txtSupTeaBagCount.requestFocus();
    }

    public void filledBagCount(ActionEvent actionEvent) {
        addSupplierValuesToDatabase(actionEvent);
    }

    public void closeSupplierValuesAddForm(ActionEvent actionEvent) {
        supplierValuesAddPane.setVisible(false);
    }





    public void logOutFromCollectorDashboard(MouseEvent mouseEvent) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();

    }


}
