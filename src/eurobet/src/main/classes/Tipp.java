package src.main.classes;

import java.io.Serializable;

public class Tipp implements Serializable {
    public enum TippAuswahl {
        MANNSCHAFT_1_GEWINNT,
        MANNSCHAFT_2_GEWINNT,
        UNENTSCHIEDEN,
        OFFEN
    }

    private static int idCounter = 0;
    private final int id;
    private int benutzerId;
    private int spielId;
    private TippAuswahl tipp;
    private int potential;
    private int punkte;

    public Tipp(int benutzerId, int spielId, TippAuswahl tipp, int potential) {
        this.id = idCounter++;
        this.benutzerId = benutzerId;
        this.spielId = spielId;
        this.tipp = tipp;
        this.potential = potential;
        this.punkte = 0;
    }

    public int getId() {
        return id;
    }

    public int getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(int benutzerId) {
        this.benutzerId = benutzerId;
    }

    public int getSpielId() {
        return spielId;
    }

    public void setSpielId(int spielId) {
        this.spielId = spielId;
    }

    public TippAuswahl getTipp() {
        return tipp;
    }

    public void setTipp(TippAuswahl tipp) {
        this.tipp = tipp;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    @Override
    public String toString() {
        return "Tipp{" +
                "id=" + id +
                ", benutzerId=" + benutzerId +
                ", spielId=" + spielId +
                ", tipp=" + tipp +
                ", potential=" + potential +
                ", punkte=" + punkte +
                '}';
    }
}