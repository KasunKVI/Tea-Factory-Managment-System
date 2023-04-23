package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.model.CustomerModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerAddOrEditFormController {

    //Components from customer adding form
    @FXML
    private TextField txtCustId;
    @FXML
    private TextField txtCustName;
    @FXML
    private TextField txtCustContact;
    @FXML
    private TextField txtCustEmplId;
    @FXML
    private ComboBox<String> cmbCustOrigin;

    @FXML
    private TextField txtCustomerIdSearch;
    @FXML
    private TextField txtCustNameEdit;
    @FXML
    private TextField txtCustContactEdit;
    @FXML
    private TextField txtCustOriginEdit;
    @FXML
    private TextField txtCustEmployeeIdEdit;
    @FXML
    private Label lblCustIdEdit;

    @FXML
    private Button btnCustAdd;
    @FXML
    private Button btnCustEdit;

    private Stage stage = new Stage();

    private String id;
    private String name;
    private String origin;
    private String contact_no;
    private String employee_id;

    public void addCustomerToDatabase(ActionEvent actionEvent) {

        id = txtCustId.getText();
        name = txtCustName.getText();
        origin = cmbCustOrigin.getValue();
        contact_no = txtCustContact.getText();
        employee_id = txtCustEmplId.getText();

        Customer customer=new Customer(id, name, origin, contact_no, employee_id);

        try {

            CustomerModel.addCustomerToDatabase(customer);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

        stage = (Stage) btnCustAdd.getScene().getWindow();
        stage.close();


    }

    public void searchCustomerFromDatabase(ActionEvent actionEvent) {

        id = txtCustomerIdSearch.getText();

        try {
            Customer customer = CustomerModel.searchCustomerFromDatabase(id);

            if (customer==null){

                new Alert(Alert.AlertType.ERROR,"There is no customer in this id").show();

            }else {


                lblCustIdEdit.setText(String.valueOf(id));
                txtCustNameEdit.setText(customer.getName());
                txtCustContactEdit.setText(customer.getContact());
                txtCustOriginEdit.setText(customer.getOrigin());
                txtCustEmployeeIdEdit.setText(customer.getEmployee_id());

            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


    }

    public void addEditedCustomersToDatabase(ActionEvent actionEvent) {

        id=lblCustIdEdit.getText();
        name=txtCustNameEdit.getText();
        contact_no=txtCustContactEdit.getText();
        origin=txtCustOriginEdit.getAccessibleHelp();
        employee_id=txtCustEmployeeIdEdit.getText();

        Customer customer = new Customer(id,name,origin,contact_no,employee_id);

        try {
            CustomerModel.addEditedCustomersToDatabase(customer);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        stage = (Stage) btnCustEdit.getScene().getWindow();
        stage.close();
    }

    public void discardCustomerAddingDetails(ActionEvent actionEvent) {

        txtCustId.clear();
        txtCustName.clear();
        txtCustContact.clear();
        txtCustEmplId.clear();
        cmbCustOrigin.setItems(null);

    }

    public void initializeComboBox(Event event) {

        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> type = new ArrayList<>();

        type.add("Local");
        type.add("Foreign");

        obList.addAll(type);

        cmbCustOrigin.setItems(obList);
        
    }

    public void enteredCustOrigin(ActionEvent actionEvent) {
        txtCustEmplId.requestFocus();
    }

    public void enteredCustId(ActionEvent actionEvent) {
        txtCustName.requestFocus();
    }


    public void enteredCustName(ActionEvent actionEvent) {
        txtCustContact.requestFocus();
    }
    

    public void enteredCustContactEdit(ActionEvent actionEvent) {
        txtCustEmployeeIdEdit.requestFocus();
    }

    public void enteredCustNameEdit(ActionEvent actionEvent) {
        txtCustContactEdit.requestFocus();
    }

    public void enteredCustEmployeeEdit(ActionEvent actionEvent) {
        addEditedCustomersToDatabase(actionEvent);
    }

    public void enteredEmployeeId(ActionEvent actionEvent) {
        addCustomerToDatabase(actionEvent);
    }

    public void discardCustomerEditForm(ActionEvent actionEvent) {

        txtCustomerIdSearch.clear();
        lblCustIdEdit.setText("");
        txtCustNameEdit.clear();
        txtCustContactEdit.clear();
        txtCustEmployeeIdEdit.clear();
        txtCustOriginEdit.clear();

    }

    public void enterCustomerId(KeyEvent keyEvent) {
        if (!txtCustId.getText().matches(Regex.customerIdRegEx())) {
            Regex.setTextColorRed(txtCustId);
        }else Regex.setTextBlack(txtCustId);
    }

    public void enterCustomerContact(KeyEvent keyEvent) {
        if (!txtCustContact.getText().matches(Regex.contactRegEx())) {
            Regex.setTextColorRed(txtCustContact);
        }else Regex.setTextBlack(txtCustContact);
    }

    public void enterCustomerName(KeyEvent keyEvent) {
        if (!txtCustName.getText().matches(Regex.nameRegEx())) {
            Regex.setTextColorRed(txtCustName);
        }else Regex.setTextBlack(txtCustName);
    }

    public void enterEmployeeId(KeyEvent keyEvent) {
        if (!txtCustEmplId.getText().matches(Regex.employeeIdRegEx())) {
            Regex.setTextColorRed(txtCustEmplId);
        }else Regex.setTextBlack(txtCustEmplId);
    }

    public void enteredCudtOriginEdit(ActionEvent actionEvent) {
    }

    public void enterCustomerContactED(KeyEvent keyEvent) {
        if (!txtCustContactEdit.getText().matches(Regex.contactRegEx())) {
            Regex.setTextColorRed(txtCustContactEdit);
        }else Regex.setTextBlack(txtCustContactEdit);
    }

    public void enterCustomerNameED(KeyEvent keyEvent) {
        if (!txtCustNameEdit.getText().matches(Regex.nameRegEx())) {
           Regex.setTextColorRed(txtCustNameEdit);
        }else Regex.setTextBlack(txtCustNameEdit);
    }

    public void enterEmployeeIdED(KeyEvent keyEvent) {
        if (!txtCustEmployeeIdEdit.getText().matches(Regex.employeeIdRegEx())) {
            Regex.setTextColorRed(txtCustEmployeeIdEdit);
        }else Regex.setTextBlack(txtCustEmployeeIdEdit);
    }
}
