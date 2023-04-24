package lk.ijse.morawakkorale_tea.controller;

import javafx.scene.control.TextField;


public class FontChanger {


    public static void setTextColorRed(TextField txt){

        txt.setStyle("-fx-text-fill: red; -fx-font-family: Arial Bold; -fx-font-size: 22px; -fx-background-radius: 10px;");

       // txt.getStyleClass().add("my-text-field-red");

    }

    public static void setTextBlack(TextField txt){

        txt.setStyle("-fx-text-fill: black; -fx-font-family: Arial Bold; -fx-font-size: 22px; -fx-background-radius: 10px;");

        //txt.getStyleClass().add("my-text-field-black");

    }

    public static void setSearchBarRed(TextField txt){
        txt.setStyle("-fx-text-fill: red; -fx-font-family:'DejaVu Serif'; -fx-font-weight: bold; -fx-font-size: 26px; -fx-background-radius: 15px;");
    }
    public static void setSearchBarBlack(TextField txt){
        txt.setStyle("-fx-text-fill: black; -fx-font-family:'DejaVu Serif'; -fx-font-weight: bold; -fx-font-size: 26px; -fx-background-radius: 15px;");
    }
}
