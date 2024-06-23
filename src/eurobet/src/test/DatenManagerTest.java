import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.main.classes.Benutzer;
import src.main.data.*;
import src.main.database.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatenManagerTest {
    private DatenManager datenManager;

    @BeforeEach
    public void setUp() throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        BenutzerDao benutzerDao = new BenutzerDaoImpl(connection);
        MannschaftenDao mannschaftenDao = new MannschaftenDaoImpl(connection);
        SpieleDao spieleDao = new SpieleDaoImpl(connection, mannschaftenDao);
        TippsDao tippsDao = new TippsDaoImpl(connection);
        datenManager = new DatenManager(spieleDao, mannschaftenDao, benutzerDao, tippsDao);
    }

    @Test
    public void testGetMannschaftenDao() {
        assertNotNull(datenManager.getMannschaftenDao(), "MannschaftenDao should not be null");
    }

    @Test
    public void testGetAndSetEingeloggterBenutzer() {
        Benutzer benutzer = new Benutzer("test", "test", Benutzer.BenutzerRolle.USER);
        datenManager.setEingeloggterBenutzer(benutzer);
        assertEquals(benutzer, datenManager.getEingeloggterBenutzer(), "Benutzer should match");
    }
}