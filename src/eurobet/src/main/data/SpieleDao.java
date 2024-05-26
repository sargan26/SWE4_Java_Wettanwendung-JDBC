package src.main.data;

import javafx.collections.ObservableList;
import src.main.classes.Spiel;

public class SpieleDao {
    private ObservableList<Spiel> spiele;

    public SpieleDao(ObservableList<Spiel> spiele) {
        this.spiele = spiele;
    }

    public ObservableList<Spiel> getAll() {
        return spiele;
    }

    public Spiel getSpielById(int id) {
        for (Spiel spiel : spiele) {
            if (spiel.getId() == id) {
                return spiel;
            }
        }
        return null;
    }

    public void add(Spiel spiel) {
        spiele.add(spiel);
    }

    public void update(Spiel spiel) {
        // TODO
    }

    public void delete(Spiel spiel) {
        spiele.remove(spiel);
    }
}