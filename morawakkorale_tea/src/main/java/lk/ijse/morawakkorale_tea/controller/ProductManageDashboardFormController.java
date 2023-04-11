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
import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Stock_Product;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.dto.tm.ProductTM;
import lk.ijse.morawakkorale_tea.dto.tm.SupplierTM;
import lk.ijse.morawakkorale_tea.model.ProductModel;
import lk.ijse.morawakkorale_tea.model.Stock_ProductModel;
import lk.ijse.morawakkorale_tea.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ProductManageDashboardFormController implements Initializable {

    @FXML
    private TableView<ProductTM> allProductDetails;

    @FXML
    private TableColumn<?,?> clmProductId;
    @FXML
    private TableColumn<?,?> clmProductName;
    @FXML
    private TableColumn<?,?> clmProductMadeDate;
    @FXML
    private TableColumn<?,?> clmProductStockId;
    @FXML
    private TableColumn<?,?> clmProductLeafValue;
    @FXML
    private TableColumn<?,?> clmProductType;
    @FXML
    private TableColumn<?,?> clmProductQuantity;

    Stage stage = new Stage();

    public void addNewProduct(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/product_adding_form.fxml"));
        stage.setTitle("Product Adder");
        stage.setScene(new Scene(root));
        stage.show();

    }



    public void loadEditForm(String form) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/"+form+"_edit_form.fxml"));
        stage.setTitle(form+"  Edit");
        stage.setScene(new Scene(root));
        stage.show();

    }



    public void editProduct(ActionEvent actionEvent) throws IOException {

        loadEditForm("product");

    }

    public void refreshTable(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCellValueFactory();
        getAll();

    }

    private void getAll() {

        ObservableList<ProductTM> obList = FXCollections.observableArrayList();
        try {
            List<Product> pdList = ProductModel.getAll();
            List<Stock_Product> stpList = Stock_ProductModel.getAll();


            for(Product product : pdList) {

                for(Stock_Product stock_product:stpList)

                 obList.add(new ProductTM(
                        product.getId(),
                        product.getName(),
                        product.getMade_date(),
                        stock_product.getStock_id(),
                        stock_product.getLeaf_value(),
                        product.getType(),
                        product.getQty_on_hand()
                ));
            }
            allProductDetails.setItems(obList);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


    }

    private void  setCellValueFactory() {

        clmProductId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        clmProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        clmProductMadeDate.setCellValueFactory(new PropertyValueFactory<>("Made_date"));
        clmProductStockId.setCellValueFactory(new PropertyValueFactory<>("Stock_id"));
        clmProductLeafValue.setCellValueFactory(new PropertyValueFactory<>("Leaf_value"));
        clmProductType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        clmProductQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

    }
}
