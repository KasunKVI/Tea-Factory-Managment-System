package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountantDashboardFormController implements Initializable {

    @FXML
    public AnchorPane supplierBillPane;
    public Pane panel;
    public BorderPane pn;
    public AnchorPane bgPane;

    public void loadSupplierBills(ActionEvent actionEvent) throws IOException {

        bgPane.getChildren().clear();
        bgPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/suppliers_bill_get__form.fxml")));

    }


    public void SnowBar(MouseEvent mouseEvent) {

        panel.setVisible(true);
        FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),panel);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        fadeTransition1.play();

        TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),panel);
        translateTransition1.setByX(+88);
        translateTransition1.play();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        panel.setVisible(false);


    }

}
