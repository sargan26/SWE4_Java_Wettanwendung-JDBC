package src.main.controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import src.main.*;
import src.main.classes.*;
import src.main.data.BenutzerDao;
import src.main.data.DatenManager;
import src.main.data.SpieleDao;
import src.main.data.TippsDao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WettanwendungController {
    // --- Constants ---
    private static final int MESSAGE_DISPLAY_TIME = 1000; // 2 seconds
    private static final int FADE_OUT_TIME = 1000; // 1 second

    // --- Data ---
    private DatenManager datenManager;
    private SpieleDao spieleDao;
    private TippsDao tippsDao;
    private BenutzerDao benutzerDao;

    private ObservableList<Spiel> spieleList;
    private ObservableList<Tipp> tippsList;
    private ObservableList<SpielTipp> spielTippList;
    private ObservableList<Benutzer> benutzerList;
    private Benutzer eingeloggterBenutzer;

    // --- FXML Panes ---
    @FXML
    private AnchorPane spielePane;
    @FXML
    private AnchorPane highscoresPane;

    // --- FXML Elements ---
    // Navigation Buttons
    @FXML
    private Button spieleButton;
    @FXML
    private Button highscoresButton;
    @FXML
    private Button logoutButton;

    // Buttons for adding, changing and deleting games, teams and users
    @FXML
    private Button spieleHinzufuegenButton;
    @FXML
    private Button spieleAendernButton;

    // Games Table
    @FXML
    private TableView<SpielTipp> gamesTable;
    @FXML
    private TableColumn<SpielTipp, LocalDateTime> anstosszeitColumn;
    @FXML
    private TableColumn<SpielTipp, String> mannschaft1Column;
    @FXML
    private TableColumn<SpielTipp, String> mannschaft2Column;
    @FXML
    private TableColumn<SpielTipp, String> spielortColumn;
    @FXML
    private TableColumn<SpielTipp, Integer> tore1Column;
    @FXML
    private TableColumn<SpielTipp, Integer> tore2Column;
    @FXML
    private TableColumn<SpielTipp, LocalDateTime> endezeitColumn;
    @FXML
    private TableColumn<SpielTipp, Boolean> spielBeendetColumn;
    @FXML
    private TableColumn<SpielTipp, Tipp.TippAuswahl> spieleTippColumn;
    @FXML
    private TableColumn<SpielTipp, Integer> spielePotentialColumn;
    @FXML
    private TableColumn<SpielTipp, Integer> spielePunkteColumn;

    // Text fields for game table
    @FXML
    private DatePicker anstossdatumField;
    @FXML
    private TextField anstosszeitField;
    @FXML
    private ComboBox mannschaft1Field;
    @FXML
    private ComboBox mannschaft2Field;
    @FXML
    private ComboBox tippField;
    @FXML
    private TextField potentialField;
    @FXML
    private TextField punkteField;

    // Highscores Table
    @FXML
    private TableView<Benutzer> highscoresTable;
    @FXML
    private TableColumn<Benutzer, String> highscoresBenutzerColumn;
    @FXML
    private TableColumn<Benutzer, Integer> highscoresPunkteColumn;

    @FXML
    private Label spieleMsg;

    @FXML
    public void initialize() {
        // Data
        datenManager = Launcher.getDatenManager();
        eingeloggterBenutzer = datenManager.getEingeloggterBenutzer();
        tippsDao = datenManager.getTippsDao();
        spieleDao = datenManager.getSpieleDao();
        benutzerDao = datenManager.getBenutzerDao();

        spieleList = spieleDao.getAll();
        tippsList = tippsDao.getAll();
        benutzerList = benutzerDao.getAll();
        eingeloggterBenutzer = benutzerList.get(0); // for testing TODO: remove

        spielTippList = createSpielTippList();
        ObservableList<Tipp.TippAuswahl> tippAuswahlList = FXCollections.observableArrayList(Tipp.TippAuswahl.values());
        tippAuswahlList.remove(Tipp.TippAuswahl.OFFEN);
        tippField.setItems(tippAuswahlList);
        mannschaft1Field.setItems(FXCollections.observableArrayList(datenManager.getMannschaftenDao().getAll()));
        mannschaft2Field.setItems(FXCollections.observableArrayList(datenManager.getMannschaftenDao().getAll()));

        // Set up the columns in the table
        anstosszeitColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSpiel().getAnstosszeit()));
        setDateTimeCellFactory(anstosszeitColumn);
        mannschaft1Column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSpiel().getMannschaft1().getName()));
        mannschaft2Column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSpiel().getMannschaft2().getName()));
        spieleTippColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTipp() != null) {
                return new SimpleObjectProperty<>(cellData.getValue().getTipp().getTipp());
            } else {
                return new SimpleObjectProperty<>();
            }
        });
        spielePotentialColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTipp() != null) {
                return new SimpleObjectProperty<>(cellData.getValue().getTipp().getPotential());
            } else {
                return new SimpleObjectProperty<>();
            }
        });
        spielePunkteColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTipp() != null) {
                return new SimpleObjectProperty<>(cellData.getValue().getTipp().getPunkte());
            } else {
                return new SimpleObjectProperty<>();
            }
        });
        gamesTable.setItems(spielTippList);

        // Set up the columns in the highscores table
        highscoresBenutzerColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUsername()));
        highscoresPunkteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPunkte()));
        highscoresTable.setItems(benutzerList);

        for (Spiel spiel : datenManager.getSpieleDao().getAll()) {
            spiel.spielBeendetProperty().addListener((observable, oldValue, newValue) -> {
                if (Boolean.TRUE.equals(newValue)) {
                    datenManager.updateScores();
                }
            });
        }

        addGamesTableListener();
        showSpielePane();
    }

    private ObservableList<SpielTipp> createSpielTippList() {
        ObservableList<SpielTipp> spielTippList = FXCollections.observableArrayList();
        for (Spiel spiel : spieleList) {
            SpielTipp spielTipp = new SpielTipp(spiel);
            for (Tipp tipp : tippsList) {
                if (tipp.getSpielId() == spiel.getId()) {
                    spielTipp.setTipp(tipp);
                    break;
                }
            }
            spielTippList.add(spielTipp);
        }
        return spielTippList;
    }

    @FXML
    public void showSpielePane() {
        spielePane.setVisible(true);
        highscoresPane.setVisible(false);
    }

    @FXML
    public void showHighScoresPane() {
        spielePane.setVisible(false);
        highscoresPane.setVisible(true);
    }

    private void addGamesTableListener() {
        gamesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SpielTipp selectedGame = gamesTable.getSelectionModel().getSelectedItem();
                Spiel selectedSpiel = selectedGame.getSpiel();
                Tipp selectedTipp = selectedGame.getTipp();

                LocalDate date = selectedSpiel.getAnstosszeit().toLocalDate();
                LocalTime time = selectedSpiel.getAnstosszeit().toLocalTime();
                anstossdatumField.setValue(date);
                anstosszeitField.setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
                mannschaft1Field.setValue(selectedSpiel.getMannschaft1().getName());
                mannschaft2Field.setValue(selectedSpiel.getMannschaft2().getName());

                if (selectedTipp != null) {
                    tippField.setValue(selectedTipp.getTipp().toString());
                    potentialField.setText(String.valueOf(selectedTipp.getPotential()));
                    punkteField.setText(String.valueOf(selectedTipp.getPunkte()));
                } else {
                    tippField.setValue("");
                    potentialField.setText("");
                    punkteField.setText("");
                }
            }
        });
    }

    private <T> void setDateTimeCellFactory(TableColumn<SpielTipp, LocalDateTime> column) {
        column.setCellFactory(col -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return new TableCell<SpielTipp, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(formatter.format(item));
                    } else {
                        setText(null);
                    }
                }
            };
        });
    }

    @FXML
    public void handleLogout() {
        try {
            Launcher.showLoginScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private int calculatePotential(Mannschaft mannschaft1, Mannschaft mannschaft2, Tipp.TippAuswahl tipp, LocalDateTime anstosszeit) {
    double staerke1 = mannschaft1.getStrength();
    double staerke2 = mannschaft2.getStrength();
    double potential = 0;

    if (tipp == Tipp.TippAuswahl.MANNSCHAFT_1_GEWINNT) {
        potential = (staerke2 / (staerke1 + staerke2)) * 100;
    } else if (tipp == Tipp.TippAuswahl.MANNSCHAFT_2_GEWINNT) {
        potential = (staerke1 / (staerke1 + staerke2)) * 100;
    } else if (tipp == Tipp.TippAuswahl.UNENTSCHIEDEN) {
        double staerkeDifferenz = Math.abs(staerke1 - staerke2);
        potential = (staerkeDifferenz / (staerke1 + staerke2)) * 100;
    }

    // Apply time factor
    LocalDateTime now = LocalDateTime.now();
    long minutesSinceStart = java.time.Duration.between(anstosszeit, now).toMinutes();
    double k = 0.02; // Adjust this value to change the speed of the decrease
    if (minutesSinceStart >= 0) {
        potential = potential * Math.exp(-k * minutesSinceStart);
    }

    return (int) Math.round(potential);
}

    @FXML
    public void handleAddSpielTipp() {
        // Get the selected game
        SpielTipp selectedSpielTipp = gamesTable.getSelectionModel().getSelectedItem();
        Spiel selectedSpiel = selectedSpielTipp.getSpiel();

        if (selectedSpiel != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime deadline = selectedSpiel.getAnstosszeit().plusMinutes(80);
            if (now.isBefore(deadline)) {
                try {
                    // Create new Tipp
                    Tipp.TippAuswahl tippAuswahl = (Tipp.TippAuswahl) tippField.getValue();
                    LocalDateTime anstosszeit = LocalDateTime.of(anstossdatumField.getValue(), LocalTime.parse(anstosszeitField.getText()));
                    int potential = calculatePotential(selectedSpiel.getMannschaft1(), selectedSpiel.getMannschaft2(), tippAuswahl, anstosszeit);
                    Tipp newTipp = new Tipp(eingeloggterBenutzer.getId(), selectedSpiel.getId(), (Tipp.TippAuswahl) tippField.getValue(), potential);
                    // Add to list and update table
                    selectedSpielTipp.setTipp(newTipp);
                    gamesTable.refresh();

                    spieleMsg.setStyle("-fx-text-fill: green;");
                    showMessage(spieleMsg, "SpielTipp erfolgreich hinzugefügt.");
                } catch (NumberFormatException e) {
                    spieleMsg.setStyle("-fx-text-fill: red;");
                    showMessage(spieleMsg, "Ungültige Eingabe für Tipp.");
                } catch (Exception e) {
                    spieleMsg.setStyle("-fx-text-fill: red;");
                    showMessage(spieleMsg, "Fehler beim Hinzufügen des SpielTipps.");
                }
            } else {
                spieleMsg.setStyle("-fx-text-fill: red;");
                showMessage(spieleMsg, "Spiel bereits beendet oder mehr als 80 Minuten vergangen.");
            }
        } else {
            spieleMsg.setStyle("-fx-text-fill: red;");
            showMessage(spieleMsg, "Kein Spiel ausgewählt.");
        }
    }

    @FXML
    public void handleUpdateSpielTipp() {
        // Get the selected game
        SpielTipp selectedSpielTipp = gamesTable.getSelectionModel().getSelectedItem();

        if (selectedSpielTipp != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime deadline = selectedSpielTipp.getSpiel().getAnstosszeit().plusMinutes(80);
            if (now.isBefore(deadline)) {
                try {
                    // Update Tipp
                    Tipp selectedTipp = selectedSpielTipp.getTipp();
                    selectedTipp.setTipp((Tipp.TippAuswahl) tippField.getValue());

                    // Update table
                    gamesTable.refresh();

                    spieleMsg.setStyle("-fx-text-fill: green;");
                    showMessage(spieleMsg, "SpielTipp erfolgreich geändert.");
                } catch (NumberFormatException e) {
                    spieleMsg.setStyle("-fx-text-fill: red;");
                    showMessage(spieleMsg, "Ungültige Eingabe für Tipp.");
                } catch (Exception e) {
                    spieleMsg.setStyle("-fx-text-fill: red;");
                    showMessage(spieleMsg, "Fehler beim Ändern des SpielTipps.");
                }
            } else {
                spieleMsg.setStyle("-fx-text-fill: red;");
                showMessage(spieleMsg, "Tipp kann nicht geändert werden, da das Spiel bereits gestartet hat oder mehr als 80 Minuten im Spiel sind.");
            }
        } else {
            spieleMsg.setStyle("-fx-text-fill: red;");
            showMessage(spieleMsg, "Kein SpielTipp ausgewählt.");
        }
    }

    private void showMessage(Label label, String message) {
        label.setText(message);
        label.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.millis(MESSAGE_DISPLAY_TIME));
        FadeTransition fadeOut = new FadeTransition(Duration.millis(FADE_OUT_TIME), label);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition seqTransition = new SequentialTransition(pause, fadeOut);
        seqTransition.setOnFinished(e -> label.setVisible(false));
        seqTransition.play();
    }

}