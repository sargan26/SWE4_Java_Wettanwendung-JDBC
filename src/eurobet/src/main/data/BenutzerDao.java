package src.main.data;

import javafx.collections.ObservableList;
import src.main.classes.Benutzer;

import java.io.Serializable;
import java.util.List;

public class BenutzerDao {
    private List<Benutzer> benutzer;

    public BenutzerDao(List<Benutzer> benutzer) {
        this.benutzer = benutzer;
    }

    public List<Benutzer> getAll() {
        return benutzer;
    }

    public void add(Benutzer benutzer) {
        this.benutzer.add(benutzer);
    }

    public Benutzer getBenutzerByName(String username) {
        for (Benutzer benutzer : benutzer) {
            if ( benutzer.getUsername().equals(username)) {
                return benutzer;
            }
        }
        return null;
    }

    public Benutzer getBenutzerById(int id) {
        for (Benutzer benutzer : benutzer) {
            if (benutzer.getId() == id) {
                return benutzer;
            }
        }
        return null;
    }

    public void update(Benutzer benutzer) {
        // TODO
    }

    public void delete(Benutzer benutzer) {
        this.benutzer.remove(benutzer);
    }
}