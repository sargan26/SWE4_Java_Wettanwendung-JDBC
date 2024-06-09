import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.main.classes.Benutzer;
import src.main.data.DatenManager;

public class DatenManagerTest {
    private DatenManager datenManager;

    @BeforeEach
    public void setUp() {
        datenManager = new DatenManager();
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