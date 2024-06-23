package src.main.data;

import src.main.classes.Mannschaft;
import src.main.classes.Spiel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpieleDaoImpl implements SpieleDao {
    private Connection connection;
    private MannschaftenDao mannschaftenDao;

    public SpieleDaoImpl(Connection connection, MannschaftenDao manschaftenDao) {
        this.connection = connection;
        this.mannschaftenDao = manschaftenDao;
    }

@Override
public List<Spiel> getAll() {
    List<Spiel> spiele = new ArrayList<>();
    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Spiel");
        while (resultSet.next()) {
            Mannschaft mannschaft1 = mannschaftenDao.getMannschaftById(resultSet.getInt("mannschaft1"));
            Mannschaft mannschaft2 = mannschaftenDao.getMannschaftById(resultSet.getInt("mannschaft2"));
            Spiel spiel = new Spiel(
                    resultSet.getTimestamp("anstosszeit").toLocalDateTime(),
                    mannschaft1,
                    mannschaft2,
                    resultSet.getString("spielort"),
                    resultSet.getInt("tore1"),
                    resultSet.getInt("tore2"),
                    resultSet.getTimestamp("endezeit") == null ? null : resultSet.getTimestamp("endezeit").toLocalDateTime(),
                    resultSet.getBoolean("spielBeendet"),
                    null // Ergebnis wird später gesetzt
            );
            spiel.setId(resultSet.getInt("id"));
            spiele.add(spiel);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return spiele;
}

    @Override
    public Spiel getSpielById(int id) {
        Spiel spiel = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Spiel WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                spiel = new Spiel(
                        resultSet.getTimestamp("anstosszeit").toLocalDateTime(),
                        null, // Mannschaften werden später gesetzt
                        null, // Mannschaften werden später gesetzt
                        resultSet.getString("spielort"),
                        resultSet.getInt("tore1"),
                        resultSet.getInt("tore2"),
                        resultSet.getTimestamp("endezeit").toLocalDateTime(),
                        resultSet.getBoolean("spielBeendet"),
                        null // Ergebnis wird später gesetzt
                );
                spiel.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spiel;
    }

    @Override
    public void add(Spiel spiel) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Spiel (anstosszeit, mannschaft1, mannschaft2, spielort, tore1, tore2, endezeit, spielBeendet) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(spiel.getAnstosszeit()));
            if (spiel.getMannschaft1() == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            } else {
                preparedStatement.setInt(2, spiel.getMannschaft1().getId());
            }
            if (spiel.getMannschaft2() == null) {
                preparedStatement.setNull(3, Types.INTEGER);
            } else {
                preparedStatement.setInt(3, spiel.getMannschaft2().getId());
            }
            preparedStatement.setString(4, spiel.getSpielort());
            preparedStatement.setInt(5, spiel.getTore1());
            preparedStatement.setInt(6, spiel.getTore2());
            preparedStatement.setTimestamp(7, null);
            preparedStatement.setBoolean(8, spiel.getSpielBeendet());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                spiel.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Spiel spiel) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Spiel SET anstosszeit = ?, spielort = ?, tore1 = ?, tore2 = ?, endezeit = ?, spielBeendet = ? WHERE id = ?");
            preparedStatement.setTimestamp(1, Timestamp.valueOf(spiel.getAnstosszeit()));
            preparedStatement.setString(2, spiel.getSpielort());
            preparedStatement.setInt(3, spiel.getTore1());
            preparedStatement.setInt(4, spiel.getTore2());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(spiel.getEndezeit()));
            preparedStatement.setBoolean(6, spiel.getSpielBeendet());
            preparedStatement.setInt(7, spiel.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Spiel spiel) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Spiel WHERE id = ?");
            preparedStatement.setInt(1, spiel.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Spiel WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}