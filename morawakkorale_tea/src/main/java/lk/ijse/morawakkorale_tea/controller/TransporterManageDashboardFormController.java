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
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.dto.tm.SupplierTM;
import lk.ijse.morawakkorale_tea.dto.tm.TransporterTM;
import lk.ijse.morawakkorale_tea.model.PaymentModel;
import lk.ijse.morawakkorale_tea.model.StockModel;
import lk.ijse.morawakkorale_tea.model.SupplierModel;
import lk.ijse.morawakkorale_tea.model.TransporterModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TransporterManageDashboardFormController implements Initializable {

    private Stage stage = new Stage();



    @FXML
    private TableView<TransporterTM> allTransportersDetails;

    @FXML
    private TableColumn<?,?> clmTrpId;
    @FXML
    private TableColumn<?,?> clmTrpName;
    @FXML
    private TableColumn<?,?> clmTrpContact;
    @FXML
    private TableColumn<?,?> clmTrpRoute;
    @FXML
    private TableColumn<?,?> clmTrpAddress;




    public void addNewTransporter(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/transporter_adding_form.fxml"));
        stage.setTitle("Transporter Adder");
        stage.setScene(new Scene(root));
        stage.show();
        Scene scene = root.getScene();
        scene.getStylesheets().add("/cssStyle/textRedStyle.css");

    }


    public void loadEditForm(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/transporter_edit_form.fxml"));
        stage.setTitle("Transporter  Edit");
        stage.setScene(new Scene(root));
        stage.show();
        Scene scene = root.getScene();
        scene.getStylesheets().add("/cssStyle/textRedStyle.css");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCellValueFactory();
        getAll();

    }
    private void setCellValueFactory() {

        clmTrpId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        clmTrpName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        clmTrpContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        clmTrpRoute.setCellValueFactory(new PropertyValueFactory<>("Route"));
        clmTrpAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));


    }
    void getAll() {
        try {
            ObservableList<TransporterTM> obList = FXCollections.observableArrayList();
            List<Transporter> trpList = TransporterModel.getAll();

            for(Transporter transporter : trpList) {
                obList.add(new TransporterTM(
                        transporter.getId(),
                        transporter.getName(),
                        transporter.getContact(),
                        transporter.getRoute(),
                        transporter.getAddress()
                ));
            }
            allTransportersDetails.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void refreshTable(MouseEvent mouseEvent) {

        getAll();
        allTransportersDetails.refresh();
    }

    public void deleteTransporterFromDatabase(ActionEvent actionEvent) {

        //Get selected column index
        int selectedID=allTransportersDetails.getSelectionModel().getSelectedIndex();

        //Get selected values in table
        TransporterTM transporterTM = allTransportersDetails.getSelectionModel().getSelectedItem();

        //Assign selected raw's supplier_id
        int transporterId = transporterTM.getId();

        //Remove selected raw from the table
        allTransportersDetails.getItems().remove(selectedID);

        try {

            String pay_id = TransporterModel.getTransporterPaymentId(transporterId);
            StockModel.deleteTransporterDetails(transporterId);
            TransporterModel.deleteTransporterFromDatabase(transporterId);

            if(pay_id!=null){

                PaymentModel.deleteTransporterPaymentFromDatabase(pay_id);

            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }
}
