package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.db.DBConnection;
import lk.ijse.morawakkorale_tea.dto.Stock;
import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddStockModel {

    public static boolean addStockToDatabase(List supplier_stock, Stock stock) throws SQLException {

        Connection con=null;
        try {

            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean stockAdd = StockModel.addStockToDatabase(stock);
            if(stockAdd){
                boolean addSupplierVal = Supplier_StockModel.addSupplierValuesToDatabase(supplier_stock);
                if(addSupplierVal){
                    con.commit();
                    return true;
                }
            }
            return false;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            con.rollback();
            return false;
        } finally {
        System.out.println("finally");
        con.setAutoCommit(true);
      }
    }
}
