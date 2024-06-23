package src.main.data;

import src.main.classes.Benutzer;
import src.main.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BenutzerDaoImpl implements BenutzerDao {
    private Connection connection;

    public BenutzerDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Benutzer> getAll() {
        List<Benutzer> benutzerList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Benutzer");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Benutzer benutzer = new Benutzer(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Benutzer.BenutzerRolle.fromValue(resultSet.getInt("rolle"))
                );
                benutzer.setId(resultSet.getInt("id"));
                benutzer.setPunkte(resultSet.getInt("punkte"));
                benutzer.setGesperrt(resultSet.getBoolean("gesperrt"));
                benutzerList.add(benutzer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return benutzerList;
    }

    public void add(Benutzer benutzer) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Benutzer (username, password, rolle, punkte, gesperrt) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, benutzer.getUsername());
            statement.setString(2, benutzer.getPassword());
            statement.setInt(3, benutzer.getRolle().getValue());
            statement.setInt(4, benutzer.getPunkte());
            statement.setBoolean(5, benutzer.isGesperrt());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Benutzer getBenutzerByName(String username) {
        Benutzer benutzer = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Benutzer WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                benutzer = new Benutzer(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Benutzer.BenutzerRolle.fromValue(resultSet.getInt("rolle"))
                );

                benutzer.setId(resultSet.getInt("id"));
                benutzer.setPunkte(resultSet.getInt("punkte"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return benutzer;
    }

    public Benutzer getBenutzerById(int id) {
        Benutzer benutzer = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Benutzer WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                benutzer = new Benutzer(
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        Benutzer.BenutzerRolle.valueOf(resultSet.getString("rolle"))
                );
                benutzer.setId(resultSet.getInt("id"));
                benutzer.setPunkte(resultSet.getInt("punkte"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return benutzer;
    }

    public void update(Benutzer benutzer) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Benutzer SET name = ?, password = ?, rolle = ?, punkte = ? WHERE id = ?");
            statement.setString(1, benutzer.getUsername());
            statement.setString(2, benutzer.getPassword());
            statement.setString(3, benutzer.getRolle().toString());
            statement.setInt(4, benutzer.getPunkte());
            statement.setInt(5, benutzer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Benutzer benutzer) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Benutzer WHERE id = ?");
            statement.setInt(1, benutzer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Benutzer WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByName(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Benutzer WHERE name = ?");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}