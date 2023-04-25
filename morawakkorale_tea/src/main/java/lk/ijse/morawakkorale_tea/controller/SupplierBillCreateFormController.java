package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.db.DBConnection;
import lk.ijse.morawakkorale_tea.dto.Payment;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.dto.Supplier_Bill;
import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;
import lk.ijse.morawakkorale_tea.model.PaymentModel;
import lk.ijse.morawakkorale_tea.model.SupplierModel;
import lk.ijse.morawakkorale_tea.model.Supplier_StockModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRResourcesUtil;
import net.sf.jasperreports.repo.Resource;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class SupplierBillCreateFormController implements Initializable {

    @FXML
    private ComboBox<Month> cmbMonthSelect;

    @FXML
    private Label lblMonth;

    @FXML
    private Label lblSupBagCount;

    @FXML
    private Label lblSupLastLeafValue;

    @FXML
    private Label lblSupLastPayment;

    @FXML
    private Label lblSupTotalLeafValue;

    @FXML
    private Label lblSupplierId;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TextField txtMonthlyRate;

    @FXML
    private TextField txtSupplierIdSearch;

    private boolean condition = true;

    public void showReport(ActionEvent actionEvent) throws SQLException, FileNotFoundException, JRException {

        if(!condition||txtSupplierIdSearch.getText().isEmpty()||txtMonthlyRate.getText().isEmpty()||cmbMonthSelect.getValue()==null){

            new Alert(Alert.AlertType.ERROR, "Input Details First").show();

        }else {

            int month = cmbMonthSelect.getValue().getValue();

            List<Integer> supplierIds = SupplierModel.getAllIds();
            List<Supplier_Bill> supplier = new ArrayList<>();

            for (Integer id : supplierIds) {

                int totalValue = Supplier_StockModel.getSupplierValues(id, month, "value");
                int bagCount = Supplier_StockModel.getSupplierValues(id, month, "bag_count");
                int rate = Integer.parseInt(txtMonthlyRate.getText());
                Double payment = (double) ((totalValue - bagCount) * rate);

                supplier.add(new Supplier_Bill(
                        id,
                        totalValue,
                        bagCount,
                        payment
                ));
            }

            File file = ResourceUtils.getFile("/home/kaviyakv/Desktop/Morawakkorale_Tea/morawakkorale_tea/src/main/resources/reports/supplierBills.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(supplier);
            Map<String, Object> map = new HashMap<>();
            map.put("CreatedBy", "Kasun Kavinda - GDSE65");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    public Supplier searchedSupplierId(ActionEvent actionEvent) {

        if(!condition){

            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();

        }else {

            String id = txtSupplierIdSearch.getText();

            try {

                Supplier supplier = SupplierModel.searchSupplierFromDatabase(id);

                if (supplier == null) {

                    new Alert(Alert.AlertType.ERROR, "There is no supplier in this id").show();
                    FontChanger.setSearchBarRed(txtSupplierIdSearch);
                    txtSupplierIdSearch.requestFocus();
                }

                return supplier;
            } catch (SQLException throwable) {

                throwable.printStackTrace();

            }

        }
        return null;
    }

    public void selectedMonthOfSupplier(ActionEvent actionEvent) {

        if(!condition||txtSupplierIdSearch.getText().isEmpty()){

            new Alert(Alert.AlertType.ERROR, "Input Id First").show();

        }else {


            Supplier supplier = searchedSupplierId(actionEvent);

            int month = cmbMonthSelect.getValue().getValue();
            int id = Integer.parseInt(txtSupplierIdSearch.getText());

            try {

                int totalValue = Supplier_StockModel.getSupplierValues(id, month, "value");
                int bagCount = Supplier_StockModel.getSupplierValues(id, month, "bag_count");

                lblSupplierName.setText(supplier.getName());
                lblSupplierId.setText(String.valueOf(supplier.getId()));
                lblMonth.setText(String.valueOf(cmbMonthSelect.getValue()));
                lblSupTotalLeafValue.setText(String.valueOf(totalValue));
                lblSupBagCount.setText(String.valueOf(bagCount));
                lblSupLastLeafValue.setText(String.valueOf(totalValue - bagCount));

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

    }

    public void initializedMonthBox() {

        ObservableList<Month> obList = FXCollections.observableArrayList();
        List<Month> months = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {

            months.add(Month.of(month));
        }

        for (Month month : months) {

            obList.add(month);
        }

        cmbMonthSelect.setItems(obList);

    }

    public void correctSupplierMonthlyPayment(ActionEvent actionEvent) throws SQLException {

        if(!condition||txtSupplierIdSearch.getText().isEmpty()||cmbMonthSelect.getValue()==null||txtMonthlyRate.getText().isEmpty()||lblSupLastPayment.getText().equals("0.0")){
            new Alert(Alert.AlertType.ERROR, "Input Details First").show();
        }else {

            int pay = PaymentModel.getPaymentId();
            int payId = pay + 1;

            Payment payment = new Payment(payId, Integer.parseInt(txtMonthlyRate.getText()), "Supplier Bill", Double.parseDouble(lblSupLastPayment.getText()), (lblSupplierName.getText()) + "-->" + (lblMonth.getText()), Integer.parseInt(lblSupplierId.getText()), null);
            try {

                int month = cmbMonthSelect.getValue().getValue();
                int id = Integer.parseInt(txtSupplierIdSearch.getText());

                PaymentModel.addPayment(payment);
                Supplier_StockModel.addPayment(id,month);
                ButtonType yes = new ButtonType("payment", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("report", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Supplier payment added successful. Do yo want to add another supplier payment or see report", yes, no).showAndWait();

                if (result.orElse(no) == yes) {

                    clearForm();

                }

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public void searchSupplierId(KeyEvent keyEvent) {

        if (!txtSupplierIdSearch.getText().matches(Regex.idRegEx())){
            condition=false;
            FontChanger.setSearchBarRed(txtSupplierIdSearch);
        }else {
            FontChanger.setSearchBarBlack(txtSupplierIdSearch);
            condition = true;
            ActionEvent actionEvent=new ActionEvent();
            searchedSupplierId(actionEvent);
        }
    }

    public void enterMonthlySupplierRate(KeyEvent keyEvent) {

        if (!txtMonthlyRate.getText().matches(Regex.valueRegEx())){
            condition=false;
            FontChanger.setTextColorRed(txtMonthlyRate);
        }else {
            FontChanger.setTextBlack(txtMonthlyRate);
            condition = true;
            double last = Integer.parseInt(lblSupLastLeafValue.getText());
            double rate = Integer.parseInt(txtMonthlyRate.getText());
            lblSupLastPayment.setText(String.valueOf(rate * last));

        }
    }

    public void checkMonth(MouseEvent mouseEvent) {

        if(cmbMonthSelect.getValue()==null){
            new Alert(Alert.AlertType.ERROR, "Select Month First").show();
        }
        if(txtSupplierIdSearch.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Input Id First").show();
        }
    }

    public void clearForm(){

        txtSupplierIdSearch.clear();
        txtMonthlyRate.clear();
        lblSupplierName.setText("");
        lblSupplierId.setText("");
        lblSupTotalLeafValue.setText("");
        lblSupBagCount.setText("");
        lblSupLastLeafValue.setText("");
        lblSupLastPayment.setText("");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializedMonthBox();

    }
}
