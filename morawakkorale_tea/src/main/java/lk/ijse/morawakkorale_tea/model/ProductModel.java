package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.CartDTO;
import lk.ijse.morawakkorale_tea.dto.Product;
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
        return CrudUtil.execute(sql,product.getId(),product.getMade_date(),product.getQty_on_hand(),product.getType(),product.getUnit_price());
    }

    public static Product searchProductFromDatabase(String id) throws SQLException {

        String sql = "SELECT * FROM Product WHERE product_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        if(resultSet.next()){

            String product_id = resultSet.getString(1);
            LocalDate made_date = resultSet.getDate(2).toLocalDate();
            Integer qty = resultSet.getInt(3);
            String type = resultSet.getString(4);
            Double unit_price = resultSet.getDouble(5);

            return new Product(product_id,made_date,qty,type,unit_price);
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

        String sql = "UPDATE Product SET  made_date = ?, qty_on_hand = ?, type = ? WHERE product_id =?";
        return CrudUtil.execute(sql,product.getMade_date(),product.getQty_on_hand(),product.getType(),product.getId());
    }

    public static List<Product> getAll() throws SQLException {

        String sql = "SELECT * FROM Product";

        List<Product> product = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            product.add(new Product(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));

        }
        return product;
    }

    public static int getProductCount(String s) throws SQLException {

        String sql = "SELECT product_id FROM Product WHERE type = ?";
        ResultSet resultSet = CrudUtil.execute(sql,s);

        int count = 0;

        while (resultSet.next()){

            count++;

        }
        return count;
    }

    public static List<String> getAllIds() throws SQLException {

        String sql = "SELECT product_id FROM Product WHERE qty_on_hand > 0";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static Product getAll(String item_id) throws SQLException {

        String sql = "SELECT * FROM Product WHERE product_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,item_id);

        if(resultSet.next()){

            String product_id = resultSet.getString(1);
            LocalDate made_date = resultSet.getDate(2).toLocalDate();
            Integer qty = resultSet.getInt(3);
            String type = resultSet.getString(4);
            Double unit_price = resultSet.getDouble(5);

            return new Product(product_id,made_date,qty,type,unit_price);
        }
        return null;
    }

    public static boolean updateProductCount(List<CartDTO> cartDTOS) throws SQLException {
        for (CartDTO dto : cartDTOS) {
            if(!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(CartDTO dto) throws SQLException {

        String sql = "UPDATE Product SET qty_on_hand = (qty_on_hand - ?) WHERE product_id = ?";
        return CrudUtil.execute(sql,dto.getQty(),dto.getId());
    }

    public static int getProductQty(String txt) {

        String sql = "SELECT qty_on_hand FROM Product WHERE product_id = ?";

        int qty = 0;
        try {
            ResultSet resultSet = CrudUtil.execute(sql,txt);

            while (resultSet.next()){
                qty=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qty;
    }

    public static boolean isExist(String text) throws SQLException {
        String sql = "SELECT type FROM Product WHERE product_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,text);

        if (resultSet.next()){
            return true;
        }else {
            return false;
        }
    }
}
