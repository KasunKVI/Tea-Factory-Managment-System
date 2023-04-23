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
import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Stock_Product;
import lk.ijse.morawakkorale_tea.model.AddProductModel;
import lk.ijse.morawakkorale_tea.model.ProductModel;
import lk.ijse.morawakkorale_tea.model.StockModel;
import lk.ijse.morawakkorale_tea.model.Stock_ProductModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ProductAddOrEditFormController implements Initializable {

    @FXML
    private Label lblProductIdEdit;
    @FXML
    private TextField txtProductLeafValueEdit;
    @FXML
    private TextField   txtProductStockIdEdit;
    @FXML
    private TextField txtProductQuantityEdit;
    @FXML
    private ComboBox<String> cmbProductTypeBoxEdit;
    @FXML
    private DatePicker dtpProductDateEdit;


    //Components from product adding form
    @FXML
    private TextField txtPdtId;
    @FXML
    private TextField txtProductLeafValue;
    @FXML
    private ComboBox<String> cmbPdtStockId;
    @FXML
    private DatePicker dtpPdtMadeDate;
    @FXML
    private TextField txtPdtProductQuantity;
    @FXML
    private ComboBox<String> cmbPdtProductType;
    @FXML
    private Label lblStockValue;
    @FXML
    private TextField txtProductUnitPrice;
    @FXML
    private TextField txtProductIdSearch;


    @FXML
    private Button btnPdtAdd;
    @FXML
    private Button btnProductAdd;
    private String id;


    public void addProductToDatabase(ActionEvent actionEvent) {

        id=txtPdtId.getText();
        int leaf_value = Integer.parseInt(txtProductLeafValue.getText());
        String stockId = cmbPdtStockId.getValue();
        LocalDate madeDate = dtpPdtMadeDate.getValue();
        int qtyOnHand = Integer.parseInt(txtPdtProductQuantity.getText());
        String productType = cmbPdtProductType.getValue();
        Double unit_price = Double.valueOf(txtProductUnitPrice.getText());

        Product product = new Product(id, madeDate, qtyOnHand, productType,unit_price);
        Stock_Product stock_product = new Stock_Product(id,stockId,leaf_value);

        try {

            AddProductModel.addProductToDataBase(stockId,leaf_value,product,stock_product);

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

            if (product==null||stock_product==null){

                new Alert(Alert.AlertType.ERROR,"There is no supplier in this id").show();
            }else {

                lblProductIdEdit.setText(id);
                txtProductLeafValueEdit.setText(String.valueOf(stock_product.getLeaf_value()));
                dtpProductDateEdit.setValue(product.getMade_date());
                txtProductQuantityEdit.setText(String.valueOf(product.getQty_on_hand()));
                txtProductStockIdEdit.setText(stock_product.getStock_id());
                cmbProductTypeBoxEdit.setValue(product.getType());
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        txtProductQuantityEdit.requestFocus();

    }


    public void addEditedProductToDatabase(ActionEvent actionEvent) {

        id=lblProductIdEdit.getText();
        LocalDate made_date= dtpProductDateEdit.getValue();
        int qty= Integer.parseInt(txtProductQuantityEdit.getText());
        String type=cmbProductTypeBoxEdit.getValue();

        Product product = new Product(id,made_date,qty,type,null);

        try {
            ProductModel.addEditedProductToDatabase(product);

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

        Stage stage = (Stage) btnProductAdd.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void addProducts(ComboBox<String> typeBox) {

        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> type = new ArrayList<>();

        type.add("English Afternoon - Green Tea");
        type.add("Earl Grey Tea - Black Tea");
        type.add("English Breakfast - Black Tea");

        obList.addAll(type);

        typeBox.setItems(obList);
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


    public void discardProductEditForm(ActionEvent actionEvent) {

        txtProductIdSearch.clear();
        txtProductLeafValueEdit.clear();
        cmbProductTypeBoxEdit.setValue(null);
        txtProductStockIdEdit.clear();
        txtProductQuantityEdit.clear();
        dtpProductDateEdit.setValue(null);
        lblProductIdEdit.setText("");

    }

    public void discardProductAddForm(ActionEvent actionEvent) {

        txtPdtId.clear();
        txtProductLeafValue.clear();
        txtPdtProductQuantity.clear();
        cmbPdtStockId.setItems(null);
        cmbPdtProductType.setItems(null);
        dtpPdtMadeDate.setValue(null);
        txtProductUnitPrice.clear();
    }

    public void enteredProductType(ActionEvent actionEvent) {
        txtPdtProductQuantity.requestFocus();
    }

    public void enteredProductQuantity(ActionEvent actionEvent) {
        addProductToDatabase(actionEvent);
    }

    public void enteredStockId(ActionEvent actionEvent) {

        String stock_id = cmbPdtStockId.getValue();
        try {
            String sId_value = StockModel.getStockValue(stock_id);
            lblStockValue.setText(sId_value + "Kg");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        txtProductLeafValue.requestFocus();
    }

    public void initializeTypeAddCmb(Event event) {
        addProducts(cmbPdtProductType);
    }

    public void initializedProductTypeEditCmb(Event event) {
        addProducts(cmbProductTypeBoxEdit);
    }
                 
    public void enterProductId(KeyEvent keyEvent) {
        if (!txtPdtId.getText().matches(Regex.productIdRegEx())) {
            Regex.setTextColorRed(txtPdtId);
        }else Regex.setTextBlack(txtPdtId);
    }

    public void enterLeafValue(KeyEvent keyEvent) {
        if(!txtProductLeafValue.getText().matches(Regex.valueRegEx())){
            Regex.setTextColorRed(txtProductLeafValue);
        }else Regex.setTextBlack(txtProductLeafValue);
    }

    public void enterUnitPrice(KeyEvent keyEvent) {
        if(!txtProductUnitPrice.getText().matches(Regex.valueRegEx())){
            Regex.setTextColorRed(txtProductUnitPrice);
        }else Regex.setTextBlack(txtProductUnitPrice);
    }

    public void enterQuantity(KeyEvent keyEvent) {

        if(!txtPdtProductQuantity.getText().matches(Regex.valueRegEx())){
            Regex.setTextColorRed(txtPdtProductQuantity);
        }else Regex.setTextBlack(txtPdtProductQuantity);
    }

    public void enterProductQuantityED(KeyEvent keyEvent) {

        if(!txtProductQuantityEdit.getText().matches(Regex.valueRegEx())){
            Regex.setTextColorRed(txtProductQuantityEdit);
        }else Regex.setTextBlack(txtProductQuantityEdit);
    }
}
