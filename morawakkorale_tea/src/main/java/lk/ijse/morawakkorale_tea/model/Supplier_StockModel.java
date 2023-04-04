package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.SQLException;

public class Supplier_StockModel {

    public static boolean addSupplierValuesToDatabase(Supplier_Stock supplier_stock) throws SQLException {

        String sql = "INSERT INTO Supplier_Stock(sup_id,stock_id,value,bag_count) VALUES (?,?,?,?)";
        return CrudUtil.execute(sql,supplier_stock.getSup_id(),supplier_stock.getStock_id(),supplier_stock.getValue(),supplier_stock.getBag_count());



    }
}
