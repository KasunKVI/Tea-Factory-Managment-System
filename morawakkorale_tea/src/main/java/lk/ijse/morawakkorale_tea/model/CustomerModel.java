package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.SQLException;

public class CustomerModel {
    public static boolean addCustomerToDatabase(Customer customer) throws SQLException {


        String sql = "INSERT INTO Customer(cust_id,name,origin,contact) VALUES(?,?,?,?) ";
        return CrudUtil.execute(sql, customer.getId(), customer.getName(), customer.getOrigin(), customer.getContact());
    }
}
