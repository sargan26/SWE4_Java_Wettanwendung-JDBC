package src.main.data;

import src.main.classes.Mannschaft;

import java.util.List;

public interface MannschaftenDao {
    List<Mannschaft> getAll();

    void add(Mannschaft mannschaft);

    void update(Mannschaft mannschaft);

    Mannschaft getMannschaftByName(String name);

    Mannschaft getMannschaftById(int id);

    void delete(Mannschaft mannschaft);

    void deleteMannschaftByName(String name);
}
