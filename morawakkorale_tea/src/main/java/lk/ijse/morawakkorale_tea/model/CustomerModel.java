package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.SQLException;

public class CustomerModel {

    public static boolean addCustomerToDatabase(Customer customer) throws SQLException {


        String sql = "INSERT INTO Customer VALUES(?,?,?,?,?) ";
        return CrudUtil.execute(sql, customer.getId(), customer.getName(), customer.getOrigin(), customer.getContact(),customer.getEmployee_id());

    }
}
