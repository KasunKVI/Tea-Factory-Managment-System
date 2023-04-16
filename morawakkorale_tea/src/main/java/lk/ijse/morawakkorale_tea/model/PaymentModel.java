package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.SQLException;

public class PaymentModel {


    public static boolean deleteTransporterPaymentFromDatabase(String pay_id) throws SQLException {

        String sql = "DELETE FROM Payment WHERE pay_id = ?";
        return CrudUtil.execute(sql,pay_id);
    }
}
