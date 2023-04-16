package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Supplier_StockModel {

    public static boolean addSupplierValuesToDatabase(Supplier_Stock supplier_stock) throws SQLException {

        String sql = "INSERT INTO Supplier_Stock(sup_id,stock_id,value,bag_count) VALUES (?,?,?,?)";
        return CrudUtil.execute(sql,supplier_stock.getSup_id(),supplier_stock.getStock_id(),supplier_stock.getValue(),supplier_stock.getBag_count());

    }


    public static boolean deleteSupplierFromDatabase(int supplierId) throws SQLException {

        String sql = "DELETE FROM Supplier_Stock WHERE sup_id = ?";
        return CrudUtil.execute(sql,supplierId);
    }

    public static boolean addSupplierValuesToDatabase(List<Supplier_Stock> supplier_stock) throws SQLException {
        for(Supplier_Stock stock : supplier_stock){
            if(!addSupplierValuesToDatabase(stock)){
                return false;
            }
        }
        return true;
    }
}
