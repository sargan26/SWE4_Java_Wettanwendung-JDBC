package src.main.data;

import javafx.collections.ObservableList;
import src.main.classes.Mannschaft;

public class MannschaftenDao {
    private ObservableList<Mannschaft> mannschaften;

    public MannschaftenDao(ObservableList<Mannschaft> mannschaften) {
        this.mannschaften = mannschaften;
    }

    public ObservableList<Mannschaft> getAll() {
        return mannschaften;
    }

    public void add(Mannschaft mannschaft) {
        this.mannschaften.add(mannschaft);
    }

    public void update(Mannschaft mannschaft) {
        // TODO
    }

    public void delete(Mannschaft mannschaft) {
        this.mannschaften.remove(mannschaft);
    }
}