package lk.ijse.morawakkorale_tea.controller;


import javafx.scene.control.TextField;
import lk.ijse.morawakkorale_tea.model.ProductModel;

public class Regex {

    public static String nameRegEx(){
        return "^[A-Z][a-z]{2,}(\\\\s+[A-Z][a-z]{2,})*$";
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

    public static String valueRegEx(){
        return "^(?!0)\\d*$";
    }

    public static String productLeafValue(){
        return "^(?!0)\\d{3,}$";
    }
    public static String idRegEx(){
        return "[0-9]{5}";
    }

    public static String addressRegEx(){
        return "^([A-Z][a-zA-Z0-9]*)(,\\s*[A-Z][a-zA-Z]+)+$";
    }

    public static String routeRegEx(){
        return "\\b[A-Z][a-zA-Z]*\\b";

    }

    public static String teaValueTrp(){
        return "^(?!0)\\d{3,}$";
    }

    public static String teaValueSup(){
        return "^(?!0)\\d{1,}$";
    }

    public static String unitPrice(){
        return "^(?!0)\\d{2,}$";
    }


}
