package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SupplierModel {

    public static boolean addSupplierToDatabase(Supplier supplier) throws SQLException {

        String sql = "INSERT INTO Supplier(sup_id,name,contact,reg_date,address) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, supplier.getId(),supplier.getName(),supplier.getContact(),supplier.getReg_date(),supplier.getAddress());


    }

    public static Supplier searchSupplierFromDatabase(String id) throws SQLException {

        String sql = "SELECT * FROM Supplier WHERE sup_id=?";
        ResultSet resultSet=CrudUtil.execute(sql,id);

        if(resultSet.next()){
            String sup_id=resultSet.getString(1);
            String name=resultSet.getString(2);
            String contact=resultSet.getString(3);
            Date reg_date=resultSet.getDate(4);
            String address=resultSet.getString(5);
            String status=resultSet.getString(6);

            return new Supplier(sup_id,name,contact,reg_date,address,status);
        }
    return null;
    }

    public static boolean addEditedSupplierToDatabase(Supplier supplier) throws SQLException {

        String sql = "UPDATE Supplier SET name = ?, contact = ?, address = ? WHERE sup_id = ?";
        return CrudUtil.execute(sql,supplier.getName(),supplier.getContact(),supplier.getAddress(),supplier.getId());
    }

    public static int getSuppliersCount() throws SQLException {

        String sql = "SELECT sup_id FROM Supplier";
        ResultSet resultSet=CrudUtil.execute(sql);

        int count=0;

        while (resultSet.next()){
            count++;
        }
        return count;
    }
}
