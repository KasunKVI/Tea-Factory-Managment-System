package lk.ijse.morawakkorale_tea.model;

import javafx.fxml.FXML;
import lk.ijse.morawakkorale_tea.dto.LogIn;
import lk.ijse.morawakkorale_tea.dto.User;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInModel {



    public static String checkUser(LogIn login) throws SQLException {

        String sql = "SELECT * FROM User WHERE id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,login.getId());

        if(resultSet.next()) {

            String id = resultSet.getString(1);
            String password = resultSet.getString(2);

            return password;
        }
       return null;
    }

    public static User getUserDetails(String id) throws SQLException {

        String sql = "SELECT * FROM User WHERE id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        while (resultSet.next()){

            String uId =resultSet.getString(1);
            String pass = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String name = resultSet.getString(4);
            String email = resultSet.getString(5);
            String position = resultSet.getString(6);

            return new User(uId,pass,contact,name,email,position);
        }
        return null;
    }

    public static boolean updateUser(String id, String contact, String name, String email) throws SQLException {


        String sql = "UPDATE User SET contact = ?, name = ?, email = ? WHERE id = ?";
        return CrudUtil.execute(sql,contact,name,email,id);
    }
}
