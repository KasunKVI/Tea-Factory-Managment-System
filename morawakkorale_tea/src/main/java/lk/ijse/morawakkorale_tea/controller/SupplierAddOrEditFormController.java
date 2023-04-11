package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierAddOrEditFormController implements Initializable {

    @FXML
    private Button btnSupAdd;
    @FXML
    private TextField txtSupId;
    @FXML
    private TextField txtSupName;
    @FXML
    private TextField txtSupContact;
    @FXML
    private DatePicker dtpSupRegDate;
    @FXML
    private TextField txtSupAddress;

    @FXML
    private TextField txtSupplierIdSearch;
    @FXML
    private Label lblSupIdEdit;
    @FXML
    private TextField txtSupNameEdit;
    @FXML
    private TextField txtSupContactEdit;
    @FXML
    private TextField txtSupAddressEdit;

    @FXML
    private TextField txtSupRegDateEdit ;
    @FXML
    private TextField txtSupStatusEdit;

    private int id;
    private String name;
    private String contact_no;
    private String address;

    private Stage stage = new Stage();



    public void addSupplierToDatabase(ActionEvent actionEvent) {

        id = Integer.parseInt(txtSupId.getText());
        name=txtSupName.getText();
        contact_no=txtSupContact.getText();
        Date reg_date = Date.valueOf(dtpSupRegDate.getValue());
        address=txtSupAddress.getText();

        Supplier supplier = new Supplier(id,name,contact_no, reg_date,address,null);

        try {

            SupplierModel.addSupplierToDatabase(supplier);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

        stage = (Stage) btnSupAdd.getScene().getWindow();
        stage.close();

    }
    public void addEditedSupplierToDatabase(ActionEvent actionEvent) {

        id= Integer.parseInt(lblSupIdEdit.getText());
        name=txtSupNameEdit.getText();
        contact_no=txtSupContactEdit.getText();
        address=txtSupAddressEdit.getText();

        Supplier supplier=new Supplier(id,name,contact_no,null,address,null);

        try {
            SupplierModel.addEditedSupplierToDatabase(supplier);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void searchSupplierFromDatabase(ActionEvent actionEvent) throws IOException {

        id = Integer.parseInt(txtSupplierIdSearch.getText());

        try {

            Supplier supplier = SupplierModel.searchSupplierFromDatabase(String.valueOf(id));

            if (supplier==null){

                new Alert(Alert.AlertType.ERROR,"There is no supplier in this id").show();

            }else {

                lblSupIdEdit.setText(String.valueOf(id));
                txtSupNameEdit.setText(supplier.getName());
                txtSupContactEdit.setText(supplier.getContact());
                txtSupAddressEdit.setText(supplier.getAddress());
                txtSupRegDateEdit.setText(String.valueOf(supplier.getReg_date()));
                txtSupStatusEdit.setText(supplier.getStatus());

            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();

        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    public void enteredSupplierId(ActionEvent actionEvent) {
        txtSupName.requestFocus();
    }

    public void enteredSupplierAllDetails(ActionEvent actionEvent) {
        addSupplierToDatabase(actionEvent);
    }

    public void enteredSupplierContact(ActionEvent actionEvent) {
        dtpSupRegDate.requestFocus();
    }

    public void enteredSupplierName(ActionEvent actionEvent) {
        txtSupContact.requestFocus();
    }

    public void enteredSupplierRegDate(ActionEvent actionEvent) {
        txtSupAddress.requestFocus();
    }
}
