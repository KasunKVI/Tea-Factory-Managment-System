package lk.ijse.morawakkorale_tea.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    public TextField txtUserId;
    public TextField txtPassword;

    public void LoginbtnOnAction() throws IOException {

        String id = txtUserId.getText();
        String password = txtPassword.getText();
        Stage stage = new Stage();

        if (id.equals("A001") && password.equals("collector")){

            Parent root = FXMLLoader.load(getClass().getResource("/view/collector_dashboard_form.fxml"));
            stage.setTitle("Collector Dashboard");
            stage.centerOnScreen();
            stage.setScene(new Scene(root));

            stage.show();

        }else if (id.equals("A002") && password.equals("manager")){

            Parent root = FXMLLoader.load(getClass().getResource("/view/manager_dashboard_form.fxml"));
            stage.setTitle("Manager Dashboard");
            stage.centerOnScreen();
            stage.setScene(new Scene(root));

            stage.show();

        } else if (id.equals("A003") && password.equals("accountant")){

            Parent root = FXMLLoader.load(getClass().getResource("/view/accountant_dashboard_form.fxml"));
            stage.setTitle("Accountant Dashboard");
            stage.centerOnScreen();
            stage.setScene(new Scene(root));

            stage.show();

        } else if (id.equals("A004") && password.equals("g_m")){

            Parent root = FXMLLoader.load(getClass().getResource("/view/general_manager_dashboard_form.fxml"));
            stage.setTitle("General Manager Dashboard");
            stage.centerOnScreen();
            stage.setScene(new Scene(root));

            stage.show();

        }



    }
}
