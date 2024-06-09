import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.main.classes.Mannschaft;

public class MannschaftTest {
    private Mannschaft mannschaft;

    @BeforeEach
    public void setUp() {
        mannschaft = new Mannschaft("Test", 1.0);
    }

    @Test
    public void testGetName() {
        assertEquals("Test", mannschaft.getName(), "Names should match");
    }

    @Test
    public void testSetName() {
        mannschaft.setName("New Test");
        assertEquals("New Test", mannschaft.getName(), "Names should match");
    }

    @Test
    public void testGetStrength() {
        assertEquals(1.0, mannschaft.getStrength(), "Strengths should match");
    }

    @Test
    public void testSetStrength() {
        mannschaft.setStrength(2.0);
        assertEquals(2.0, mannschaft.getStrength(), "Strengths should match");
    }
}