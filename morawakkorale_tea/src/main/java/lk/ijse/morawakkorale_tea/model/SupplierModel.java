package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.SQLException;

public class SupplierModel {

    public static boolean addSupplierToDatabase(Supplier supplier) throws SQLException {

        String sql = "INSERT INTO Supplier VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, supplier.getId(),supplier.getName(),supplier.getContact(),supplier.getReg_date(),supplier.getAddress());


    }
}
