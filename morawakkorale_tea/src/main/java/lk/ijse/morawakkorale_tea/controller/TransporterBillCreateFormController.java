package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.model.SupplierModel;
import lk.ijse.morawakkorale_tea.model.TransporterModel;

import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TransporterBillCreateFormController {

    @FXML
    private ComboBox<Month> cmbMonthSelect;

    @FXML
    private Label lblMonth;

    @FXML
    private Label lblTransporterBagCount;

    @FXML
    private Label lblTransporterId;

    @FXML
    private Label lblTransporterLastLeafValue;

    @FXML
    private Label lblTransporterLastPayment;

    @FXML
    private Label lblTransporterName;

    @FXML
    private Label lblTransporterTotalLeafValue;

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

    public void showReport(ActionEvent actionEvent) {
    }

    public void countTransporterLastPayment(ActionEvent actionEvent) {
    }

    public void correctTransporterMonthlyPayment(ActionEvent actionEvent) {
    }

    public void selectedMonthOfTransporter(ActionEvent actionEvent) {

        Transporter transporter = searchedTransporterId(actionEvent);
    }

    public void initializedMonthBox(Event event) {

        SupplierBillCreateFormController.comboBoxAdd(cmbMonthSelect);

    }
}
