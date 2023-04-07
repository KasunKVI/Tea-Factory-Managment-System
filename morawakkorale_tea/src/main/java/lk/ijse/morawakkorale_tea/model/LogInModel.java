package lk.ijse.morawakkorale_tea.model;

import javafx.fxml.FXML;
import lk.ijse.morawakkorale_tea.dto.LogIn;
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
}
