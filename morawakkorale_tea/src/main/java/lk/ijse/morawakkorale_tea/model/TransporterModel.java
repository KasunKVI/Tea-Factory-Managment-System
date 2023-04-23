package lk.ijse.morawakkorale_tea.model;

import lk.ijse.morawakkorale_tea.dto.Supplier;
import lk.ijse.morawakkorale_tea.dto.Transporter;
import lk.ijse.morawakkorale_tea.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransporterModel {
    public static boolean addTransporterToDatabase(Transporter transporter) throws SQLException {


        String sql = "INSERT INTO Transporter(tp_id,name,contact,route,address) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, transporter.getId(),transporter.getName(),transporter.getContact(),transporter.getRoute(),transporter.getAddress());
    }

    public static Transporter searchTransporterFromDatabase(String id) throws SQLException {

        String sql = "SELECT * FROM Transporter WHERE tp_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        if (resultSet.next()){

            Integer tp_id=resultSet.getInt(1);
            String name=resultSet.getString(2);
            String contact=resultSet.getString(3);
            String route=resultSet.getString(4);
            String address=resultSet.getString(5);

            return new Transporter(tp_id,name,contact,route,address);
        }
        return null;
    }

    public static boolean addEditedTransportersToDatabase(Transporter transporter) throws SQLException {

        String sql = "UPDATE Transporter SET name = ?, contact = ?, route = ?, address = ? WHERE tp_id = ?";
        return CrudUtil.execute(sql,transporter.getName(),transporter.getContact(),transporter.getRoute(),transporter.getAddress(),transporter.getId());

    }

    public static int getTransportersCount() throws SQLException {

        String sql = "SELECT tp_id FROM Transporter";
        ResultSet resultSet = CrudUtil.execute(sql);

        int count = 0;

        while (resultSet.next()){
            count++;
        }
        return count;
    }

    public static List<Transporter> getAll() throws SQLException {

        String sql = "SELECT * FROM Transporter";

        List<Transporter>  transporter = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            transporter.add(new Transporter(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));

        }
        return transporter;
    }

    public static String getTransporterPaymentId(int transporterId) throws SQLException {

        String sql = "SELECT pay_id FROM Transporter WHERE tp_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,transporterId);

        String pay_id=null;

        while (resultSet.next()){
            pay_id=resultSet.getString(1);
        }
        return pay_id;
    }

    public static boolean deleteTransporterFromDatabase(int transporterId) throws SQLException {

        String sql = "DELETE FROM Transporter WHERE tp_id = ?";
        return CrudUtil.execute(sql,transporterId);
    }

    public static List<Integer> getAllIds() throws SQLException {

        String sql = "SELECT tp_id FROM Transporter";
        List<Integer> idS = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()){
            idS.add(resultSet.getInt(1));
        }
        return idS;
    }

    public static String getRoute(Integer id) throws SQLException {

        String sql = "SELECT route FROM Transporter WHERE tp_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        String route=null;

        while (resultSet.next()){

            route=resultSet.getString(1);
        }
        return route;
    }
}
