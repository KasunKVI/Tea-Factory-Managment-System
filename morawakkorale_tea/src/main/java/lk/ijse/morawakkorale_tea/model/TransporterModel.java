package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransporterModel {
    public static boolean addTransporterToDatabase(Transporter transporter) throws SQLException {


        String sql = "INSERT INTO Transporter(tp_id,name,contact,route,address) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, transporter.getId(),transporter.getName(),transporter.getContact(),transporter.getRoute(),transporter.getAddress());
    }

    public static Transporter searchTransporterFromDatabase(String id) throws SQLException {

        String sql = "SELECT * FROM Transporter WHERE tp_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        if (resultSet.next()){

            String tp_id=resultSet.getString(1);
            String name=resultSet.getString(2);
            String contact=resultSet.getString(3);
            String route=resultSet.getString(4);
            String address=resultSet.getString(5);
            String payment=resultSet.getString(6);

            return new Transporter(tp_id,name,contact,route,address);
        }
        return null;
    }

    public static boolean addEditedTransportersToDatabase(Transporter transporter) throws SQLException {

        String sql = "UPDATE Transporter SET name = ?, contact = ?, route = ?, address = ? WHERE tp_id = ?";
        return CrudUtil.execute(sql,transporter.getName(),transporter.getContact(),transporter.getRoute(),transporter.getAddress(),transporter.getId());

    }
}
