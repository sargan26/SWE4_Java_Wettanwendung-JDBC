package src.main.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.main.classes.Benutzer;
import src.main.classes.Mannschaft;
import src.main.classes.Spiel;
import src.main.classes.Tipp;

import java.time.LocalDateTime;

public class DatenManager {
    private SpieleDao spieleDao;
    private MannschaftenDao mannschaftenDao;
    private BenutzerDao benutzerDao;
    private TippsDao tippsDao;
    private Benutzer eingeloggterBenutzer;

    public DatenManager() {
        // Initialisieren Sie die ObservableLists
        ObservableList<Spiel> spiele = FXCollections.observableArrayList();
        ObservableList<Mannschaft> mannschaften = FXCollections.observableArrayList();
        ObservableList<Benutzer> benutzer = FXCollections.observableArrayList();
        ObservableList<Tipp> tipps = FXCollections.observableArrayList();

        // Initialisieren Sie die DAOs
        this.spieleDao = new SpieleDao(spiele);
        this.mannschaftenDao = new MannschaftenDao(mannschaften);
        this.benutzerDao = new BenutzerDao(benutzer);
        this.tippsDao = new TippsDao(tipps);
    }

    public void loadExampleMannschaften() {
        mannschaftenDao.add(new Mannschaft("Deutschland", 0.8));
        mannschaftenDao.add(new Mannschaft("England", 0.7));
        mannschaftenDao.add(new Mannschaft("Italien", 0.6));
        mannschaftenDao.add(new Mannschaft("Österreich", 0.5));
        mannschaftenDao.add(new Mannschaft("Spanien", 0.9));
        mannschaftenDao.add(new Mannschaft("Frankreich", 0.4));
    }

    public void loadExampleSpiele() {
        loadExampleMannschaften();
        ObservableList<Mannschaft> mannschaften = mannschaftenDao.getAll();

        // Should be within time frame to vote
        spieleDao.add(new Spiel(LocalDateTime.now().minusMinutes(20), mannschaften.get(3), mannschaften.get(2), "Warschau", 3, 2, LocalDateTime.now().plusMinutes(70), false, Tipp.TippAuswahl.OFFEN));
        spieleDao.add(new Spiel(LocalDateTime.now().minusMinutes(75), mannschaften.get(0), mannschaften.get(1), "Madrid", 3, 2, LocalDateTime.now().plusMinutes(15), false, Tipp.TippAuswahl.OFFEN));
        // Should not be votable
        spieleDao.add(new Spiel(LocalDateTime.now().minusMinutes(85), mannschaften.get(0), mannschaften.get(1), "Madrid", 3, 2, LocalDateTime.now().plusMinutes(5), false, Tipp.TippAuswahl.OFFEN));
        spieleDao.add(new Spiel(LocalDateTime.now().minusDays(2), mannschaften.get(0), mannschaften.get(4), "Berlin", 3, 2, LocalDateTime.now().minusDays(2).plusMinutes(90), true, Tipp.TippAuswahl.MANNSCHAFT_1_GEWINNT));

        spieleDao.add(new Spiel(LocalDateTime.now().plusDays(1), mannschaften.get(2), mannschaften.get(3), "Wien"));
        spieleDao.add(new Spiel(LocalDateTime.now().plusDays(3), mannschaften.get(5), mannschaften.get(2), "Rom"));
    }

    public void loadExampleBenutzer() {
        benutzerDao.add(new Benutzer("admin", "admin", Benutzer.BenutzerRolle.ADMIN));
        benutzerDao.add(new Benutzer("user", "user", Benutzer.BenutzerRolle.USER));
        benutzerDao.add(new Benutzer("dagobert duck", "user", Benutzer.BenutzerRolle.USER));
        benutzerDao.getBenutzerByName("dagobert duck").setPunkte(100);
    }

    public void loadExampleTipps() {
        ObservableList<Spiel> spiele = spieleDao.getAll();
        ObservableList<Benutzer> benutzer = benutzerDao.getAll();

        tippsDao.add(new Tipp(benutzer.get(1).getId(), spiele.get(2).getId(), Tipp.TippAuswahl.MANNSCHAFT_1_GEWINNT, 66));
        tippsDao.add(new Tipp(benutzer.get(1).getId(), spiele.get(3).getId(), Tipp.TippAuswahl.UNENTSCHIEDEN, 33));
    }

    public void updateScores() {
    for (Benutzer benutzer : benutzerDao.getAll()) {
        benutzer.setPunkte(0);
    }
    for (Tipp tipp : tippsDao.getAll()) {
        Spiel spiel = spieleDao.getSpielById(tipp.getSpielId());
        if (spiel.getSpielBeendet() && tipp.getTipp().equals(spiel.getErgebnis())) {
            Benutzer benutzer = benutzerDao.getBenutzerById(tipp.getBenutzerId());
            benutzer.setPunkte(benutzer.getPunkte() + 100);
        }
    }
}

    public SpieleDao getSpieleDao() {
        return spieleDao;
    }

    public MannschaftenDao getMannschaftenDao() {
        return mannschaftenDao;
    }

    public BenutzerDao getBenutzerDao() {
        return benutzerDao;
    }

    public TippsDao getTippsDao() {
        return tippsDao;
    }

    public Benutzer getEingeloggterBenutzer() {
        return eingeloggterBenutzer;
    }

    public void setEingeloggterBenutzer(Benutzer eingeloggterBenutzer) {
        this.eingeloggterBenutzer = eingeloggterBenutzer;
    }
}
