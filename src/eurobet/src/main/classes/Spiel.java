package src.main.classes;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Spiel {
    private static int idCounter = 0;
    private final int id;
    private ObjectProperty<LocalDateTime> anstosszeit;
    private ObjectProperty<Mannschaft> mannschaft1;
    private ObjectProperty<Mannschaft> mannschaft2;
    private StringProperty spielort;
    private IntegerProperty tore1;
    private IntegerProperty tore2;
    private ObjectProperty<LocalDateTime> endezeit;
    private BooleanProperty spielBeendet;
    private Tipp.TippAuswahl ergebnis;



    public Spiel(LocalDateTime anstosszeit, Mannschaft mannschaft1, Mannschaft mannschaft2, String spielort, int tore1, int tore2, LocalDateTime endezeit, boolean spielBeendet, Tipp.TippAuswahl ergebnis) {
        this.id = idCounter++;
        this.anstosszeit = new SimpleObjectProperty<>(anstosszeit);
        this.mannschaft1 = new SimpleObjectProperty(mannschaft1);
        this.mannschaft2 = new SimpleObjectProperty(mannschaft2);
        this.spielort = new SimpleStringProperty(spielort);
        this.tore1 = new SimpleIntegerProperty(tore1);
        this.tore2 = new SimpleIntegerProperty(tore2);
        this.endezeit = new SimpleObjectProperty<>(endezeit);
        this.spielBeendet = new SimpleBooleanProperty(spielBeendet);
        this.ergebnis = ergebnis;
    }

    public Spiel(LocalDateTime anstosszeit, Mannschaft mannschaft1, Mannschaft mannschaft2, String spielort) {
        this.id = idCounter++;
        this.anstosszeit = new SimpleObjectProperty<>(anstosszeit);
        this.mannschaft1 = new SimpleObjectProperty(mannschaft1);
        this.mannschaft2 = new SimpleObjectProperty(mannschaft2);
        this.spielort = new SimpleStringProperty(spielort);
        this.tore1 = new SimpleIntegerProperty(0); // default value
        this.tore2 = new SimpleIntegerProperty(0); // default value
        this.endezeit = new SimpleObjectProperty<>(null); // default value
        this.spielBeendet = new SimpleBooleanProperty(false); // default value
        this.ergebnis = null;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getAnstosszeit() {
        return anstosszeit.get();
    }

    public ObjectProperty<LocalDateTime> anstosszeitProperty() {
        return anstosszeit;
    }

    public void setAnstosszeit(LocalDateTime anstosszeit) {
        this.anstosszeit.set(anstosszeit);
    }

    public Mannschaft getMannschaft1() {
        return mannschaft1.get();
    }

    public ObjectProperty<Mannschaft> mannschaft1Property() {
        return mannschaft1;
    }

    public void setMannschaft1(Mannschaft mannschaft1) {
        this.mannschaft1.set(mannschaft1);
    }

    public Mannschaft getMannschaft2() {
        return mannschaft2.get();
    }

    public ObjectProperty<Mannschaft> mannschaft2Property() {
        return mannschaft2;
    }

    public void setMannschaft2(Mannschaft mannschaft2) {
        this.mannschaft2.set(mannschaft2);
    }

    public String getSpielort() {
        return spielort.get();
    }

    public StringProperty spielortProperty() {
        return spielort;
    }

    public void setSpielort(String spielort) {
        this.spielort.set(spielort);
    }

    public int getTore1() {
        return tore1.get();
    }

    public IntegerProperty tore1Property() {
        return tore1;
    }

    public void setTore1(int tore1) {
        this.tore1.set(tore1);
    }

    public int getTore2() {
        return tore2.get();
    }

    public IntegerProperty tore2Property() {
        return tore2;
    }

    public void setTore2(int tore2) {
        this.tore2.set(tore2);
    }

    public LocalDateTime getEndezeit() {
        return endezeit.get();
    }

    public ObjectProperty<LocalDateTime> endezeitProperty() {
        return endezeit;
    }

    public void setEndezeit(LocalDateTime endezeit) {
        this.endezeit.set(endezeit);
    }

    public boolean getSpielBeendet() {
        return spielBeendet.get();
    }

    public BooleanProperty spielBeendetProperty() {
        return spielBeendet;
    }

    public void setSpielBeendet(boolean spielBeendet) {
        this.spielBeendet.set(spielBeendet);
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