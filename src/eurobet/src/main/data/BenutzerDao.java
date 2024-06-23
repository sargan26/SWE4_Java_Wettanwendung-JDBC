package src.main.data;

import src.main.classes.Benutzer;
import java.util.List;

public interface BenutzerDao {
    List<Benutzer> getAll();
    void add(Benutzer benutzer);
    Benutzer getBenutzerByName(String username);
    Benutzer getBenutzerById(int id);
    void update(Benutzer benutzer);
    void delete(Benutzer benutzer);
    void deleteById(int id);
    void deleteByName(String username);
}