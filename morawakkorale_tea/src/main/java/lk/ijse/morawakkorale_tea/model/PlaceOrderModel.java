package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.db.DBConnection;
import lk.ijse.morawakkorale_tea.dto.CartDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderModel {


    public static boolean placeOrder(String id, String customer_id, int payId, String text, List<CartDTO> cartDTOS) throws SQLException {

        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();


        con.setAutoCommit(false);

        boolean isSaved = OrderModel.placeOrder(id,text,customer_id,payId);

        if(isSaved){
            boolean isUpdate = ProductModel.updateProductCount(cartDTOS);

            if (isUpdate) {
                boolean isAdd = Order_ProductModel.addOrder(id,cartDTOS);

                if (isAdd) {
                    con.commit();
                    return true;
                }

                }
            }
        return false;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
             con.rollback();
            return false;
        } finally {
        con.setAutoCommit(true);
        }
    }

}
