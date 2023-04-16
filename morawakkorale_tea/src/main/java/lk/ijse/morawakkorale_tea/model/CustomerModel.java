package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Customer;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Customer> getAll() throws SQLException {

        String sql = "SELECT * FROM Customer";

        List<Customer>  customer = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            customer.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));

        }
        return customer;
    }

    public static Customer searchCustomerFromDatabase(String id) throws SQLException {

        String sql = "SELECT * FROM Customer WHERE cust_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        if(resultSet.next()){

            String cust_id= resultSet.getString(1);
            String name=resultSet.getString(2);
            String origin=resultSet.getString(3);
            String contact=resultSet.getString(4);
            String employee_id=resultSet.getString(5);

            return new Customer(cust_id,name,origin,contact,employee_id);
        }
        return null;
    }

    public static boolean addEditedCustomersToDatabase(Customer customer) throws SQLException {

        String sql = "UPDATE Customer SET name = ?, contact = ?, employee_id = ? WHERE cust_id = ?";
        return CrudUtil.execute(sql,customer.getName(),customer.getContact(),customer.getEmployee_id(),customer.getId());

    }

    public static boolean deleteCustomerFromDatabase(String customerId) throws SQLException {

        String sql = "DELETE FROM Customer WHERE cust_id = ?";
        return CrudUtil.execute(sql,customerId);
    }
}
