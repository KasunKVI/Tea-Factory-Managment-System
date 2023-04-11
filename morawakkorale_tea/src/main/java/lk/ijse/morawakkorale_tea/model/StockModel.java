package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.db.DBConnection;
import lk.ijse.morawakkorale_tea.dto.Stock;
import lk.ijse.morawakkorale_tea.dto.Supplier_Stock;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {

    public static boolean addStockToDatabase(Stock stock) throws SQLException {

        String sql = "INSERT INTO Stock VALUES (?,?,?,?)";
        return CrudUtil.execute(sql,stock.getStock_id(),stock.getDate(),stock.getValue(),stock.getTransporter_id());
        
    }


    public static String generateStockId() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT stock_id FROM Stock ORDER BY stock_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if(resultSet.next()) {

            return splitOrderId(resultSet.getString(1));

        }

        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) {


        if(currentOrderId != null) {

            String[] strings = currentOrderId.split("S");

            int id = Integer.parseInt(strings[1]);

            id++;

            String num= String.valueOf(id);

            String formattedId = "";

            if(num.length()==1||num.length()==2) {

                formattedId = String.format("%03d", id);

            }else{

                formattedId= String.valueOf(id);

            }
            return "S"+formattedId;
        }
        return "S001";
    }

    public static List<String> getSupplierValue() throws SQLException {

        String sql = "SELECT stock_id FROM Stock";
        List<String> stock_ids = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            stock_ids.add(resultSet.getString(1));

        }
        return stock_ids;
    }
}
