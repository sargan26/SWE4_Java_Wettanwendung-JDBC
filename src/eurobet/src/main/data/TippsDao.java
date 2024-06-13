package src.main.data;

import javafx.collections.ObservableList;
import src.main.classes.Tipp;

import java.util.List;

public class TippsDao {
    private List<Tipp> tipps;

    public TippsDao(List<Tipp> tipps) {
        this.tipps = tipps;
    }

    public List<Tipp> getAll() {
        return tipps;
    }

    public void add(Tipp tipp) {
        this.tipps.add(tipp);
    }

    public void update(Tipp tipp) {
        // TODO
    }

    public Tipp getTippById(int id) {
        for (Tipp tipp : tipps) {
            if (tipp.getId() == id) {
                return tipp;
            }
        }
        return null;
    }

    public void delete(Tipp tipp) {
        this.tipps.remove(tipp);
    }
}