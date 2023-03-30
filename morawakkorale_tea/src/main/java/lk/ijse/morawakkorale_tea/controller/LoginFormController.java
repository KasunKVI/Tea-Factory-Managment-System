package lk.ijse.morawakkorale_tea.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    public void LoginbtnOnAction() throws IOException {

    Stage stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/view/collector_dashboard_form.fxml"));
          stage.setTitle("Collector Dashboard");
          stage.centerOnScreen();
          stage.setScene(new Scene(root));

          stage.show();
    }
}
