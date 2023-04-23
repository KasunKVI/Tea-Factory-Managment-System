package lk.ijse.morawakkorale_tea.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.morawakkorale_tea.dto.User;
import lk.ijse.morawakkorale_tea.model.LogInModel;

import java.io.IOException;
import java.sql.SQLException;

public class SideBarOperations {




    private Stage stage = new Stage();


    public static void showMenuBar(Pane menuBarPanel){

        menuBarPanel.setDisable(false );
        fadeMenuBar(menuBarPanel,0,1,+88);
        menuBarPanel.setVisible(true);

    }

    public static void hideMenuBar(Pane menuBarPanel){

        fadeMenuBar(menuBarPanel,1,0,-88);
        menuBarPanel.setDisable(true);

    }

    public static void fadeMenuBar(Pane pane,int from,int to,int x) {

        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane);
        fadeTransition1.setFromValue(from);
        fadeTransition1.setToValue(to);
        fadeTransition1.play();

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane);
        translateTransition1.setByX(x);
        translateTransition1.play();
    }


    public static void logOut(Button btnLogOut) throws IOException {

        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(SideBarOperations.class.getResource("/view/login_form.fxml"));
        stage.setTitle("Setting Window");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();
    }
}
