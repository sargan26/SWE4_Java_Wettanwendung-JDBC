package src.main.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import src.main.data.*;
import src.main.database.DatabaseManager;

public class EuroBetServer {
    public static void main(String[] args) {
        try {
            // Create the datamanger, load example data
            Connection connection = DatabaseManager.getConnection();

            BenutzerDao benutzerDao = new BenutzerDaoImpl(connection);
            MannschaftenDao mannschaftenDao = new MannschaftenDaoImpl(connection);
            SpieleDao spieleDao = new SpieleDaoImpl(connection, mannschaftenDao);
            TippsDao tippsDao = new TippsDaoImpl(connection);


            DatenManager datenManager = new DatenManager(spieleDao, mannschaftenDao, benutzerDao, tippsDao);
            deleteAll();
            datenManager.loadExampleBenutzer();
            datenManager.loadExampleSpiele();
            datenManager.loadExampleTipps();

            // Create the service and bind it to the registry
            EuroBetServiceImpl euroBetService = new EuroBetServiceImpl(datenManager);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("EuroBetService", euroBetService);
            System.out.println("EuroBetServer started");
        } catch (Exception e) {
            System.out.println("EuroBetServer failed to start:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteAll() {
        String sqlBenutzer = "DELETE FROM Benutzer";
        String sqlMannschaft = "DELETE FROM Mannschaft";
        String sqlSpiel = "DELETE FROM Spiel";
        String sqlTipp = "DELETE FROM Tipp";

        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sqlBenutzer);
             PreparedStatement pstmt2 = connection.prepareStatement(sqlMannschaft);
             PreparedStatement pstmt3 = connection.prepareStatement(sqlSpiel);
             PreparedStatement pstmt4 = connection.prepareStatement(sqlTipp)) {
            pstmt4.executeUpdate();
            pstmt.executeUpdate();
            pstmt3.executeUpdate();
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
