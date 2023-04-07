package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.*;
import lk.ijse.morawakkorale_tea.model.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManagerDashboardFormController implements Initializable {



    public AnchorPane backgroundPane;
    @FXML
    public Pane menuBarPanel;


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
    @FXML
    private Button btnTrpAdd;
    @FXML
    private Button btnPdtAdd;

    //Components from supplier adding form
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

    //Components from product adding form
    @FXML
    private TextField txtPdtId;
    @FXML
    private TextField txtPdtName;
    @FXML
    private ComboBox cmbPdtStockId;
    @FXML
    private DatePicker dtpPdtMadeDate;
    @FXML
    private TextField txtPdtProductQuantity;
    @FXML
    private ComboBox cmbPdtProductType;

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
    private TextField txtProductIdSearch;
    @FXML
    private Label lblProductIdEdit;
    @FXML
    private TextField txtProductNameEdit;
    @FXML
    private TextField   txtProductStockIdEdit;
    @FXML
    private TextField txtProductQuantityEdit;
    @FXML
    private TextField txtProductGLValueEdit;
    @FXML
    private TextField txtProductTypeEdit;
    @FXML
    private TextField txtProductMadeDate;

    //available stock pane components
    @FXML
    private Label lblEnglishAfternoonCount;
    @FXML
    private Label lblEarlGreyTeaCount;
    @FXML
    private Label lblEnglishBreakfastTeaCount;

    //company summary pane components
    @FXML
    private Label lblSuppliersCount;
    @FXML
    private Label lblTransportersCount;
    @FXML
    private Label lblCustomersCount;



    private Stage stage=new Stage();

    private String id;
    private String name;
    private String contact_no;
    private Date reg_date;
    private String address;
    private String route;
    private String stockId;
    private LocalDate madeDate;
    private int qtyOnHand;
    private String productType;
    private String employee_id;
    private String origin;

    boolean dashboardDisplay=true;

    public void hideMenuBar(MouseEvent mouseEvent) {

      SideBarOperations.hideMenuBar(menuBarPanel);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

       SideBarOperations.showMenuBar(menuBarPanel);

    }


    public void showSupplierManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/suppliers_manage_form.fxml")));
        dashboardDisplay=false;
    }

    public void showBuyersManage(ActionEvent actionEvent) throws IOException {

        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customers_manage_form.fxml")));
        dashboardDisplay=false;
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
        reg_date= Date.valueOf(dtpSupRegDate.getValue());
        address=txtSupAddress.getText();

        Supplier supplier = new Supplier(id,name,contact_no,reg_date,address,null);

        try {

            SupplierModel.addSupplierToDatabase(supplier);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

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

        } catch (SQLException throwable) {

            throwable.printStackTrace();

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

        Product product = new Product(id,name,madeDate,qtyOnHand,productType);

        try {

            ProductModel.addProductToDatabase(product);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


        stage = (Stage) btnPdtAdd.getScene().getWindow();
        stage.close();

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


    public void editSupplier(ActionEvent actionEvent) throws IOException {

        loadEditForm("supplier");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



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

    public void addEditedProductToDatabase(ActionEvent actionEvent) {

        
    }

    public void searchProductFromDatabase(ActionEvent actionEvent) {

        id = txtProductIdSearch.getText();

        Product product = ProductModel.searchProductFromDatabase(id);
        Stock_Product stock_product = Stock_ProductModel.searchProductFromDatabase(id);
    }

    public void editProduct(ActionEvent actionEvent) throws IOException {

        loadEditForm("product");

    }

    public void exitSystem(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void updatePanes(MouseEvent mouseEvent) throws SQLException {

        int supplier_count = SupplierModel.getSuppliersCount();
        int transporters_count = TransporterModel.getTransportersCount();
        int customers_count = CustomerModel.getCustomerCount();

        lblSuppliersCount.setText(String.valueOf(supplier_count));
        lblTransportersCount.setText(String.valueOf(transporters_count));
        lblCustomersCount.setText(String.valueOf(customers_count));

    }
}
