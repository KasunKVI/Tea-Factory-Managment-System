package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Product;
import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {

    public static boolean addProductToDatabase(Product product) throws SQLException {

        String sql = "INSERT INTO Product VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql,product.getId(),product.getName(),product.getMade_date(),product.getQty_on_hand(),product.getType());
    }

    public static Product searchProductFromDatabase(String id) throws SQLException {

        String sql = "SELECT * FROM Product WHERE product_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        if(resultSet.next()){

            String product_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String made_date = String.valueOf(resultSet.getDate(3));
            Integer qty = resultSet.getInt(4);
            String type = resultSet.getString(5);

            return new Product(product_id,name,made_date,qty,type);
        }
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

    public static boolean addEditedProductToDatabase(Product product) throws SQLException {

        String sql = "UPDATE Product SET name = ?, made_date = ?, qty_on_hand = ?, type = ? WHERE product_id =?";
        return CrudUtil.execute(sql,product.getName(),product.getMade_date(),product.getQty_on_hand(),product.getType(),product.getId());
    }

    public static List<Product> getAll() throws SQLException {

        String sql = "SELECT * FROM Product";

        List<Product> product = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            product.add(new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            ));

        }
        return product;
    }
}
