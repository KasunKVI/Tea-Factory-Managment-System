package lk.ijse.morawakkorale_tea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.controller.LoginFormController;

public class Launcher extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        stage.setTitle("Login Window");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();

        primaryStage=stage;

    }
}
