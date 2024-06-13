package src.main.data;

import javafx.collections.ObservableList;
import src.main.classes.Mannschaft;

import java.util.List;

public class MannschaftenDao {
    private List<Mannschaft> mannschaften;

    public MannschaftenDao(List<Mannschaft> mannschaften) {
        this.mannschaften = mannschaften;
    }

    public List<Mannschaft> getAll() {
        return mannschaften;
    }

    public void add(Mannschaft mannschaft) {
        this.mannschaften.add(mannschaft);
    }

    public void update(Mannschaft mannschaft) {
        Mannschaft oldMannschaft = getMannschaftByName(mannschaft.getName());
        oldMannschaft.setStrength(mannschaft.getStrength());
        System.out.println("Mannschaft updated: " + oldMannschaft.toString() + " new strength is: " + mannschaft.getStrength());
    }

    public Mannschaft getMannschaftByName(String name) {
        for (Mannschaft mannschaft : mannschaften) {
            if ( mannschaft.getName().equals(name)) {
                return mannschaft;
            }
        }
        return null;
    }

    public Mannschaft getMannschaftById(int id) {
        for (Mannschaft mannschaft : mannschaften) {
            if (mannschaft.getId() == id) {
                return mannschaft;
            }
        }
        return null;
    }

    public void delete(Mannschaft mannschaft) {
        this.mannschaften.remove(mannschaft);
    }

    public void deleteMannschaftByName(String name) {
        Mannschaft mannschaftToDelete = null;
        for (Mannschaft mannschaft : mannschaften) {
            if (mannschaft.getName().equals(name)) {
                mannschaftToDelete = mannschaft;
            }
        }
        if (mannschaftToDelete != null) {
            this.mannschaften.remove(mannschaftToDelete);
        }
    }
}