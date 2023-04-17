package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderModel {
    public static int getMonthlyOrderCount(int i) throws SQLException {

        int year = LocalDate.now().getYear();

        String sql = "SELECT order_id FROM Orders WHERE YEAR(date) = ? AND MONTH(date) = ?";
        ResultSet resultSet = CrudUtil.execute(sql,year,i);

        int count=0;

        while (resultSet.next()){
            count++;
        }
        return count;
    }
}
