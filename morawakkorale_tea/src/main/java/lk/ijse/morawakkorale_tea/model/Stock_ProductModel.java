package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Stock_Product;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Stock_ProductModel {
    public static Stock_Product searchProductFromDatabase(String id) throws SQLException {

        String sql = "SELECT * FROM Stock_Product WHERE id_product = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        if(resultSet.next()){

            String product_id = resultSet.getString(1);
            String stock_id = resultSet.getString(2);
            Integer leaf_value = resultSet.getInt(3);

            return new Stock_Product(product_id,stock_id,leaf_value);
        }
        return null;
    }

    public static boolean addProductToDatabase(Stock_Product stock_product) throws SQLException {

        String sql = "INSERT INTO Stock_Product VALUES (?,?,?)";
        return CrudUtil.execute(sql,stock_product.getProduct_id(),stock_product.getStock_id(),stock_product.getLeaf_value());
    }

    public static List<Stock_Product> getAll() throws SQLException {

        String sql = "SELECT * FROM Stock_Product";
        List<Stock_Product> stock_product = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            stock_product.add(new Stock_Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
            ));

        }
        return stock_product;
    }
}
