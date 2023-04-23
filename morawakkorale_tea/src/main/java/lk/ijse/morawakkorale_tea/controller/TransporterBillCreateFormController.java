package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.morawakkorale_tea.dto.*;
import lk.ijse.morawakkorale_tea.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransporterBillCreateFormController {

    @FXML
    private ComboBox<Month> cmbMonthSelect;

    @FXML
    private Label lblMonth;

    @FXML
    private Label lblTransporterId;


    @FXML
    private Label lblTransporterLastPayment;

    @FXML
    private Label lblTransporterName;

    @FXML
    private Label lblTransporterTotalLeafValue;

    @FXML
    private Label lblTransporterRoute;

    @FXML
    private TextField txtMonthlyRate;

    @FXML
    private TextField txtTransporterIdSearch;


    public Transporter searchedTransporterId(ActionEvent actionEvent) {

        String id = txtTransporterIdSearch.getText();

        try {

            Transporter transporter = TransporterModel.searchTransporterFromDatabase(id);

            if (transporter==null){

                new Alert(Alert.AlertType.ERROR,"There is no transporter in this id").show();
            }

            return transporter;

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }
        return null;
    }

    public void showReport(ActionEvent actionEvent) throws SQLException, FileNotFoundException, JRException {

        int month = cmbMonthSelect.getValue().getValue();

        List<Integer> trpIds = TransporterModel.getAllIds();
        List<TransporterBill> transporter = new ArrayList<>();

        for (Integer id : trpIds) {

            int totalValue = StockModel.getTransporterValues(id,month);
            int rate = Integer.parseInt(txtMonthlyRate.getText());
            String route = TransporterModel.getRoute(id);
            Double payment = (double) (totalValue * rate);

            transporter.add(new TransporterBill(
                    id,
                    route,
                    totalValue,
                    payment
            ));

        }


        File file = ResourceUtils.getFile("/home/kaviyakv/Desktop/Morawakkorale_Tea/morawakkorale_tea/src/main/java/lk/ijse/morawakkorale_tea/reports/transporterBills.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transporter);
        Map<String,Object> map = new HashMap<>();
        map.put("CreatedBy","Kasun Kavinda - GDSE65");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map,dataSource);
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void countTransporterLastPayment(ActionEvent actionEvent) {
    }

    public void correctTransporterMonthlyPayment(ActionEvent actionEvent) throws SQLException {

        int pay = PaymentModel.getPaymentId();
        int payId=pay+1;

        Integer tp_id = Integer.parseInt(lblTransporterId.getText());

        Payment payment = new Payment(payId,Integer.parseInt(txtMonthlyRate.getText()),"Transporter Bill",Double.parseDouble(lblTransporterLastPayment.getText()),(lblTransporterName.getText())+"-->"+(lblMonth.getText()),null,tp_id);

        try {
            PaymentModel.addPayment(payment);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void selectedMonthOfTransporter(ActionEvent actionEvent) {

        Transporter transporter = searchedTransporterId(actionEvent);

        int month = cmbMonthSelect.getValue().getValue();
        int id = transporter.getId();

        try {

            int totalValue = StockModel.getTransporterValues(id,month);


            lblTransporterId.setText(String.valueOf(transporter.getId()));
            lblTransporterName.setText(transporter.getName());
            lblMonth.setText(String.valueOf(cmbMonthSelect.getValue()));
            lblTransporterTotalLeafValue.setText(String.valueOf(totalValue));
            lblTransporterRoute.setText(transporter.getRoute());

            txtMonthlyRate.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    double rate = Double.parseDouble(txtMonthlyRate.getText());
                    lblTransporterLastPayment.setText(String.valueOf(rate * totalValue));
                }
            });
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void initializedMonthBox(Event event) {

        SupplierBillCreateFormController.comboBoxAdd(cmbMonthSelect);

    }
}
