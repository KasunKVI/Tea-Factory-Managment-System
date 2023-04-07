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

    public static List<Supplier_Stock> getSupplierValue(String stockId) throws SQLException {

        String sql = "SELECT * FROM Supplier_Stock WHERE stock_id=?";
        List<Supplier_Stock> supplierStock = new ArrayList<>();
        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        while (resultSet.next()){
            supplierStock.add(new Supplier_Stock (
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            ));
        }
        return supplierStock;
    }
}
