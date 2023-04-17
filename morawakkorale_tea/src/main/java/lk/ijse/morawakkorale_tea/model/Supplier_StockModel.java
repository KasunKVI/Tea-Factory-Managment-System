package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Supplier_Bill;
import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Supplier_StockModel {

    public static boolean addSupplierValuesToDatabase(Supplier_Stock supplier_stock) throws SQLException {

        String sql = "INSERT INTO Supplier_Stock(sup_id,stock_id,value,bag_count,date) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, supplier_stock.getSup_id(), supplier_stock.getStock_id(), supplier_stock.getValue(), supplier_stock.getBag_count(), supplier_stock.getDate());

    }


    public static boolean deleteSupplierFromDatabase(int supplierId) throws SQLException {

        String sql = "DELETE FROM Supplier_Stock WHERE sup_id = ?";
        return CrudUtil.execute(sql, supplierId);
    }

    public static boolean addSupplierValuesToDatabase(List<Supplier_Stock> supplier_stock) throws SQLException {
        for (Supplier_Stock stock : supplier_stock) {
            if (!addSupplierValuesToDatabase(stock)) {
                return false;
            }
        }
        return true;
    }

    public static List<Supplier_Bill> getAll() throws SQLException {

        String sql = "SELECT sup_id,value,bag_count,payment_ FROM Supplier_Stock";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<Supplier_Bill> supplier_bills = new ArrayList<>();

        while (resultSet.next()) {
            supplier_bills.add(new Supplier_Bill(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            ));
        }
        return supplier_bills;
    }

    public static int getSupplierValues(Integer id, int month, String value) throws SQLException {

        String sql = "SELECT "+value+" FROM Supplier_Stock WHERE MONTH(date) = ? AND sup_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,month,id);

        int count = 0;

        while (resultSet.next()){
            count+=resultSet.getInt(1);
        }
        return count;
    }
}
