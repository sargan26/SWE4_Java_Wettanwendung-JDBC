import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.main.classes.Mannschaft;
import src.main.data.MannschaftenDaoImpl;
import src.main.database.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MannschaftenDaoImplTest {
    private MannschaftenDaoImpl dao;
    private Mannschaft testMannschaft;

    @BeforeEach
    public void setUp() throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        dao = new MannschaftenDaoImpl(connection);
        testMannschaft = new Mannschaft("Test", 1.0);
        dao.add(testMannschaft);
    }

    @Test
    public void testGetAll() {
        assertEquals(1, dao.getAll().size(), "Size should be 1");
    }

    @Test
    public void testAdd() {
        Mannschaft newMannschaft = new Mannschaft("New Test", 2.0);
        dao.add(newMannschaft);
        assertEquals(2, dao.getAll().size(), "Size should be 2");
    }

    @Test
    public void testUpdate() {
        Mannschaft updatedMannschaft = new Mannschaft("Test", 3.0);
        dao.update(updatedMannschaft);
        assertEquals(3.0, dao.getMannschaftByName("Test").getStrength(), "Strength should be updated to 3.0");
    }

    @Test
    public void testGetMannschaftByName() {
        Mannschaft foundMannschaft = dao.getMannschaftByName("Test");
        assertEquals(testMannschaft, foundMannschaft, "Mannschafts should match");
    }

    @Test
    public void testGetMannschaftById() {
        Mannschaft foundMannschaft = dao.getMannschaftById(testMannschaft.getId());
        assertEquals(testMannschaft, foundMannschaft, "Mannschafts should match");
    }

    @Test
    public void testDelete() {
        dao.delete(testMannschaft);
        assertEquals(0, dao.getAll().size(), "Size should be 0");
    }

    @Test
    public void testDeleteMannschaftByName() {
        dao.deleteMannschaftByName("Test");
        assertEquals(0, dao.getAll().size(), "Size should be 0");
    }
}