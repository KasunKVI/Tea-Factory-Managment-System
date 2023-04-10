package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.model.TransporterModel;

import java.io.IOException;
import java.sql.SQLException;

public class TransporterManageFormController {

    private Stage stage = new Stage();

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

    private String id;
    private String name;
    private String contact_no;
    private String address;
    private String route;


    public void addNewTransporter(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/transporter_adding_form.fxml"));
        stage.setTitle("Transporter Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void addTransporterToDatabase(ActionEvent actionEvent) {

        id=txtTrpId.getText();
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

        id=lblTransporterIdEdit.getText();
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
    }

    public void searchTransporterFromDatabase(ActionEvent actionEvent) {

        id = txtTransporterIdSearch.getText();

        try {
            Transporter transporter = TransporterModel.searchTransporterFromDatabase(id);

            if (transporter==null){

                new Alert(Alert.AlertType.ERROR,"There is no transporter in this id").show();

            }else {


                lblTransporterIdEdit.setText(id);
                txtTransporterNameEdit.setText(transporter.getName());
                txtTransporterContactEdit.setText(transporter.getContact());
                txtTransporterAddressEdit.setText(transporter.getAddress());
                txtTransporterRouteEdit.setText(transporter.getRoute());

            }


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void editTransporter(ActionEvent actionEvent) throws IOException {

        loadEditForm("transporter");

    }

    public void loadEditForm(String form) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/"+form+"_edit_form.fxml"));
        stage.setTitle(form+"  Edit");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
