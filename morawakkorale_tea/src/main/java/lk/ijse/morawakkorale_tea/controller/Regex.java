package lk.ijse.morawakkorale_tea.controller;


import javafx.scene.control.TextField;

public class Regex {

    public static String nameRegEx(){
        return "^[\\p{L} .'-]+$";
    }

    public static String emailRegEx(){
        return "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,})+$";
    }

    public static String contactRegEx(){
        return "^0\\d{9}$";
    }

    public static String customerIdRegEx(){
        return "^C\\d{3}$";
    }

    public static String employeeIdRegEx(){
     return "^E\\d{3}$";
    }

    public static String productIdRegEx(){
        return "^P\\d{3}$";
    }

    public static void setTextColorRed(TextField txt){

        txt.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

    }

    public static void setTextBlack(TextField txt){

        txt.setStyle("-fx-text-fill: black; -fx-font-weight: normal;");

    }

    public static String valueRegEx(){
        return "\\d*";
    }
}
