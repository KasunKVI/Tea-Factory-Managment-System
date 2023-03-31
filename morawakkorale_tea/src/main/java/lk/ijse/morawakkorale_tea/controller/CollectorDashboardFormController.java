package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CollectorDashboardFormController implements Initializable {

    @FXML
    Button btnSetting;

    @FXML
    public Pane menuBarPanel;

    @FXML
    AnchorPane pane2;

    @FXML
    AnchorPane menu;

    @FXML
    public Pane supplierValuesAddPane;
    public Pane stockPane;




    public void btnSettingOnAction(ActionEvent actionEvent) throws IOException {

        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/accountant_dashboard_form.fxml"));
        stage.setTitle("Accountant Dashboard");
        stage.centerOnScreen();
        stage.setScene(new Scene     (root));

        stage.show();
    }

    public void btncolorchanger(MouseEvent mouseEvent) {

        btnSetting.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 15px;");

    }

    public void btncolorback(MouseEvent mouseEvent) {

        btnSetting.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 15px;");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


    public void hideMenuBar(MouseEvent mouseEvent) {

        MenuBarOperation.fadeMenuBar(menuBarPanel,1,0,-88);

        menuBarPanel.setDisable(true);

    }


    public void showMenuBar(MouseEvent mouseEvent) {

        menuBarPanel.setDisable(false );
        MenuBarOperation.fadeMenuBar(menuBarPanel,0,1,+88);
        menuBarPanel.setVisible(true);

    }

    public void SuppierValuesAddWindowShower(ActionEvent actionEvent) {

        supplierValuesAddPane.setVisible(true);
        FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),supplierValuesAddPane);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        fadeTransition1.play();

    }
}
