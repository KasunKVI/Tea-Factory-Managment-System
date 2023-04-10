package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerManageFormController {


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
    private Button btnCustAdd;


    Stage stage = new Stage();


    public void addNewCustomer(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/customer_adding_form.fxml"));
        stage.setTitle("Customer Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void addCustomerToDatabase(ActionEvent actionEvent) {

        String id = txtCustId.getText();
        String name = txtCustName.getText();
        String origin = (String) cmbCustOrigin.getValue();
        String contact_no = txtCustContact.getText();
        String employee_id = txtCustEmplId.getText();

        Customer customer=new Customer(id, name, origin, contact_no, employee_id);

        try {
            CustomerModel.addCustomerToDatabase(customer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        stage = (Stage) btnCustAdd.getScene().getWindow();
        stage.close();


    }

}
