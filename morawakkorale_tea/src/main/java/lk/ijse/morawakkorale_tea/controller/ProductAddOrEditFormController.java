package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Stock_Product;
import lk.ijse.morawakkorale_tea.model.ProductModel;
import lk.ijse.morawakkorale_tea.model.StockModel;
import lk.ijse.morawakkorale_tea.model.Stock_ProductModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductAddOrEditFormController implements Initializable {

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
    private TextField txtProductMadeDateEdit;


    //Components from product adding form
    @FXML
    private TextField txtPdtId;
    @FXML
    private TextField txtPdtName;
    @FXML
    private ComboBox<String> cmbPdtStockId;
    @FXML
    private DatePicker dtpPdtMadeDate;
    @FXML
    private TextField txtPdtProductQuantity;
    @FXML
    private ComboBox<String> cmbPdtProductType;

    @FXML
    private TextField txtProductIdSearch;


    @FXML
    private Button btnPdtAdd;
    private String id;


    public void addProductToDatabase(ActionEvent actionEvent) {

        id=txtPdtId.getText();
        String name = txtPdtName.getText();
        String stockId = cmbPdtStockId.getValue();
        String madeDate = String.valueOf(dtpPdtMadeDate.getValue());
        int qtyOnHand = Integer.parseInt(txtPdtProductQuantity.getText());
        String productType = cmbPdtProductType.getValue();

        Product product = new Product(id, name, madeDate, qtyOnHand, productType);
        Stock_Product stock_product = new Stock_Product(id,stockId,null);

        try {

            ProductModel.addProductToDatabase(product);
            Stock_ProductModel.addProductToDatabase(stock_product);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


        Stage stage = (Stage) btnPdtAdd.getScene().getWindow();
        stage.close();

    }

    public void searchProductFromDatabase(ActionEvent actionEvent) {

        id = txtProductIdSearch.getText();

        try {
            Product product = ProductModel.searchProductFromDatabase(id);
            Stock_Product stock_product = Stock_ProductModel.searchProductFromDatabase(id);

            if (product==null){

                new Alert(Alert.AlertType.ERROR,"There is no supplier in this id").show();
            }else {


                lblProductIdEdit.setText(product.getId());
                txtProductNameEdit.setText(product.getName());
                txtProductMadeDateEdit.setText(product.getMade_date());
                txtProductQuantityEdit.setText(String.valueOf(product.getQty_on_hand()));
                txtProductStockIdEdit.setText(stock_product.getStock_id());
                txtProductTypeEdit.setText(product.getType());
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }


    public void addEditedProductToDatabase(ActionEvent actionEvent) {

        id=lblProductIdEdit.getText();
        String name=txtProductNameEdit.getText();
        String made_date=txtProductMadeDateEdit.getText();
        int qty= Integer.parseInt(txtProductQuantityEdit.getText());
        String type=txtProductTypeEdit.getText();

        Product product = new Product(id,name,made_date,qty,type);

        try {
            ProductModel.addEditedProductToDatabase(product);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void addProducts(Event event) {

        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> type = new ArrayList<>();

        type.add("English Afternoon - Green Tea");
        type.add("Earl Grey Tea - Black Tea");
        type.add("English Breakfast - Black Tea");

        obList.addAll(type);

        cmbPdtProductType.setItems(obList);
    }

    public void addStockIds(Event event) {
        
        ObservableList <String> observableList = FXCollections.observableArrayList();
        try {
            List<String> stock_id_list = StockModel.getSupplierValue();
            observableList.addAll(stock_id_list);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        cmbPdtStockId.setItems(observableList);


    }
}
