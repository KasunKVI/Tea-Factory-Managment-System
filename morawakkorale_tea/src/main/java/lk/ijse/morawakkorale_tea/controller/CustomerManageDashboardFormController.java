package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.dto.tm.CustomerTM;
import lk.ijse.morawakkorale_tea.dto.tm.SupplierTM;
import lk.ijse.morawakkorale_tea.dto.tm.TransporterTM;
import lk.ijse.morawakkorale_tea.model.CustomerModel;
import lk.ijse.morawakkorale_tea.model.TransporterModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerManageDashboardFormController implements Initializable {

    //Components from customer manage form
    @FXML
    private TableView<CustomerTM> allCustomersDetails;
    @FXML
    private TableColumn<?,?> clmCustId;
    @FXML
    private TableColumn<?,?> clmCustName;
    @FXML
    private TableColumn<?,?> clmCustContact;
    @FXML
    private TableColumn<?,?> clmCustOrigin;



    Stage stage = new Stage();

    //show add customer form
    public void addNewCustomer(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/customer_adding_form.fxml"));
        stage.setTitle("Customer Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }

    //show edit customer form
    public void editCustomer(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/customer_edit_form.fxml"));
        stage.setTitle("Customer Edit");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCellValueFactory();
        getAll();

    }

    private void setCellValueFactory() {

        clmCustId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        clmCustName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        clmCustContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        clmCustOrigin.setCellValueFactory(new PropertyValueFactory<>("Origin"));

    }

    //add all customers data to table view
    void getAll() {
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<Customer> custList = CustomerModel.getAll();

            for(Customer customer : custList) {
                obList.add(new CustomerTM(
                        customer.getId(),
                        customer.getName(),
                        customer.getContact(),
                        customer.getOrigin()
                ));
            }
            allCustomersDetails.setItems(obList);

        } catch (SQLException e) {

            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    //run getAll method and refresh table
    public void refreshTable(MouseEvent mouseEvent) {

        getAll();
        allCustomersDetails.refresh();

    }

    //delete customer data from database and tableView
    public void deleteCustomerFromDatabase(ActionEvent actionEvent) {


        int selectedID=allCustomersDetails.getSelectionModel().getSelectedIndex();

        CustomerTM customer = allCustomersDetails.getSelectionModel().getSelectedItem();

        String customerId = customer.getId();

        allCustomersDetails.getItems().remove(selectedID);

        try {

            CustomerModel.deleteCustomerFromDatabase(customerId);

        } catch (SQLException throwable) {

            throwable.printStackTrace();
        }
    }
}
