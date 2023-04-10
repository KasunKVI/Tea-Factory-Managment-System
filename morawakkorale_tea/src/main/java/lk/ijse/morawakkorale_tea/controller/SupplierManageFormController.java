package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.model.SupplierModel;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class SupplierManageFormController {

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

    private String id;
    private String name;
    private String contact_no;
    private String address;



    private Stage stage = new Stage();

    public void addNewSupplier(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/supplier_adding_form.fxml"));
        stage.setTitle("Supplier Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void editSupplier(ActionEvent actionEvent) throws IOException {

        loadEditForm("supplier");

    }
    public void loadEditForm(String form) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/"+form+"_edit_form.fxml"));
        stage.setTitle(form+"  Edit");
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void addSupplierToDatabase(ActionEvent actionEvent) {

        id = txtSupId.getText();
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



        id=lblSupIdEdit.getText();
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

        id = txtSupplierIdSearch.getText();

        try {

            Supplier supplier = SupplierModel.searchSupplierFromDatabase(id);

            if (supplier==null){

                new Alert(Alert.AlertType.ERROR,"There is no supplier in this id").show();
            }else {


                lblSupIdEdit.setText(id);
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


}
