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
import lk.ijse.morawakkorale_tea.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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

    private Stage stage = new Stage();
    private boolean condition = false;
    @FXML
    private Button btnProductAdd;
    private String id;


    public void addProductToDatabase(ActionEvent actionEvent) {

        if(!condition||txtPdtId.getText().isEmpty()||txtProductLeafValue.getText().isEmpty()||txtPdtProductQuantity.getText().isEmpty()||txtProductUnitPrice.getText().isEmpty()||cmbPdtProductType.getValue()==null||cmbPdtStockId.getValue()==null||dtpPdtMadeDate.getValue()==null) {

            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();

        }else {

            id = txtPdtId.getText();
            int leaf_value = Integer.parseInt(txtProductLeafValue.getText());
            String stockId = cmbPdtStockId.getValue();
            LocalDate madeDate = dtpPdtMadeDate.getValue();
            int qtyOnHand = Integer.parseInt(txtPdtProductQuantity.getText());
            String productType = cmbPdtProductType.getValue();
            Double unit_price = Double.valueOf(txtProductUnitPrice.getText());

            Product product = new Product(id, madeDate, qtyOnHand, productType, unit_price);
            Stock_Product stock_product = new Stock_Product(id, stockId, leaf_value);

            try {

                AddProductModel.addProductToDataBase(stockId, leaf_value, product, stock_product);

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Product added successful. Do yo want to add another product", yes, no).showAndWait();

                if (result.orElse(no) == yes) {

                    discardProductAddForm(actionEvent);

                }else {

                    stage = (Stage) btnPdtAdd.getScene().getWindow();
                    stage.close();
                }

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

    }

    public void searchProductFromDatabase(ActionEvent actionEvent) {

        if(!condition){
            new Alert(Alert.AlertType.ERROR, "Input Valid Id").show();

        }else {

            id = txtProductIdSearch.getText();

            try {
                Product product = ProductModel.searchProductFromDatabase(id);
                Stock_Product stock_product = Stock_ProductModel.searchProductFromDatabase(id);

                if (product == null || stock_product == null) {

                    new Alert(Alert.AlertType.ERROR, "There is no product in this id").show();
                    txtProductIdSearch.requestFocus();

                } else {

                    lblProductIdEdit.setText(id);
                    txtProductLeafValueEdit.setText(String.valueOf(stock_product.getLeaf_value()));
                    dtpProductDateEdit.setValue(product.getMade_date());
                    txtProductQuantityEdit.setText(String.valueOf(product.getQty_on_hand()));
                    txtProductStockIdEdit.setText(stock_product.getStock_id());
                    cmbProductTypeBoxEdit.setValue(product.getType());

                    txtProductQuantityEdit.requestFocus();
                }

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

    }


    public void addEditedProductToDatabase(ActionEvent actionEvent) {

        if (!condition||txtProductQuantityEdit.getText().isEmpty()||txtProductLeafValueEdit.getText().isEmpty()||txtProductStockIdEdit.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();
        } else {

            id = lblProductIdEdit.getText();
            LocalDate made_date = dtpProductDateEdit.getValue();
            int qty = Integer.parseInt(txtProductQuantityEdit.getText());
            String type = cmbProductTypeBoxEdit.getValue();

            Product product = new Product(id, made_date, qty, type, null);

            try {
                ProductModel.addEditedProductToDatabase(product);
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Product edited successful. Do yo want to edit another product", yes, no).showAndWait();

                if (result.orElse(no) == yes) {

                    discardProductEditForm(actionEvent);

                }else {

                    stage = (Stage) btnProductAdd.getScene().getWindow();
                    stage.close();
                }

            } catch (SQLException throwable) {

                throwable.printStackTrace();

            }
        }

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
        lblStockValue.setText("");
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
                 
    public void enterProductId(KeyEvent keyEvent) throws SQLException {

        if (!txtPdtId.getText().matches(Regex.productIdRegEx())) {
            condition=false;
            FontChanger.setTextColorRed(txtPdtId);

        }else if(ProductModel.isExist(txtPdtId.getText())) {

            FontChanger.setTextColorRed(txtPdtId);
            condition = false;

        }else {
            FontChanger.setTextBlack(txtPdtId);
            condition = true;
        }
    }

    public void enterLeafValue(KeyEvent keyEvent) {

        if(!txtProductLeafValue.getText().matches(Regex.productLeafValue())){
            condition=false;
            FontChanger.setTextColorRed(txtProductLeafValue);
        }else {
            FontChanger.setTextBlack(txtProductLeafValue);
            condition = true;
        }
    }

    public void enterUnitPrice(KeyEvent keyEvent) {

        if(!txtProductUnitPrice.getText().matches(Regex.unitPrice())){
            condition=false;
            FontChanger.setTextColorRed(txtProductUnitPrice);
        }else {
            FontChanger.setTextBlack(txtProductUnitPrice);
            condition = true;
        }
    }

    public void enterQuantity(KeyEvent keyEvent) {

        if(!txtPdtProductQuantity.getText().matches(Regex.valueRegEx())){
            condition=false;
            FontChanger.setTextColorRed(txtPdtProductQuantity);
        }else {
            FontChanger.setTextBlack(txtPdtProductQuantity);
            condition = true;
        }
    }

    public void enterProductQuantityED(KeyEvent keyEvent) {

        if(!txtProductQuantityEdit.getText().matches(Regex.valueRegEx())){
            condition=false;
            FontChanger.setTextColorRed(txtProductQuantityEdit);
        }else {
            FontChanger.setTextBlack(txtProductQuantityEdit);
            condition = true;
        }
    }

    public void enterProductIdSearch(KeyEvent keyEvent) {

        if(!txtProductIdSearch.getText().matches(Regex.productIdRegEx())){
            condition=false;
            FontChanger.setTextColorRed(txtProductIdSearch);
        }else {
            FontChanger.setTextBlack(txtProductIdSearch);
            condition = true;
        }
    }
}
