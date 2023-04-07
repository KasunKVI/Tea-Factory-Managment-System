package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {

    public static boolean addCustomerToDatabase(Customer customer) throws SQLException {

        String sql = "INSERT INTO Customer VALUES(?,?,?,?,?) ";
        return CrudUtil.execute(sql, customer.getId(), customer.getName(), customer.getOrigin(), customer.getContact(),customer.getEmployee_id());

    }

    public static int getCustomerCount() throws SQLException {

        String sql = "SELECT cust_id FROM Customer";
        ResultSet resultSet = CrudUtil.execute(sql);

        int count=0;

        while (resultSet.next()){
            count++;
        }

        return count;
    }
}
