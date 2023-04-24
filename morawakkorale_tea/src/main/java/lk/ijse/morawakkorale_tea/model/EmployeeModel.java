package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static List<String> getIds() throws SQLException {

        String sql = "SELECT emp_id FROM Employee";
        List<String> emp_ids = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            emp_ids.add(resultSet.getString(1));

        }
        return emp_ids;
    }
}
