package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.model.CustomerModel;
import lk.ijse.morawakkorale_tea.model.ProductModel;
import lk.ijse.morawakkorale_tea.model.SupplierModel;
import lk.ijse.morawakkorale_tea.model.TransporterModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ManagerDashboardFormController  {

    @FXML
    public Pane menuBarPanel;
    public AnchorPane backgroundPane;

    Stage stage=new Stage();
    Stage pm=new Stage();

    //Components from customer adding form
    @FXML
    public TextField txtCustId;
    public TextField txtCustName;
    public TextField txtCustContact;
    public TextField txtCustEmplId;
    public ComboBox cmbCustOrigin;


    @FXML
    public Button btnAddCustomer;
    public Button btnCustAdd;

    public Button btnTrpAdd;
    public Button btnPdtAdd;


    //Components from supplier adding form
    @FXML
    private Button btnSupAdd;
    public TextField txtSupId;
    public TextField txtSupName;
    public TextField txtSupContact;
    public DatePicker dtpSupRegDate;
    public TextField txtSupAddress;


    //Components from supplier adding form
    @FXML
    public TextField txtTrpId;
    public TextField txtTrpName;
    public TextField txtTrpContact;
    public TextField txtTrpRoute;
    public TextField txtTrpAddress;


    //Components from product adding form
    @FXML
    public TextField txtPdtId;
    public TextField txtPdtName;
    public ComboBox cmbPdtStockId;
    public DatePicker dtpPdtMadeDate;
    public TextField txtPdtProductQuantity;
    public ComboBox cmbPdtProductType;

    String id;
    String name;
    String contact_no;
    LocalDate reg_date;
    String address;
    String route;
    String stockId;
    LocalDate madeDate;
    int qtyOnHand;
    String productType;
    String employee_id;
    String origin;

    public void hideMenuBar(MouseEvent mouseEvent) {

        MenuBarOperation.fadeMenuBar(menuBarPanel,1,0,-88);

        menuBarPanel.setDisable(true);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

        menuBarPanel.setDisable(false );
        MenuBarOperation.fadeMenuBar(menuBarPanel,0,1,+88);
        menuBarPanel.setVisible(true);

    }


    public void showSupplierManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/suppliers_manage_form.fxml")));

    }

    public void logout(ActionEvent actionEvent) {


    }

    public void showBuyersManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customers_manage_form.fxml")));

    }

    public void showTransportersManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/transporters_manage_form.fxml")));

    }

    public void showProductManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/product_manage_form.fxml")));

    }

    public void addNewCustomer(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/customer_adding_form.fxml"));
        stage.setTitle("Customer Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void exitFromSystem(MouseEvent mouseEvent) {
    }

    public void addCustomerToDatabase(ActionEvent actionEvent) {

        id=txtCustId.getText();
        name=txtCustName.getText();
        origin= (String) cmbCustOrigin.getValue();
        contact_no=txtCustContact.getText();
        employee_id=txtCustEmplId.getText();

        Customer customer=new Customer(id,name,origin,contact_no,employee_id);

        try {
            CustomerModel.addCustomerToDatabase(customer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        stage = (Stage) btnCustAdd.getScene().getWindow();
        stage.close();


    }

    public void addNewSupplier(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/supplier_adding_form.fxml"));
        stage.setTitle("Supplier Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void addSupplierToDatabase(ActionEvent actionEvent) {

        id=txtSupId.getText();
        name=txtSupName.getText();
        contact_no=txtSupContact.getText();
        reg_date=dtpSupRegDate.getValue();
        address=txtSupAddress.getText();

        Supplier supplier = new Supplier(id,name,contact_no,reg_date,address);

        try {

            SupplierModel.addSupplierToDatabase(supplier);

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        stage = (Stage) btnSupAdd.getScene().getWindow();
        stage.close();

    }

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

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        stage = (Stage) btnTrpAdd.getScene().getWindow();
        stage.close();

    }

    public void addNewProduct(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/product_adding_form.fxml"));
        stage.setTitle("Product Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void addProductToDatabase(ActionEvent actionEvent) {

        id=txtPdtId.getText();
        name=txtPdtName.getText();
        stockId= (String) cmbPdtStockId.getValue();
        madeDate=dtpPdtMadeDate.getValue();
        qtyOnHand= Integer.parseInt(txtPdtProductQuantity.getText());
        productType= (String) cmbPdtProductType.getValue();

        Product product = new Product(id,name,madeDate,qtyOnHand,productType,stockId);

        try {

            ProductModel.addProductToDatabase(product);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        stage = (Stage) btnPdtAdd.getScene().getWindow();
        stage.close();

    }
}
