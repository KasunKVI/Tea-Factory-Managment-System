package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.db.DBConnection;
import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Stock_Product;

import java.sql.Connection;
import java.sql.SQLException;

public class AddProductModel {

    public static boolean addProductToDataBase(String stock_id,int leaf_Value, Product product, Stock_Product stock_product) throws SQLException {

        Connection con = null;

        try {

            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSave = StockModel.updateStock(stock_id,leaf_Value);

            if (isSave){

                boolean isAdd = ProductModel.addProductToDatabase(product);

                if(isAdd){

                    boolean isDone = Stock_ProductModel.addProductToDatabase(stock_product);

                    if(isDone){

                        con.commit();
                        return true;

                    }
                }
            }
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            con.rollback();
            return false;
        }finally {
            con.setAutoCommit(true);
        }

    }
}
