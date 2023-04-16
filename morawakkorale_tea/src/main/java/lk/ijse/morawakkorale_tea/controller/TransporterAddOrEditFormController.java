package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.model.TransporterModel;

import java.sql.SQLException;

public class TransporterAddOrEditFormController {

    //Components from supplier adding form
    @FXML
    private TextField txtTrpId;
    @FXML
    private TextField txtTrpName;
    @FXML
    private TextField txtTrpContact;
    @FXML
    private TextField txtTrpRoute;
    @FXML
    private TextField txtTrpAddress;

    @FXML
    private TextField txtTransporterIdSearch;
    @FXML
    private Label lblTransporterIdEdit;
    @FXML
    private TextField txtTransporterNameEdit;
    @FXML
    private TextField txtTransporterContactEdit;
    @FXML
    private TextField txtTransporterAddressEdit;
    @FXML
    private TextField txtTransporterRouteEdit;

    @FXML
    private Button btnTrpAdd;
    @FXML
    private Button btnTransporterAdd;

    private int id;
    private String name;
    private String contact_no;
    private String address;
    private String route;

    private Stage stage = new Stage();

    public void addTransporterToDatabase(ActionEvent actionEvent) {

        id= Integer.parseInt(txtTrpId.getText());
        name=txtTrpName.getText();
        contact_no=txtTrpContact.getText();
        route=txtTrpRoute.getText();
        address=txtTrpAddress.getText();

        Transporter transporter = new Transporter(id,name,contact_no,route,address);

        try {

            TransporterModel.addTransporterToDatabase(transporter);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

        stage = (Stage) btnTrpAdd.getScene().getWindow();
        stage.close();

    }

    public void addEditedTransportersToDatabase(ActionEvent actionEvent) {

        id= Integer.parseInt(lblTransporterIdEdit.getText());
        name=txtTransporterNameEdit.getText();
        contact_no=txtTransporterContactEdit.getText();
        address=txtTransporterAddressEdit.getText();
        route=txtTransporterRouteEdit.getText();

        Transporter transporter = new Transporter(id,name,contact_no,route,address);

        try {
            TransporterModel.addEditedTransportersToDatabase(transporter);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        stage = (Stage) btnTransporterAdd.getScene().getWindow();
        stage.close();
    }

    public void searchTransporterFromDatabase(ActionEvent actionEvent) {

        id = Integer.parseInt(txtTransporterIdSearch.getText());

        try {
            Transporter transporter = TransporterModel.searchTransporterFromDatabase(String.valueOf(id));

            if (transporter==null){

                new Alert(Alert.AlertType.ERROR,"There is no transporter in this id").show();

            }else {


                lblTransporterIdEdit.setText(String.valueOf(id));
                txtTransporterNameEdit.setText(transporter.getName());
                txtTransporterContactEdit.setText(transporter.getContact());
                txtTransporterAddressEdit.setText(transporter.getAddress());
                txtTransporterRouteEdit.setText(transporter.getRoute());

            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void discardAddTransporterForm(ActionEvent actionEvent) {

        txtTrpId.clear();
        txtTrpName.clear();
        txtTrpAddress.clear();
        txtTrpContact.clear();
        txtTrpRoute.clear();

    }

    public void discardTransporterEdit(ActionEvent actionEvent) {

        txtTransporterIdSearch.clear();
        txtTransporterNameEdit.clear();
        lblTransporterIdEdit.setText("");
        txtTransporterAddressEdit.clear();
        txtTransporterContactEdit.clear();
        txtTransporterRouteEdit.clear();

    }

    public void enteredTransporterEditedAddress(ActionEvent actionEvent) {
        txtTransporterRouteEdit.requestFocus();
    }

    public void enteredTransporterEditedContact(ActionEvent actionEvent) {
        txtTransporterAddressEdit.requestFocus();
    }

    public void enteredTransporterEditedName(ActionEvent actionEvent) {
        txtTransporterContactEdit.requestFocus();
    }

    public void enteredTransporterEditedRoute(ActionEvent actionEvent) {
        addEditedTransportersToDatabase(actionEvent);
    }

    public void enteredTransporterId(ActionEvent actionEvent) {
        txtTrpName.requestFocus();
    }

    public void enteredTransporterAddress(ActionEvent actionEvent) {
        addTransporterToDatabase(actionEvent);
    }

    public void enteredTransporterContact(ActionEvent actionEvent) {
        txtTrpRoute.requestFocus();
    }

    public void enteredTransporterName(ActionEvent actionEvent) {
        txtTrpContact.requestFocus();
    }

    public void enteredTransporterRoute(ActionEvent actionEvent) {
        txtTrpAddress.requestFocus();
    }
}
