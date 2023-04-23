package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.CartDTO;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order_ProductModel {
    public static boolean addOrder(String id, List<CartDTO> cartDTOS) throws SQLException {
        for (CartDTO dto : cartDTOS) {
            if(!addOrders(dto,id)) {
                return false;
            }
        }
        return true;
    }

    private static boolean addOrders(CartDTO dto, String id) throws SQLException {

        String sql = "INSERT INTO Order_Product VALUES (?,?,?)";
        return CrudUtil.execute(sql,dto.getId(),id,dto.getType());
    }

    public static List<String> getIds(String s) throws SQLException {


        String sql = "SELECT id_order FROM Order_Product WHERE type = ?";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql,s);

        while (resultSet.next()){
           ids.add(resultSet.getString(1));

        }
        return ids;
    }
}
