package src.main.data;

import src.main.classes.Mannschaft;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MannschaftenDaoImpl implements MannschaftenDao {
    private Connection connection;

    public MannschaftenDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Mannschaft> getAll() {
        List<Mannschaft> mannschaften = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Mannschaft");
            while (resultSet.next()) {
                Mannschaft mannschaft = new Mannschaft(resultSet.getString("name"), resultSet.getDouble("strength"));
                mannschaft.setId(resultSet.getInt("id"));
                mannschaften.add(mannschaft);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mannschaften;
    }

    @Override
    public void add(Mannschaft mannschaft) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Mannschaft (name, strength) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, mannschaft.getName());
            preparedStatement.setDouble(2, mannschaft.getStrength());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                mannschaft.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Mannschaft mannschaft) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Mannschaft SET name = ?, strength = ? WHERE id = ?");
            preparedStatement.setString(1, mannschaft.getName());
            preparedStatement.setDouble(2, mannschaft.getStrength());
            preparedStatement.setInt(3, mannschaft.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mannschaft getMannschaftByName(String name) {
        Mannschaft mannschaft = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Mannschaft WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mannschaft = new Mannschaft(resultSet.getString("name"), resultSet.getDouble("strength"));
                mannschaft.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mannschaft;
    }

    @Override
    public Mannschaft getMannschaftById(int id) {
        Mannschaft mannschaft = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Mannschaft WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mannschaft = new Mannschaft(resultSet.getString("name"), resultSet.getDouble("strength"));
                mannschaft.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mannschaft;
    }

    @Override
    public void delete(Mannschaft mannschaft) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Mannschaft WHERE id = ?");
            preparedStatement.setInt(1, mannschaft.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMannschaftByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Mannschaft WHERE name = ?");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}