package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.Launcher;
import lk.ijse.morawakkorale_tea.dto.LogIn;
import lk.ijse.morawakkorale_tea.model.LogInModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LoginFormController {

    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtPassword;

    public void loginBtnOnAction() throws IOException, SQLException {

        String id = txtUserId.getText();
        String password = txtPassword.getText();
        Stage stage = new Stage();

        LogIn login=new LogIn(id,password);

       String pass =  LogInModel.checkUser(login);


        if ( password.equals(pass)){

            if(id.equals("A001")) {

                Parent root = FXMLLoader.load(getClass().getResource("/view/collector_dashboard_form.fxml"));
                stage.setTitle("Collector Dashboard");
                stage.setScene(new Scene(root));

            }else if (id.equals("A002")){


                Parent root = FXMLLoader.load(getClass().getResource("/view/manager_dashboard_form.fxml"));
                stage.setTitle("Manager Dashboard");
                stage.setScene(new Scene(root));


             } else if (id.equals("A003") ){

                Parent root = FXMLLoader.load(getClass().getResource("/view/accountant_dashboard_form.fxml"));
                stage.setTitle("Accountant Dashboard");
                stage.setScene(new Scene(root));


             } else if (id.equals("A004") ) {

                Parent root = FXMLLoader.load(getClass().getResource("/view/general_manager_dashboard_form.fxml"));
                stage.setTitle("General Manager Dashboard");
                stage.setScene(new Scene(root));
            }

            stage.centerOnScreen();
            stage.show();
            Launcher.primaryStage.close();


        }else{

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Password or User Id incorrect. Do you want enter again?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                txtUserId.requestFocus();

            }else {
                System.exit(0);
            }
        }



    }

    public void doneUserId(ActionEvent actionEvent) {

        txtPassword.requestFocus();

    }

    public void doneUserIdAndPassword(ActionEvent actionEvent) throws IOException, SQLException {

        loginBtnOnAction();

    }
}
