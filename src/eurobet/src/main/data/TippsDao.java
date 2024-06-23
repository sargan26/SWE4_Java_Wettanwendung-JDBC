package src.main.data;

import src.main.classes.Tipp;

import java.util.List;

public interface TippsDao {
    List<Tipp> getAll();

    void add(Tipp tipp);

    void update(Tipp tipp);

    Tipp getTippById(int id);

    void delete(Tipp tipp);
}
