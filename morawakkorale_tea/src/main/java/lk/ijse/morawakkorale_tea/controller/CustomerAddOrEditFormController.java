package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.model.CustomerModel;

import java.sql.SQLException;

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
    private ComboBox cmbCustOrigin;

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
        origin = (String) cmbCustOrigin.getValue();
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
}
