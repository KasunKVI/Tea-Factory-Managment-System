package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductModel {

    public static boolean addProductToDatabase(Product product) throws SQLException {

        String sql = "INSERT INTO Product VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql,product.getId(),product.getName(),product.getMade_date(),product.getQty_on_hand(),product.getType());
    }

    public static Product searchProductFromDatabase(String id) {
        return null;
    }

    public static int getProductCountCount(String type) throws SQLException {

        String sql = "SELECT product_id FROM Product WHERE type = ?";
        ResultSet resultSet = CrudUtil.execute(sql,type);

        int count=0;

        while (resultSet.next()){
            count++;
        }
        return count;
    }

}
