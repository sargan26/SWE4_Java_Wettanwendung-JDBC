package src.main.data;

import src.main.classes.Tipp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TippsDaoImpl implements TippsDao {
    private Connection connection;

    public TippsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Tipp> getAll() {
        List<Tipp> tipps = new ArrayList<>();
        System.out.println("List .getAll() in TippsDaoImpl");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Tipp");
            System.out.println("resultSet: " + resultSet);
            System.out.println("resultSet.getInt(\"benutzerId\") = " + resultSet.getInt("benutzerId"));
            System.out.println("resultSet.getInt(\"spielId\") = " + resultSet.getInt("spielId"));
            System.out.println("resultSet.getInt(\"tipp\") = " + resultSet.getInt("tipp"));

            while (resultSet.next()) {
                Tipp tipp = new Tipp(
                        resultSet.getInt("benutzerId"),
                        resultSet.getInt("spielId"),
                        //Tipp.TippAuswahl.fromValue(resultSet.getInt("tipp")),
                        Tipp.TippAuswahl.OFFEN,
                        resultSet.getInt("potential")
                );
                tipp.setId(resultSet.getInt("id"));
                tipp.setPunkte(resultSet.getInt("punkte"));
                tipps.add(tipp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipps;
    }

    @Override
    public void add(Tipp tipp) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Tipp (benutzerId, spielId, tipp, potential) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, tipp.getBenutzerId());
            preparedStatement.setInt(2, tipp.getSpielId());
            preparedStatement.setInt(3, tipp.getTipp().getValue());
            preparedStatement.setInt(4, tipp.getPotential());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                tipp.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Tipp tipp) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Tipp SET benutzerId = ?, spielId = ?, tipp = ?, potential = ?, punkte = ? WHERE id = ?");
            preparedStatement.setInt(1, tipp.getBenutzerId());
            preparedStatement.setInt(2, tipp.getSpielId());
            preparedStatement.setString(3, tipp.getTipp().toString());
            preparedStatement.setInt(4, tipp.getPotential());
            preparedStatement.setInt(5, tipp.getPunkte());
            preparedStatement.setInt(6, tipp.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Tipp getTippById(int id) {
        Tipp tipp = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Tipp WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tipp = new Tipp(
                        resultSet.getInt("benutzerId"),
                        resultSet.getInt("spielId"),
                        Tipp.TippAuswahl.valueOf(resultSet.getString("tipp")),
                        resultSet.getInt("potential")
                );
                tipp.setId(resultSet.getInt("id"));
                tipp.setPunkte(resultSet.getInt("punkte"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipp;
    }

    @Override
    public void delete(Tipp tipp) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Tipp WHERE id = ?");
            preparedStatement.setInt(1, tipp.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}