package src.main.data;

import src.main.classes.Spiel;

import java.util.List;

public interface SpieleDao {
    List<Spiel> getAll();

    Spiel getSpielById(int id);

    void add(Spiel spiel);

    void update(Spiel spiel);

    void delete(Spiel spiel);

    void deleteById(int id);
}
