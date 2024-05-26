package src.main.data;

import javafx.collections.ObservableList;
import src.main.classes.Tipp;

public class TippsDao {
    private ObservableList<Tipp> tipps;

    public TippsDao(ObservableList<Tipp> tipps) {
        this.tipps = tipps;
    }

    public ObservableList<Tipp> getAll() {
        return tipps;
    }

    public void add(Tipp tipp) {
        this.tipps.add(tipp);
    }

    public void update(Tipp tipp) {
        // TODO
    }

    public void delete(Tipp tipp) {
        this.tipps.remove(tipp);
    }
}