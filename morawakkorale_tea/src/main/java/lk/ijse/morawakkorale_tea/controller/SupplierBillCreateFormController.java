package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.sql.SQLException;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class SupplierBillCreateFormController {

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


    public void showReport(ActionEvent actionEvent) throws SQLException, FileNotFoundException, JRException {

        int month = cmbMonthSelect.getValue().getValue();

        List<Integer> supplierIds = SupplierModel.getAllIds();
        List<Supplier_Bill> supplier = new ArrayList<>();

        for (Integer id : supplierIds) {

            int totalValue = Supplier_StockModel.getSupplierValues(id,month,"value");
            int bagCount = Supplier_StockModel.getSupplierValues(id,month,"bag_count");
            int rate = Integer.parseInt(txtMonthlyRate.getText());
            Double payment = (double) ((totalValue - bagCount) * rate);

            supplier.add(new Supplier_Bill(
                    id,
                    totalValue,
                    bagCount,
                    payment
            ));

        }


        File file = ResourceUtils.getFile("/home/kaviyakv/Desktop/Morawakkorale_Tea/morawakkorale_tea/src/main/java/lk/ijse/morawakkorale_tea/reports/supplierBills.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(supplier);
        Map<String,Object> map = new HashMap<>();
        map.put("CreatedBy","Kasun Kavinda - GDSE65");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map,dataSource);
        JasperViewer.viewReport(jasperPrint,false);
    }

    public Supplier searchedSupplierId(ActionEvent actionEvent) {

        String id = txtSupplierIdSearch.getText();

        try {

            Supplier supplier = SupplierModel.searchSupplierFromDatabase(id);

            if (supplier==null){

                new Alert(Alert.AlertType.ERROR,"There is no supplier in this id").show();
            }

            return supplier;
        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }
        return null;
    }

    public void countSupplierLastPayment(ActionEvent actionEvent) {
    }

    public void selectedMonthOfSupplier(ActionEvent actionEvent) {

        Supplier supplier = searchedSupplierId(actionEvent);

        int month = cmbMonthSelect.getValue().getValue();
        int id = Integer.parseInt(txtSupplierIdSearch.getText());

        try {

            int totalValue = Supplier_StockModel.getSupplierValues(id,month,"value");
            int bagCount = Supplier_StockModel.getSupplierValues(id,month,"bag_count");

            lblSupplierName.setText(supplier.getName());
            lblSupplierId.setText(String.valueOf(supplier.getId()));
            lblMonth.setText(String.valueOf(cmbMonthSelect.getValue()));
            lblSupTotalLeafValue.setText(String.valueOf(totalValue));
            lblSupBagCount.setText(String.valueOf(bagCount));
            lblSupLastLeafValue.setText(String.valueOf(totalValue-bagCount));
            txtMonthlyRate.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    double last = Integer.parseInt(lblSupLastLeafValue.getText());
                    double rate = Integer.parseInt(txtMonthlyRate.getText());
                    lblSupLastPayment.setText(String.valueOf(rate * last));
                }
            });


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void initializedMonthBox(Event event) {


        comboBoxAdd(cmbMonthSelect);

    }

    static void comboBoxAdd(ComboBox<Month> cmbMonthSelect) {
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

        int pay = PaymentModel.getPaymentId();
        int payId=pay+1;
        System.out.println(payId);

        Payment payment = new Payment(payId,Integer.parseInt(txtMonthlyRate.getText()),"Supplier Bill",Integer.parseInt(lblSupLastPayment.getText()),(lblSupplierName.getText())+"-->"+(lblMonth.getText()),Integer.parseInt(lblSupplierId.getText()),null);
        try {
            PaymentModel.addPayment(payment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
