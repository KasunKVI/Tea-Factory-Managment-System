package lk.ijse.morawakkorale_tea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        stage.setTitle("Login Window");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();
    }
}
