package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.morawakkorale_tea.dto.*;
import lk.ijse.morawakkorale_tea.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.*;

public class TransporterBillCreateFormController implements Initializable {

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

    private boolean condition=true;


    public Transporter searchedTransporterId(ActionEvent actionEvent) {

        if(!condition){

            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();

        }else {

            String id = txtTransporterIdSearch.getText();

            try {

                Transporter transporter = TransporterModel.searchTransporterFromDatabase(id);

                if (transporter == null) {

                    new Alert(Alert.AlertType.ERROR, "There is no transporter in this id").show();
                    FontChanger.setSearchBarRed(txtTransporterIdSearch);
                    txtTransporterIdSearch.requestFocus();
                }

                return transporter;

            } catch (SQLException throwable) {

                throwable.printStackTrace();

            }
        }
            return null;

    }

    public void showReport(ActionEvent actionEvent) throws SQLException, FileNotFoundException, JRException {

        if (!condition||txtTransporterIdSearch.getText().isEmpty()||txtMonthlyRate.getText().isEmpty()||cmbMonthSelect.getValue()==null){

            new Alert(Alert.AlertType.ERROR, "Input Details First").show();

        }else {

            int month = cmbMonthSelect.getValue().getValue();

            List<Integer> trpIds = TransporterModel.getAllIds();
            List<TransporterBill> transporter = new ArrayList<>();

            for (Integer id : trpIds) {

                int totalValue = StockModel.getTransporterValues(id, month);
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


            File file = ResourceUtils.getFile("/home/kaviyakv/Desktop/Morawakkorale_Tea/morawakkorale_tea/src/main/resources/reports/transporterBills.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transporter);
            Map<String, Object> map = new HashMap<>();
            map.put("CreatedBy", "Kasun Kavinda - GDSE65");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    public void correctTransporterMonthlyPayment(ActionEvent actionEvent) throws SQLException {

        if(!condition||txtTransporterIdSearch.getText().isEmpty()||txtMonthlyRate.getText().isEmpty()||cmbMonthSelect.getValue()==null||lblTransporterLastPayment.getText().equals("0.0")){

            new Alert(Alert.AlertType.ERROR, "Input Details First").show();

        }else {
            int pay = PaymentModel.getPaymentId();
            int payId = pay + 1;

            Integer tp_id = Integer.parseInt(lblTransporterId.getText());

            Payment payment = new Payment(payId, Integer.parseInt(txtMonthlyRate.getText()), "Transporter Bill", Double.parseDouble(lblTransporterLastPayment.getText()), (lblTransporterName.getText()) + "-->" + (lblMonth.getText()), null, tp_id);

            try {

                int month = cmbMonthSelect.getValue().getValue();
                int id = Integer.parseInt(txtTransporterIdSearch.getText());

                PaymentModel.addPayment(payment);
                StockModel.addPayment(id,month);
                ButtonType yes = new ButtonType("payment", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("report", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Transporter payment added successful. Do yo want to add another supplier payment or see report", yes, no).showAndWait();

                if (result.orElse(no) == yes) {

                    clearForm();
                    txtTransporterIdSearch.requestFocus();

                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private void clearForm() {

        txtMonthlyRate.clear();
        txtTransporterIdSearch.clear();

        lblTransporterId.setText("");
        lblTransporterName.setText("");
        lblTransporterRoute.setText("");
        lblTransporterLastPayment.setText("");
        lblTransporterTotalLeafValue.setText("");

    }

    public void selectedMonthOfTransporter(ActionEvent actionEvent) {

        if (!condition||txtTransporterIdSearch.getText().isEmpty()){

            new Alert(Alert.AlertType.ERROR, "Input Id First").show();

        }else {

            Transporter transporter = searchedTransporterId(actionEvent);

            int month = cmbMonthSelect.getValue().getValue();
            int id = transporter.getId();


            try {

                int totalValue = StockModel.getTransporterValues(id, month);

                    lblTransporterId.setText(String.valueOf(transporter.getId()));
                    lblTransporterName.setText(transporter.getName());
                    lblMonth.setText(String.valueOf(cmbMonthSelect.getValue()));
                    lblTransporterTotalLeafValue.setText(String.valueOf(totalValue));
                    lblTransporterRoute.setText(transporter.getRoute());


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

    public void enterTransporterMonthlyRate(KeyEvent keyEvent) {

        if (!txtMonthlyRate.getText().matches(Regex.valueRegEx())){
            condition=false;
            FontChanger.setTextColorRed(txtMonthlyRate);
        }else {
            FontChanger.setTextBlack(txtMonthlyRate);
            condition = true;
            double rate = Double.parseDouble(txtMonthlyRate.getText());
            double totalValue = Double.parseDouble(lblTransporterTotalLeafValue.getText());
            lblTransporterLastPayment.setText(String.valueOf(rate * totalValue));

        }
    }

    public void enterTransporterIdSearch(KeyEvent keyEvent) {

        if (!txtTransporterIdSearch.getText().matches(Regex.idRegEx())){
            condition=false;
            FontChanger.setSearchBarRed(txtTransporterIdSearch);
        }else {
            FontChanger.setSearchBarBlack(txtTransporterIdSearch);
            condition = true;
            ActionEvent actionEvent=new ActionEvent();
            searchedTransporterId(actionEvent);
        }
    }

    public void checkMonth(MouseEvent mouseEvent) {

        if(cmbMonthSelect.getValue()==null){
            new Alert(Alert.AlertType.ERROR, "Select Month First").show();
        }
        if(txtTransporterIdSearch.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Input Id First").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializedMonthBox();
    }
}
