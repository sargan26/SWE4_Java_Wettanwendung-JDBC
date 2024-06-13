package src.main.classes;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Spiel implements Serializable {
    private static int idCounter = 0;
    private final int id;
    private LocalDateTime anstosszeit;
    private Mannschaft mannschaft1;
    private Mannschaft mannschaft2;
    private String spielort;
    private Integer tore1;
    private Integer tore2;
    private LocalDateTime endezeit;
    private Boolean spielBeendet;
    private Tipp.TippAuswahl ergebnis;



    public Spiel(LocalDateTime anstosszeit, Mannschaft mannschaft1, Mannschaft mannschaft2, String spielort, int tore1, int tore2, LocalDateTime endezeit, boolean spielBeendet, Tipp.TippAuswahl ergebnis) {
        this.id = idCounter++;
        this.anstosszeit = anstosszeit;
        this.mannschaft1 = mannschaft1;
        this.mannschaft2 = mannschaft2;
        this.spielort = spielort;
        this.tore1 = tore1;
        this.tore2 = tore2;
        this.endezeit = endezeit;
        this.spielBeendet = spielBeendet;
        this.ergebnis = ergebnis;
    }

    public Spiel(LocalDateTime anstosszeit, Mannschaft mannschaft1, Mannschaft mannschaft2, String spielort) {
        this.id = idCounter++;
        this.anstosszeit = anstosszeit;
        this.mannschaft1 = mannschaft1;
        this.mannschaft2 = mannschaft2;
        this.spielort = spielort;
        this.tore1 = 0; // default value
        this.tore2 = 0; // default value
        this.endezeit = null; // default value
        this.spielBeendet = false; // default value
        this.ergebnis = null;
    }

        public int getId() {
            return id;
        }

    public LocalDateTime getAnstosszeit() {
        return anstosszeit;
    }

    public void setAnstosszeit(LocalDateTime anstosszeit) {
        this.anstosszeit = anstosszeit;
    }

    public Mannschaft getMannschaft1() {
        return mannschaft1;
    }

    public void setMannschaft1(Mannschaft mannschaft1) {
        this.mannschaft1 = mannschaft1;
    }

    public Mannschaft getMannschaft2() {
        return mannschaft2;
    }

    public void setMannschaft2(Mannschaft mannschaft2) {
        this.mannschaft2 = mannschaft2;
    }

    public String getSpielort() {
        return spielort;
    }

    public void setSpielort(String spielort) {
        this.spielort = spielort;
    }

    public int getTore1() {
        return tore1;
    }

    public void setTore1(int tore1) {
        this.tore1 = tore1;
    }

    public int getTore2() {
        return tore2;
    }

    public void setTore2(int tore2) {
        this.tore2 = tore2;
    }

    public LocalDateTime getEndezeit() {
        return endezeit;
    }

    public void setEndezeit(LocalDateTime endezeit) {
        this.endezeit = endezeit;
    }

    public boolean getSpielBeendet() {
        return spielBeendet;
    }

    public void setSpielBeendet(boolean spielBeendet) {
        this.spielBeendet = spielBeendet;
    }

    public Tipp.TippAuswahl getErgebnis() {
        return ergebnis;
    }

    public void setErgebnis(Tipp.TippAuswahl ergebnis) {
        this.ergebnis = ergebnis;
    }

    @Override
    public String toString() {
        return "Spiel{" +
                "id=" + id +
                "anstosszeit=" + anstosszeit +
                ", mannschaft1=" + mannschaft1 +
                ", mannschaft2=" + mannschaft2 +
                ", spielort=" + spielort +
                ", tore1=" + tore1 +
                ", tore2=" + tore2 +
                ", spielBeendet=" + spielBeendet +
                '}';
    }
}