package lk.ijse.morawakkorale_tea.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.morawakkorale_tea.dto.CartDTO;
import lk.ijse.morawakkorale_tea.dto.Payment;
import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Order_Payment;
import lk.ijse.morawakkorale_tea.dto.tm.PlaceOrderTM;
import lk.ijse.morawakkorale_tea.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController implements Initializable {

    //components from placeOrder form
    @FXML
    private TableColumn<?, ?> clmItemId;

    @FXML
    private TableColumn<?, ?> clmItemQty;

    @FXML
    private TableColumn<?, ?> clmItemTotal;

    @FXML
    private TableColumn<?, ?> clmItemType;

    @FXML
    private TableColumn<?, ?> clmItemUnitPrice;

    @FXML
    private ComboBox<String> cmbOrderCustomerIds;

    @FXML
    private ComboBox<String> cmbOrderItemIds;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblProductMadeDate;

    @FXML
    private Label lblOrderProductType;

    @FXML
    private Label lblOrderTotalPrice;

    @FXML
    private Label lblProductQtyOnHand;

    @FXML
    private Label lblProductUnitPrice;

    @FXML
    private TableView<PlaceOrderTM> tblOrderCart;

    @FXML
    private TextField txtOrderProductQty;

    //observableList for initialize table
    ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();

    public void discardPlaceOrderForm(ActionEvent actionEvent) {
        clearItemDetails();
        cmbOrderCustomerIds.setValue(null);
    }

    //add selected product to cart table
    public void addProductToCart(ActionEvent actionEvent) {

        if(cmbOrderItemIds.getValue()==null||cmbOrderCustomerIds.getValue()==null) {

            new Alert(Alert.AlertType.ERROR, "Please Select Item And Customer First").show();

        }else if(txtOrderProductQty.getText().isEmpty()) {

            new Alert(Alert.AlertType.ERROR, "Please add quantity").show();
            txtOrderProductQty.requestFocus();

        }else {

            int qtyTxt = Integer.parseInt(txtOrderProductQty.getText());
            int qtyLbl = Integer.parseInt(lblProductQtyOnHand.getText());

            if (qtyTxt > qtyLbl || qtyTxt == 0) {

                new Alert(Alert.AlertType.ERROR, "Please Enter Valid Quantity").show();
                txtOrderProductQty.requestFocus();

            } else {


                String item_id = cmbOrderItemIds.getValue();
                String type = lblOrderProductType.getText();
                Double unit_price = Double.valueOf(lblProductUnitPrice.getText());
                Integer qty = qtyTxt;
                Double total = unit_price * qty;

                PlaceOrderTM placeOrderTM = new PlaceOrderTM(item_id, type, unit_price, qty, total);

                obList.add(placeOrderTM);
                tblOrderCart.setItems(obList);
                tblOrderCart.refresh();

                cmbOrderItemIds.getItems().remove(cmbOrderItemIds.getValue().indexOf(item_id));
                calculateNetTotal();
                clearItemDetails();
            }

        }

    }

    //place order and update database
    public void placeOrder(ActionEvent actionEvent) throws SQLException {


        if(tblOrderCart.getItems().isEmpty()){

            new Alert(Alert.AlertType.ERROR, " Please add item to your cart first").show();

        }else {

            int pay = PaymentModel.getPaymentId();
            int payId = pay + 1;

            Payment payment = new Payment(payId, 0, "Order Payment", Double.parseDouble(lblOrderTotalPrice.getText()), (cmbOrderCustomerIds.getValue()) + "-->" + (LocalDate.now()), null, null);
            try {
                PaymentModel.addPayment(payment);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }


            String id = lblOrderId.getText();
            String customer_id = cmbOrderCustomerIds.getValue();

            List<CartDTO> cartDTOS = new ArrayList<>();

            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {

                PlaceOrderTM tm = obList.get(i);

                CartDTO cartDTO = new CartDTO(tm.getItem_id(), tm.getType(), tm.getQty());
                cartDTOS.add(cartDTO);
            }

            boolean isPlaced = PlaceOrderModel.placeOrder(id, customer_id, payId, lblOrderTotalPrice.getText(), cartDTOS);
            if (isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                tblOrderCart.getItems().clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        }

    }

    //show all orders report
    public void showReport(ActionEvent actionEvent) throws FileNotFoundException, JRException, SQLException {


                List<Order_Payment> orders = OrderModel.getAllIds();
                List<Order_Payment> order_payments = new ArrayList<>();

                for (Order_Payment order_payment : orders) {

                    String id = order_payment.getOrder_id();
                    LocalDate date = order_payment.getDate();
                    int total = order_payment.getTotal();
                    String customer = order_payment.getCustomer();

                    order_payments.add(new Order_Payment(
                            id,
                            date,
                            total,
                            customer
                    ));
                }

                File file = ResourceUtils.getFile("/home/kaviyakv/Desktop/Morawakkorale_Tea/morawakkorale_tea/src/main/resources/reports/orderDetails.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(order_payments);
                Map<String, Object> map = new HashMap<>();
                map.put("CreatedBy", "Kasun Kavinda - GDSE65");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource);
                JasperViewer.viewReport(jasperPrint, false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeCustomerIdsBox();
        initializeItemIdsBox();
        setCellValueFactory();
        generateNextOrderId();

    }

    private void setCellValueFactory() {

        clmItemId.setCellValueFactory(new PropertyValueFactory<>("Item_id"));
        clmItemType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        clmItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("Unit_price"));
        clmItemQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        clmItemTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
    }

    //initialize item ids to combo box
    private void initializeItemIdsBox() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> itIds = ProductModel.getAllIds();

            for(String ids:itIds){
                obList.add(ids);
            }
            cmbOrderItemIds.setItems(obList);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //initialize customer ids to combo box
    private void initializeCustomerIdsBox() {

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> cIds = CustomerModel.getAllIds();

            for(String ids : cIds){

                obList.add(ids);
            }

            cmbOrderCustomerIds.setItems(obList);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //set values to labels when selected product
    public void selectedProductId(ActionEvent actionEvent) {

        String item_id = cmbOrderItemIds.getValue();

        try {
            Product product = ProductModel.getAll(item_id);

            if (product!=null) {

                lblOrderProductType.setText(product.getType());
                lblProductUnitPrice.setText(String.valueOf(product.getUnit_price()));
                lblProductQtyOnHand.setText(String.valueOf(product.getQty_on_hand()));
                lblProductMadeDate.setText(String.valueOf(product.getMade_date()));

            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    //calculate order total
    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {

            double total  = (Double) clmItemTotal.getCellData(i);
            netTotal += total;

        }
        lblOrderTotalPrice.setText(String.valueOf(netTotal));
    }

    //generate order id
    public void generateNextOrderId(){

        try {

            String order_id = OrderModel.generateOrderId();
            lblOrderId.setText(order_id);
            lblOrderDate.setText(String.valueOf(LocalDate.now()));

        } catch (SQLException throwable) {

            throwable.printStackTrace();

        }

    }

    //check product qty
    public void enterProductQty(KeyEvent keyEvent) {


        if(cmbOrderItemIds.getValue()==null) {
            FontChanger.setTextColorRed(txtOrderProductQty);
        }else {
            int qtyTxt = Integer.parseInt(txtOrderProductQty.getText());
            int qtyLbl = Integer.parseInt(lblProductQtyOnHand.getText());

            if (qtyTxt > qtyLbl || qtyTxt == 0) {

                FontChanger.setTextColorRed(txtOrderProductQty);
            } else {
                FontChanger.setTextBlack(txtOrderProductQty);
            }
        }
    }

    //clear order form
    public void clearItemDetails(){

        cmbOrderItemIds.setValue(null);
        lblOrderProductType.setText("");
        lblProductMadeDate.setText("");
        lblProductUnitPrice.setText("");
        lblProductQtyOnHand.setText("");
        txtOrderProductQty.clear();

    }
}
