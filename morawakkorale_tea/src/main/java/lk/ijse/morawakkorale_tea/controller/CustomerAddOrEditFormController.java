package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.model.CustomerModel;
import lk.ijse.morawakkorale_tea.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerAddOrEditFormController implements Initializable {

    //Components from customer adding form
    @FXML
    private TextField txtCustId;
    @FXML
    private TextField txtCustName;
    @FXML
    private TextField txtCustContact;
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
    private Label lblCustIdEdit;

    @FXML
    private ComboBox<String> cmbEmpIds;

    @FXML
    private Button btnCustAdd;
    @FXML
    private Button btnCustEdit;

    private Stage stage = new Stage();

    private boolean condition = true;

    private String id;
    private String name;
    private String origin;
    private String contact_no;
    private String employee_id;

    //add new customer to database
    public void addCustomerToDatabase(ActionEvent actionEvent) {

        if(!condition||txtCustId.getText().isEmpty()||txtCustName.getText().isEmpty()||txtCustContact.getText().isEmpty()||cmbCustOrigin.getValue()==null||cmbEmpIds.getValue()==null){

            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();

        }else {

            id = txtCustId.getText();
            name = txtCustName.getText();
            origin = cmbCustOrigin.getValue();
            contact_no = txtCustContact.getText();
            employee_id = cmbEmpIds.getValue();

            Customer customer = new Customer(id, name, origin, contact_no, employee_id);

            try {

                CustomerModel.addCustomerToDatabase(customer);
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Customer added successful. Do yo want to add another customer", yes, no).showAndWait();

                if (result.orElse(no) == yes) {

                    discardCustomerAddingDetails(actionEvent);

                }else {

                    stage = (Stage) btnCustAdd.getScene().getWindow();
                    stage.close();
                }

            } catch (SQLException throwable) {

                throwable.printStackTrace();

            }

        }


    }

    //search customer from database
    public void searchCustomerFromDatabase(ActionEvent actionEvent) {

        if(!condition){
            new Alert(Alert.AlertType.ERROR, "Input Valid Id").show();
        }else {

            id = txtCustomerIdSearch.getText();

            try {
                Customer customer = CustomerModel.searchCustomerFromDatabase(id);

                if (customer == null) {

                    new Alert(Alert.AlertType.ERROR, "There is no customer in this id").show();
                    txtCustomerIdSearch.requestFocus();

                } else {

                    lblCustIdEdit.setText(String.valueOf(id));
                    txtCustNameEdit.setText(customer.getName());
                    txtCustContactEdit.setText(customer.getContact());
                    txtCustOriginEdit.setText(customer.getOrigin());
                    cmbEmpIds.setValue(customer.getEmployee_id());

                    txtCustNameEdit.requestFocus();

                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }


    }

    //add edited customer values to database
    public void addEditedCustomersToDatabase(ActionEvent actionEvent) {

        if (!condition||txtCustNameEdit.getText().isEmpty()||txtCustContactEdit.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();
        } else {

            id = lblCustIdEdit.getText();
            name = txtCustNameEdit.getText();
            contact_no = txtCustContactEdit.getText();
            origin = txtCustOriginEdit.getAccessibleHelp();
            employee_id = cmbEmpIds.getValue();

            Customer customer = new Customer(id, name, origin, contact_no, employee_id);

            try {
                CustomerModel.addEditedCustomersToDatabase(customer);
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Customer edited successful. Do yo want to edit another customer", yes, no).showAndWait();

                if (result.orElse(no) == yes) {

                    discardCustomerEditForm(actionEvent);

                }else {

                    stage = (Stage) btnCustEdit.getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    //clear customer adding form
    public void discardCustomerAddingDetails(ActionEvent actionEvent){

            txtCustId.clear();
            txtCustName.clear();
            txtCustContact.clear();
            cmbEmpIds.setItems(null);
            cmbCustOrigin.setItems(null);


    }

    //initialize comboBox while it showing
    public void initializeComboBox(Event event) {

        if(!condition){

            new Alert(Alert.AlertType.ERROR, "Input Valid Contact").show();
            txtCustContact.requestFocus();

        }

        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> type = new ArrayList<>();

        type.add("Local");
        type.add("Foreign");

        obList.addAll(type);

        cmbCustOrigin.setItems(obList);
        
    }

    //check customer id
    public void enteredCustId(ActionEvent actionEvent) {

        if(!condition){
            new Alert(Alert.AlertType.ERROR, "Input Valid Customer Id").show();
            txtCustId.requestFocus();
        }else {
            txtCustName.requestFocus();
        }
    }

    //check customer name
    public void enteredCustName(ActionEvent actionEvent) {

        if(!condition){
            new Alert(Alert.AlertType.ERROR, "Input Valid Name").show();
            txtCustName.requestFocus();
        }else {
            txtCustContact.requestFocus();
        }
    }
    
    //check edited customer contact
    public void enteredCustContactEdit(ActionEvent actionEvent) {

        if(!condition){
            new Alert(Alert.AlertType.ERROR, "Input Valid Contact").show();
            txtCustContactEdit.requestFocus();
        }
    }

    //check edited customer name
    public void enteredCustNameEdit(ActionEvent actionEvent) {

        if(!condition){
            new Alert(Alert.AlertType.ERROR, "Input Valid Name").show();
            txtCustNameEdit.requestFocus();
        }else {
            txtCustContactEdit.requestFocus();
        }
    }

    //clear customer edit window
    public void discardCustomerEditForm(ActionEvent actionEvent) {

        txtCustomerIdSearch.clear();
        lblCustIdEdit.setText("");
        txtCustNameEdit.clear();
        txtCustContactEdit.clear();
        cmbEmpIds.setValue(null);
        txtCustOriginEdit.clear();

    }

    //validate customer id
    public void enterCustomerId(KeyEvent keyEvent) throws SQLException {

        if (!txtCustId.getText().matches(Regex.customerIdRegEx())) {
                FontChanger.setTextColorRed(txtCustId);
                condition = false;

        }else if(CustomerModel.isExist(txtCustId.getText())) {

            FontChanger.setTextColorRed(txtCustId);
            condition = false;

        }else {
            FontChanger.setTextBlack(txtCustId);
            condition = true;
        }
    }

    //validate customer contact
    public void enterCustomerContact(KeyEvent keyEvent) {
        if (!txtCustContact.getText().matches(Regex.contactRegEx())) {
            FontChanger.setTextColorRed(txtCustContact);
            condition=false;
        }else {
            FontChanger.setTextBlack(txtCustContact);
            condition = true;
        }
    }

    //validate customer name
    public void enterCustomerName(KeyEvent keyEvent) {
        if (!txtCustName.getText().matches(Regex.nameRegEx())) {
            FontChanger.setTextColorRed(txtCustName);
            condition=false;
        }else {
            FontChanger.setTextBlack(txtCustName);
            condition = true;
        }
    }


    public void enterCustomerContactED(KeyEvent keyEvent) {
        if (!txtCustContactEdit.getText().matches(Regex.contactRegEx())) {
            FontChanger.setTextColorRed(txtCustContactEdit);
            condition=false;
        }else {
            FontChanger.setTextBlack(txtCustContactEdit);
            condition = true;
        }
    }

    //validate edited customer edited name
    public void enterCustomerNameED(KeyEvent keyEvent) {
        if (!txtCustNameEdit.getText().matches(Regex.nameRegEx())) {
           FontChanger.setTextColorRed(txtCustNameEdit);
            condition=false;
        }else {
            FontChanger.setTextBlack(txtCustNameEdit);
            condition = true;
        }
    }

    //check customer id from search bar
    public void enterCustomerIdSearch(KeyEvent keyEvent) {

        if (!txtCustomerIdSearch.getText().matches(Regex.customerIdRegEx())) {
            FontChanger.setSearchBarRed(txtCustomerIdSearch);
            condition=false;
        }else {
            FontChanger.setSearchBarBlack(txtCustomerIdSearch);
            condition = true;
        }
    }


    //initialize comboBx while form load form
    public void initializeComboBoxEmp() {
        ObservableList <String> observableList = FXCollections.observableArrayList();
        try {
            List<String> employeeIds = EmployeeModel.getIds();
            observableList.addAll(employeeIds);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        cmbEmpIds.setItems(observableList);



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeComboBoxEmp();
    }
}
