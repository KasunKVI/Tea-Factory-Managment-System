package lk.ijse.morawakkorale_tea.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.morawakkorale_tea.dto.User;
import lk.ijse.morawakkorale_tea.model.LogInModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {

    @FXML
    private Button btnCloseSetting;

    @FXML
    private Button btnSaveSetting;

    @FXML
    private Label lblUserId;

    @FXML
    private TextField txtUserContact;

    @FXML
    private TextField txtUserEmail;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserPosition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        String id = LoginFormController.id;

        try {

            User user = LogInModel.getUserDetails(id);

            if(user!=null) {
                lblUserId.setText(user.getId());
                txtUserName.setText(user.getName());
                txtUserContact.setText(user.getContact());
                txtUserEmail.setText(user.getEmail());
                txtUserPosition.setText(user.getPosition());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void closeSettingForm(ActionEvent actionEvent) {


        Stage stage = (Stage) btnCloseSetting.getScene().getWindow();
        stage.close();

    }

    public void saveEditedUserDetails(ActionEvent actionEvent) {

        String id = lblUserId.getText();
        String contact = txtUserContact.getText();
        String name = txtUserName.getText();
        String email = txtUserEmail.getText();

        try {

            LogInModel.updateUser(id,contact,name,email);
            new Alert(Alert.AlertType.CONFIRMATION, "Are you sure").show();

        } catch (SQLException er) {

            er.printStackTrace();
        }
    }

    public void enteredName(KeyEvent keyEvent) {
        if (!txtUserName.getText().matches(Regex.nameRegEx())) {
           Regex.setTextColorRed(txtUserName);
        }else Regex.setTextBlack(txtUserName);
    }

    public void enteredEmail(KeyEvent keyEvent) {

        if(!txtUserEmail.getText().matches(Regex.emailRegEx())){
            Regex.setTextColorRed(txtUserEmail);
        }else Regex.setTextBlack(txtUserEmail);
    }

    public void enteredContact(KeyEvent keyEvent) {

        if(!txtUserContact.getText().matches(Regex.contactRegEx())){
            Regex.setTextColorRed(txtUserContact);
        }else Regex.setTextBlack(txtUserContact);
    }

}
