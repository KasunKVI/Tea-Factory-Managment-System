package lk.ijse.morawakkorale_tea.model;

import javafx.scene.control.TextField;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public static boolean addSupplierToDatabase(Supplier supplier) throws SQLException {

        String sql = "INSERT INTO Supplier VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(sql, supplier.getId(),supplier.getName(),supplier.getContact(),supplier.getReg_date(),supplier.getAddress(),supplier.getStatus());


    }

    public static Supplier searchSupplierFromDatabase(String id) throws SQLException {

        String sql = "SELECT * FROM Supplier WHERE sup_id=?";
        ResultSet resultSet=CrudUtil.execute(sql,id);

        if(resultSet.next()){
            Integer sup_id= Integer.valueOf(resultSet.getString(1));
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

    public static int getNewSupplierCount(int yr, int i) throws SQLException {

        String sql = "SELECT sup_id FROM Supplier WHERE YEAR(reg_date) = ? AND MONTH(reg_date) = ?";
        ResultSet resultSet = CrudUtil.execute(sql,yr,i);

        int count=0;

        while (resultSet.next()){
            count++;
        }
        return count;
    }

    public static int getNewSupplierCountYear(String s) throws SQLException {

        int year= Integer.parseInt(s);
        String sql = "SELECT sup_id FROM Supplier WHERE YEAR(reg_date) = ?";
        ResultSet resultSet = CrudUtil.execute(sql,year);

        int count=0;

        while (resultSet.next()){
            count++;
        }
        return count;
    }

    public static List<Supplier> getAll() throws SQLException {

        String sql = "SELECT * FROM Supplier";

        List<Supplier> supplier = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
                supplier.add(new Supplier(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                ));

        }
        return supplier;
    }

    public static boolean deleteSupplierFromDatabase(int supplierId) throws SQLException {

        String sql = "DELETE FROM Supplier WHERE sup_id = ?";
        return CrudUtil.execute(sql,supplierId);
    }

    public static List<Integer> getAllIds() throws SQLException {

        String sql = "SELECT sup_id FROM Supplier";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<Integer> ids = new ArrayList<>();

        while (resultSet.next()){
            ids.add(resultSet.getInt(1));
        }
        return ids;
    }

    public static boolean isExist(String txtSupIdStock) throws SQLException {
        String sql = "SELECT name FROM Supplier WHERE sup_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,txtSupIdStock);

        if (resultSet.next()){
            return false;
        }else {
            return true;
        }
    }
}
