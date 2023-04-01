package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.SQLException;

public class TransporterModel {
    public static boolean addTransporterToDatabase(Transporter transporter) throws SQLException {


        String sql = "INSERT INTO Transporter(tp_id,name,contact,route,address) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, transporter.getId(),transporter.getName(),transporter.getContact(),transporter.getRoute(),transporter.getAddress());
    }
}
