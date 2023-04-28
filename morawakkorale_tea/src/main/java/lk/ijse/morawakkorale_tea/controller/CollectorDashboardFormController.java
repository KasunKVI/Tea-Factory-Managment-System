package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.morawakkorale_tea.dto.Stock;
import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;
import lk.ijse.morawakkorale_tea.dto.tm.SupplierTeaValuesTM;
import lk.ijse.morawakkorale_tea.model.AddStockModel;
import lk.ijse.morawakkorale_tea.model.StockModel;
import lk.ijse.morawakkorale_tea.model.SupplierModel;
import lk.ijse.morawakkorale_tea.model.TransporterModel;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class CollectorDashboardFormController implements Initializable,MenuBarControl {

    //components form collector dashboard
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
    private Button btnLogOut;

    private boolean condition = false;

    //observableList for initialize table
    ObservableList <SupplierTeaValuesTM> observableList = FXCollections.observableArrayList();


    //call two methods while load form
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

    //show supplier value add window
    public void supplierValuesAddWindowShower(ActionEvent actionEvent) {

        if(!condition){
            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();

        }else {

            supplierValuesAddPane.setVisible(true);
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), supplierValuesAddPane);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(1);
            fadeTransition1.play();
        }

    }

    //add new stock to a database
    public void addStockToDatabase(ActionEvent actionEvent) {

        if(!condition){

            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();
        }else {


            String stock_id = lblStockId.getText();
            LocalDate date = dtpStockDate.getValue();
            int stock_value = Integer.parseInt(txtStockValue.getText());
            int transporter_id = Integer.parseInt(txtTransporterIdStock.getText());

            List<Supplier_Stock> supplierStock = new ArrayList<>();

            Stock stock = new Stock(stock_id, date.toString(), stock_value, transporter_id);

            for (int i = 0; i < tableSupplierTeaValues.getItems().size(); i++) {

                SupplierTeaValuesTM tm = observableList.get(i);

                Supplier_Stock supplier_stock = new Supplier_Stock(tm.getId(), stock_id, tm.getValue(), tm.getBag(), null, date);
                supplierStock.add(supplier_stock);
            }

            try {

                boolean isAdd = AddStockModel.addStockToDatabase(supplierStock, stock);

                if (isAdd) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Stock Added").show();
                    tableSupplierTeaValues.getItems().clear();
                    discardOrClearStockAddValues(actionEvent);

                }
            } catch (SQLException throwable) {

                throwable.printStackTrace();

            }

        }
    }

    //generate next stock id
    public void generateNextStockId(){

        try {

            String stock_id = StockModel.generateStockId();
            lblStockId.setText(stock_id);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

    }

    //add supplier tea values to tableView
    public void addSupplierValuesToTable(ActionEvent actionEvent)  {

        if(!condition){

            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();

        }else {

            int supplier_id = Integer.parseInt(txtSupIdStock.getText());
            int supplier_tea_value = Integer.parseInt(txtSupTeaValue.getText());
            int supplier_bag_count = Integer.parseInt(txtSupTeaBagCount.getText());

            discardOrClearSupplierValues(actionEvent);

            SupplierTeaValuesTM supplierTeaValuesTM = new SupplierTeaValuesTM(supplier_id, supplier_bag_count, supplier_tea_value);

            observableList.add(supplierTeaValuesTM);
            tableSupplierTeaValues.setItems(observableList);
            tableSupplierTeaValues.refresh();

            txtSupIdStock.requestFocus();
        }

    }

    //clear supplier value window
    public void discardOrClearSupplierValues(ActionEvent actionEvent) {

        txtSupIdStock.setText("");
        txtSupTeaValue.setText("");
        txtSupTeaBagCount.setText("");

    }

    //clear stock add window
    public void discardOrClearStockAddValues(ActionEvent actionEvent) {

        txtTransporterIdStock.setText("");
        dtpStockDate.setValue(null);
        txtStockValue.setText("");

    }


    //check stock date if already added
    public void filledDate(ActionEvent actionEvent) {

        LocalDate lDate = dtpStockDate.getValue();

        if(lDate != null){

            int date = lDate.getDayOfMonth();
            int month = lDate.getMonthValue();
            int year = LocalDate.now().getYear();


            if (year != lDate.getYear()) {

                new Alert(Alert.AlertType.ERROR, "This value too old").show();

            } else {

                try {
                    boolean isExist = StockModel.isExist(date, month);

                    if (isExist) {
                        new Alert(Alert.AlertType.ERROR, "This Day Stock Already Added").show();

                    } else {
                        txtStockValue.requestFocus();
                    }
                } catch (SQLException er) {
                    er.printStackTrace();
                }
            }
        }

    }


    public void filledTotalValue(ActionEvent actionEvent) {
        supplierValuesAddWindowShower(actionEvent);
    }

    public void filledSupplierId(ActionEvent actionEvent) {

        if(!condition) {
            new Alert(Alert.AlertType.ERROR, "Input Valid Supplier Id").show();
            txtSupIdStock.requestFocus();
        }else {
            txtSupTeaValue.requestFocus();
        }
    }

    public void filledTeaValue(ActionEvent actionEvent) {
        if(!condition) {

            new Alert(Alert.AlertType.ERROR, "Input Valid Tea value").show();
            txtSupTeaValue.requestFocus();
        }else {
            txtSupTeaBagCount.requestFocus();
        }
    }

    public void filledBagCount(ActionEvent actionEvent) {
        if(!condition) {
            new Alert(Alert.AlertType.ERROR, "Input Valid BagCount").show();
            txtSupTeaBagCount.requestFocus();
        }else {
            addSupplierValuesToTable(actionEvent);
        }
    }

    //close supplier value add window
    public void closeSupplierValuesAddForm(ActionEvent actionEvent) {
        condition=true;
        supplierValuesAddPane.setVisible(false);
    }

    /*these five  methods use for operate menu bar*/
    @Override
    public void hideMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.hideMenuBar(menuBarPanel);

    }

    @Override
    public void showMenuBar(MouseEvent mouseEvent) {

        SideBarOperations.showMenuBar(menuBarPanel);


    }

    @Override
    public void exitSystem(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @Override
    public void openSettingForm(MouseEvent mouseEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/setting_form.fxml"));
        stage.setTitle("Setting Window");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();

    }

    @Override
    public void logOut(ActionEvent actionEvent) throws IOException {

       SideBarOperations.logOut(btnLogOut);

    }

    //validate transporter id
    public void enterTransporterId(KeyEvent keyEvent) throws SQLException {

        if (!txtTransporterIdStock.getText().matches(Regex.idRegEx())){
            condition=false;
            FontChanger.setTextColorRed(txtTransporterIdStock);
        }else if(TransporterModel.isExist(txtTransporterIdStock.getText())) {
                    condition=false;
                    FontChanger.setTextColorRed(txtTransporterIdStock);

        }else{
            FontChanger.setTextBlack(txtTransporterIdStock);
            condition = true;
        }


    }

    //validate tea value
    public void enterTeaValue(KeyEvent keyEvent) {

        if (!txtStockValue.getText().matches(Regex.teaValueTrp())){
            condition=false;
            FontChanger.setTextColorRed(txtStockValue);
        }else {
            FontChanger.setTextBlack(txtStockValue);
            condition = true;
        }
    }

    //validate supplier id
    public void enterSupplierId(KeyEvent keyEvent) throws SQLException {

        if (!txtSupIdStock.getText().matches(Regex.idRegEx())) {
            condition = false;
            FontChanger.setTextColorRed(txtSupIdStock);

        }else if (SupplierModel.isExist(txtSupIdStock.getText())) {

                condition = false;
                FontChanger.setTextColorRed(txtSupIdStock);
        } else {
                FontChanger.setTextBlack(txtSupIdStock);
                condition = true;
        }

    }

    //validate supplier tia value
    public void enterSupplierTea(KeyEvent keyEvent) {

        if (!txtSupTeaValue.getText().matches(Regex.teaValueSup())){
            condition = false;
            FontChanger.setTextColorRed(txtSupTeaValue);
        }else {
            FontChanger.setTextBlack(txtSupTeaValue);
            condition = true;
        }
    }

    //validate supplier bag count
    public void enterSupplierBag(KeyEvent keyEvent) {

        if (!txtSupTeaBagCount.getText().matches(Regex.valueRegEx())){
            condition = false;
            FontChanger.setTextColorRed(txtSupTeaBagCount);
        }else {
            FontChanger.setTextBlack(txtSupTeaBagCount);
            condition = true;
        }
    }

    //check transporter id is valid
    public void checkTransporterId(Event event) {

        if(!condition) {

            new Alert(Alert.AlertType.ERROR, "Input Valid Transporter Id").show();
            txtTransporterIdStock.requestFocus();
        }

        }
}
