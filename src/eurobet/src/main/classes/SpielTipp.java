package src.main.classes;

import java.io.Serializable;

public class SpielTipp implements Serializable  {
    private Spiel spiel;
    private Tipp tipp;

    public SpielTipp(Spiel spiel, Tipp tipp) {
        this.spiel = spiel;
        this.tipp = tipp;
    }

    public SpielTipp(Spiel spiel) {
        this.spiel = spiel;
        this.tipp = null;
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    public Tipp getTipp() {
        return tipp;
    }

    public void setTipp(Tipp tipp) {
        this.tipp = tipp;
    }
}