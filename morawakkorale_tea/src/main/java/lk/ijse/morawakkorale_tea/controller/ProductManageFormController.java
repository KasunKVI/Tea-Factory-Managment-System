package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Stock_Product;
import lk.ijse.morawakkorale_tea.model.ProductModel;
import lk.ijse.morawakkorale_tea.model.Stock_ProductModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProductManageFormController {

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
    private Button btnPdtAdd;

    private String id;

    private Stage stage = new Stage();

    public void addNewProduct(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/product_adding_form.fxml"));
        stage.setTitle("Product Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void addProductToDatabase(ActionEvent actionEvent) {

        id=txtPdtId.getText();
        String name = txtPdtName.getText();
        String stockId = (String) cmbPdtStockId.getValue();
        LocalDate madeDate = dtpPdtMadeDate.getValue();
        int qtyOnHand = Integer.parseInt(txtPdtProductQuantity.getText());
        String productType = (String) cmbPdtProductType.getValue();

        Product product = new Product(id, name, madeDate, qtyOnHand, productType);

        try {

            ProductModel.addProductToDatabase(product);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


        stage = (Stage) btnPdtAdd.getScene().getWindow();
        stage.close();

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
}
