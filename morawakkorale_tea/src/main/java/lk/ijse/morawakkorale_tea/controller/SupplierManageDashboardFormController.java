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
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;
import lk.ijse.morawakkorale_tea.dto.tm.SupplierTM;
import lk.ijse.morawakkorale_tea.dto.tm.SupplierTeaValuesTM;
import lk.ijse.morawakkorale_tea.model.SupplierModel;
import lk.ijse.morawakkorale_tea.model.Supplier_StockModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierManageDashboardFormController implements Initializable {


    //components from supplier manage from
    @FXML
    public TableView<SupplierTM> allSupplierDetails;

    @FXML
    private TableColumn<?,?> clmSupId;
    @FXML
    private TableColumn<?,?> clmSupName;
    @FXML
    private TableColumn<?,?> clmSupContact;
    @FXML
    private TableColumn<?,?> clmSupRegDate;
    @FXML
    private TableColumn<?,?> clmSupAddress;
    @FXML
    private TableColumn<?,?> clmSupStatus;



    private Stage stage = new Stage();

    //show add new supplier from
    public void addNewSupplier(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/supplier_adding_form.fxml"));
        stage.setTitle("Supplier Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void editSupplier(ActionEvent actionEvent) throws IOException {

        loadEditForm("supplier");

    }

    //load edit supplier form
    public void loadEditForm(String form) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/"+form+"_edit_form.fxml"));
        stage.setTitle(form+"  Edit");
        stage.setScene(new Scene(root));
        stage.show();

    }


    private void setCellValueFactory() {

        clmSupId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        clmSupName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        clmSupContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        clmSupRegDate.setCellValueFactory(new PropertyValueFactory<>("Reg_date"));
        clmSupAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        clmSupStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCellValueFactory();
        getAll();
    }

    //get all suppliers details from database and set it to tableView
    public void getAll() {
        try {
            ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
            List<Supplier> supList = SupplierModel.getAll();

            for(Supplier supplier : supList) {
                obList.add(new SupplierTM(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getContact(),
                        supplier.getReg_date(),
                        supplier.getAddress(),
                        supplier.getStatus()
                ));
            }
            allSupplierDetails.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    //refresh tableView
    public void refreshTable(MouseEvent mouseEvent) {
        getAll();
        allSupplierDetails.refresh();
    }

    //delete supplier from database
    public void deleteSupplierFromDatabase(ActionEvent actionEvent) {

        int selectedID=allSupplierDetails.getSelectionModel().getSelectedIndex();

        SupplierTM supplier = allSupplierDetails.getSelectionModel().getSelectedItem();

        int supplierId = supplier.getId();

        allSupplierDetails.getItems().remove(selectedID);

        try {

            Supplier_StockModel.deleteSupplierFromDatabase(supplierId);
            SupplierModel.deleteSupplierFromDatabase(supplierId);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }
    }
}
