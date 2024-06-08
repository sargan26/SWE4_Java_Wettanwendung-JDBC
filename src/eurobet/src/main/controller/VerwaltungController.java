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
import src.main.classes.Benutzer;
import src.main.Client;
import src.main.classes.Mannschaft;
import src.main.classes.Spiel;
import src.main.classes.Tipp;
import src.main.server.EuroBetService;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static src.main.Client.getEuroBetService;

public class VerwaltungController {
    // --- Constants ---
    private static final int MESSAGE_DISPLAY_TIME = 1000; // 2 seconds
    private static final int FADE_OUT_TIME = 1000; // 1 second

    // --- Data ---
    private EuroBetService euroBetService;
    private ObservableList<Spiel> spieleList;
    private ObservableList<Mannschaft> mannschaftenList;
    private ObservableList<Benutzer> benutzerList;

    // --- FXML Panes ---
    @FXML
    private AnchorPane spielePane;
    @FXML
    private AnchorPane mannschaftenPane;
    @FXML
    private AnchorPane benutzerPane;

    // --- FXML Elements ---
    // Navigation Buttons
    @FXML
    private Button spieleButton;
    @FXML
    private Button mannschaftenButton;
    @FXML
    private Button benutzerButton;
    @FXML
    private Button logoutButton;

    // Buttons for adding, changing and deleting games, teams and users
    @FXML
    private Button spieleHinzufuegenButton;
    @FXML
    private Button spieleAendernButton;
    @FXML
    private Button spieleLoeschenButton;

    @FXML
    private Button mannschaftenHinzufuegenButton;
    @FXML
    private Button mannschaftenAendernButton;
    @FXML
    private Button mannschaftenLoeschenButton;

    @FXML
    private Button benutzerHinzufuegenButton;
    @FXML
    private Button benutzerAendernButton;
    @FXML
    private Button benutzerLoeschenButton;

    // Games Table
    @FXML
    private TableView<Spiel> gamesTable;
    @FXML
    private TableColumn<Spiel, LocalDateTime> anstosszeitColumn;
    @FXML
    private TableColumn<Spiel, String> mannschaft1Column;
    @FXML
    private TableColumn<Spiel, String> mannschaft2Column;
    @FXML
    private TableColumn<Spiel, String> spielortColumn;
    @FXML
    private TableColumn<Spiel, Integer> tore1Column;
    @FXML
    private TableColumn<Spiel, Integer> tore2Column;
    @FXML
    private TableColumn<Spiel, LocalDateTime> endezeitColumn;
    @FXML
    private TableColumn<Spiel, Boolean> spielBeendetColumn;

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
    private TextField spielortField;
    @FXML
    private TextField tore1Field;
    @FXML
    private TextField tore2Field;
    @FXML
    private DatePicker enddatumField;
    @FXML
    private TextField endzeitField;
    @FXML
    private javafx.scene.control.CheckBox spielBeendetField;

    // Mannschaften Table
    @FXML
    private TableView<Mannschaft> mannschaftenTable;
    @FXML
    private TableColumn<Mannschaft, String> mannschaftenNameColumn;
    @FXML
    private TableColumn<Mannschaft, Double> mannschaftenStaerkeColumn;

    // Text fields for Mannschaften table
    @FXML
    private TextField mannschaftenNameField;
    @FXML
    private TextField mannschaftenStaerkeField;

    // Benutzer Table
    @FXML
    private TableView<Benutzer> benutzerTable;
    @FXML
    private TableColumn<Benutzer, String> benutzerNameColumn;
    @FXML
    private TableColumn<Benutzer, String> benutzerPasswortColumn;
    @FXML
    private TableColumn<Benutzer, Benutzer.BenutzerRolle> benutzerRolleColumn;
    @FXML
    private TableColumn<Benutzer, Boolean> benutzerGesperrtColumn;

    // Text fields for Benutzer table
    @FXML
    private TextField benutzerNameField;
    @FXML
    private TextField benutzerPasswortField;
    @FXML
    private ComboBox<Benutzer.BenutzerRolle> benutzerRolleField;
    @FXML
    private CheckBox benutzerGesperrtField;

    @FXML
    private Label mannschaftenMsg;
    @FXML
    private Label benutzerMsg;
    @FXML
    private Label spieleMsg;


    @FXML
    public void initialize() {
        // Data
        euroBetService = getEuroBetService();

        try {
            spieleList = FXCollections.observableArrayList(getEuroBetService().getAllSpiele());
            mannschaftenList = FXCollections.observableArrayList(getEuroBetService().getAllMannschaften());
            benutzerList = FXCollections.observableArrayList(getEuroBetService().getAllBenutzer());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        benutzerRolleField.setItems(FXCollections.observableArrayList(Benutzer.BenutzerRolle.values()));
        mannschaft1Field.setItems(mannschaftenList);
        mannschaft2Field.setItems(mannschaftenList);

        // Set up the columns in the table
        anstosszeitColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnstosszeit()));
        // Formats the date and time in the "Anstosszeit" column
        setDateTimeCellFactory(anstosszeitColumn);
        mannschaft1Column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMannschaft1().getName()));
        mannschaft2Column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMannschaft2().getName()));
        spielortColumn.setCellValueFactory(new PropertyValueFactory<>("spielort"));
        tore1Column.setCellValueFactory(new PropertyValueFactory<>("tore1"));
        tore2Column.setCellValueFactory(new PropertyValueFactory<>("tore2"));
        spielBeendetColumn.setCellValueFactory(new PropertyValueFactory<>("spielBeendet"));
        endezeitColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndezeit()));
        setDateTimeCellFactory(endezeitColumn);

        mannschaftenNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mannschaftenStaerkeColumn.setCellValueFactory(new PropertyValueFactory<>("strength"));

        benutzerNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        benutzerPasswortColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        benutzerRolleColumn.setCellValueFactory(new PropertyValueFactory<>("rolle"));
        benutzerGesperrtColumn.setCellValueFactory(new PropertyValueFactory<>("gesperrt"));



        ObservableList<String> mannschaftenNamen = FXCollections.observableArrayList();
        ObservableList<Mannschaft> mannschaften = mannschaftenList;
        for (Mannschaft mannschaft : mannschaften) {
            mannschaftenNamen.add(mannschaft.getName());
        }
        mannschaft1Field.setItems(mannschaftenNamen);
        mannschaft2Field.setItems(mannschaftenNamen);

        benutzerTable.setItems(benutzerList);
        gamesTable.setItems(spieleList);
        mannschaftenTable.setItems(mannschaftenList);
        addGamesTableListener();
        addMannschaftenTableListener();
        addBenutzerTableListener();
        showSpielePane();
    }

    @FXML
    public void showSpielePane() {
        spielePane.setVisible(true);
        mannschaftenPane.setVisible(false);
        benutzerPane.setVisible(false);
    }

    @FXML
    public void showMannschaftenPane() {
        spielePane.setVisible(false);
        mannschaftenPane.setVisible(true);
        benutzerPane.setVisible(false);
    }

    @FXML
    public void showBenutzerPane() {
        spielePane.setVisible(false);
        mannschaftenPane.setVisible(false);
        benutzerPane.setVisible(true);
    }

    private void addGamesTableListener() {
        gamesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Spiel selectedGame = gamesTable.getSelectionModel().getSelectedItem();
                LocalDate date = selectedGame.getAnstosszeit().toLocalDate();
                LocalTime time = selectedGame.getAnstosszeit().toLocalTime();
                anstossdatumField.setValue(date);
                anstosszeitField.setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
                mannschaft1Field.setValue(selectedGame.getMannschaft1().getName());
                mannschaft2Field.setValue(selectedGame.getMannschaft2().getName());
                spielortField.setText(selectedGame.getSpielort());
                tore1Field.setText(String.valueOf(selectedGame.getTore1()));
                tore2Field.setText(String.valueOf(selectedGame.getTore2()));
                if (selectedGame.getEndezeit() != null) {
                    date = selectedGame.getEndezeit().toLocalDate();
                    time = selectedGame.getEndezeit().toLocalTime();
                    enddatumField.setValue(date);
                    endzeitField.setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
                } else {
                    enddatumField.setValue(null);
                    endzeitField.setText("");
                }
                spielBeendetField.setSelected(selectedGame.getSpielBeendet());
            }
        });
    }

    private void addMannschaftenTableListener() {
        mannschaftenTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Mannschaft selectedTeam = mannschaftenTable.getSelectionModel().getSelectedItem();
                mannschaftenNameField.setText(selectedTeam.getName());
                mannschaftenStaerkeField.setText(String.valueOf(selectedTeam.getStrength()));
            }
        });
    }

    private void addBenutzerTableListener() {
        benutzerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Benutzer selectedUser = benutzerTable.getSelectionModel().getSelectedItem();
                benutzerNameField.setText(selectedUser.getUsername());
                benutzerPasswortField.setText(selectedUser.getPassword());
                benutzerRolleField.setValue(selectedUser.getRolle());
                benutzerGesperrtField.setSelected(selectedUser.isGesperrt());
            }
        });
    }

    private <T> void setDateTimeCellFactory(TableColumn<Spiel, LocalDateTime> column) {
        column.setCellFactory(col -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return new TableCell<Spiel, LocalDateTime>() {
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
            Client.showLoginScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddMannschaft() {
        String name = mannschaftenNameField.getText();
        double strength = Double.parseDouble(mannschaftenStaerkeField.getText());

        // Check if team exists
        for (Mannschaft mannschaft : mannschaftenList) {
            if (mannschaft.getName().equals(name)) {
                mannschaftenMsg.setStyle("-fx-text-fill: red;");
                showMessage(mannschaftenMsg, "Mannschaft existiert bereits.");
                return;
            }
        }

        // Add team
        Mannschaft newMannschaft = new Mannschaft(name, strength);
        mannschaftenList.add(newMannschaft);
        mannschaftenTable.setItems(mannschaftenList);
        mannschaftenMsg.setStyle("-fx-text-fill: green;");
        showMessage(mannschaftenMsg, "Hinzufügen erfolgreich.");
    }

    @FXML
    public void handleUpdateMannschaft() {
        // Get the selected team
        String name = mannschaftenNameField.getText();
        Mannschaft selectedMannschaft = null;

        for (Mannschaft mannschaft : mannschaftenList) {
            if (mannschaft.getName().equals(name)) {
                selectedMannschaft = mannschaft;
                break;
            }
        }

        if (selectedMannschaft != null) {
            // Update team strength
            double newStrength = Double.parseDouble(mannschaftenStaerkeField.getText());
            selectedMannschaft.setStrength(newStrength);
            mannschaftenTable.refresh();
            mannschaftenMsg.setStyle("-fx-text-fill: green;");
            showMessage(mannschaftenMsg, "Update erfolgreich.");
        } else {
            mannschaftenMsg.setStyle("-fx-text-fill: red;");
            showMessage(mannschaftenMsg, "Ausgewählte Mannschaft nicht gefunden.");
        }
    }

    @FXML
    public void handleDeleteMannschaft() {
        // Get the selected team
        String name = mannschaftenNameField.getText();
        Mannschaft selectedMannschaft = null;

        for (Mannschaft mannschaft : mannschaftenList) {
            if (mannschaft.getName().equals(name)) {
                selectedMannschaft = mannschaft;
                break;
            }
        }

        if (selectedMannschaft != null) {
            // Remove the team from the list
            mannschaftenList.remove(selectedMannschaft);
            mannschaftenTable.setItems(mannschaftenList);
            mannschaftenMsg.setStyle("-fx-text-fill: green;");
            showMessage(mannschaftenMsg, "Löschen erfolgreich.");
        } else {
            mannschaftenMsg.setStyle("-fx-text-fill: red;");
            showMessage(mannschaftenMsg, "Ausgewählte Mannschaft nicht gefunden.");
        }
    }

    @FXML
    public void handleAddSpiel() {
        String mannschaft1Name = mannschaft1Field.getValue().toString();
        String mannschaft2Name = mannschaft2Field.getValue().toString();
        String spielort = spielortField.getText();
        Mannschaft mannschaft1 = getMannschaftByName(mannschaft1Name);
        Mannschaft mannschaft2 = getMannschaftByName(mannschaft2Name);

        if (mannschaft1 == null || mannschaft2 == null) {
            spieleMsg.setStyle("-fx-text-fill: red;");
            showMessage(spieleMsg, "Mannschaft nicht gefunden.");
            return;
        }

        LocalDateTime anstosszeit = LocalDateTime.of(anstossdatumField.getValue(), LocalTime.parse(anstosszeitField.getText(), DateTimeFormatter.ofPattern("HH:mm")));

        if (spielBeendetField.isSelected()) {
            try {
                int tore1 = Integer.parseInt(tore1Field.getText());
                int tore2 = Integer.parseInt(tore2Field.getText());
                LocalDateTime endezeit = LocalDateTime.of(enddatumField.getValue(), LocalTime.parse(endzeitField.getText(), DateTimeFormatter.ofPattern("HH:mm")));

                Tipp.TippAuswahl ergebnis;
                if (tore1 > tore2) {
                    ergebnis = Tipp.TippAuswahl.MANNSCHAFT_1_GEWINNT;
                } else if (tore1 < tore2) {
                    ergebnis = Tipp.TippAuswahl.MANNSCHAFT_2_GEWINNT;
                } else {
                    ergebnis = Tipp.TippAuswahl.UNENTSCHIEDEN;
                }
                // Add game
                Spiel newSpiel = new Spiel(anstosszeit, mannschaft1, mannschaft2, spielort, tore1, tore2, endezeit, true, ergebnis);
                ObservableList<Spiel> spiele = spieleList;
                spiele.add(newSpiel);
                gamesTable.setItems(spiele);
                spieleMsg.setStyle("-fx-text-fill: green;");
                showMessage(spieleMsg, "Spiel erfolgreich hinzugefügt.");
            } catch (NumberFormatException e) {
                spieleMsg.setStyle("-fx-text-fill: red;");
                showMessage(spieleMsg, "Ungültige Eingabe für Tore.");
            } catch (DateTimeParseException e) {
                spieleMsg.setStyle("-fx-text-fill: red;");
                showMessage(spieleMsg, "Ungültige Eingabe für Datum oder Zeit.");
            } catch (Exception e) {
                spieleMsg.setStyle("-fx-text-fill: red;");
                showMessage(spieleMsg, "Fehler beim Hinzufügen des Spiels.");
            }
        } else {
            // Add game
            Spiel newSpiel = new Spiel(anstosszeit, mannschaft1, mannschaft2, spielort);
            spieleList.add(newSpiel);
            gamesTable.setItems(spieleList);
            spieleMsg.setStyle("-fx-text-fill: green;");
            showMessage(spieleMsg, "Spiel erfolgreich hinzugefügt.");
        }
    }

    @FXML
    public void handleUpdateSpiel() {
        // Get the selected game
        Spiel selectedSpiel = gamesTable.getSelectionModel().getSelectedItem();

        if (selectedSpiel != null) {
            // Update game details
            LocalDateTime anstosszeit = LocalDateTime.of(anstossdatumField.getValue(), LocalTime.parse(anstosszeitField.getText(), DateTimeFormatter.ofPattern("HH:mm")));
            Mannschaft mannschaft1 = getMannschaftByName(mannschaft1Field.getValue().toString());
            Mannschaft mannschaft2 = getMannschaftByName(mannschaft2Field.getValue().toString());
            String spielort = spielortField.getText();
            int tore1 = Integer.parseInt(tore1Field.getText());
            int tore2 = Integer.parseInt(tore2Field.getText());
            LocalDateTime endezeit = LocalDateTime.of(enddatumField.getValue(), LocalTime.parse(endzeitField.getText(), DateTimeFormatter.ofPattern("HH:mm")));
            boolean spielBeendet = spielBeendetField.isSelected();

            selectedSpiel.setAnstosszeit(anstosszeit);
            selectedSpiel.setMannschaft1(mannschaft1);
            selectedSpiel.setMannschaft2(mannschaft2);
            selectedSpiel.setSpielort(spielort);
            selectedSpiel.setTore1(tore1);
            selectedSpiel.setTore2(tore2);
            selectedSpiel.setEndezeit(endezeit);
            selectedSpiel.setSpielBeendet(spielBeendet);

            gamesTable.refresh();
            spieleMsg.setStyle("-fx-text-fill: green;");
            showMessage(spieleMsg, "Spiel erfolgreich geändert.");
        }
        spieleMsg.setStyle("-fx-text-fill: red;");
        showMessage(spieleMsg, "Spiel nicht gefunden.");
    }

    @FXML
    public void handleDeleteSpiel() {
        // Get the selected game
        Spiel selectedSpiel = gamesTable.getSelectionModel().getSelectedItem();

        if (selectedSpiel != null) {
            // Remove the game from the list
            spieleList.remove(selectedSpiel);
            gamesTable.setItems(spieleList);
            spieleMsg.setStyle("-fx-text-fill: green;");
            showMessage(spieleMsg, "Spiel erfolgreich gelöscht.");
        }
        spieleMsg.setStyle("-fx-text-fill: red;");
        showMessage(spieleMsg, "Spiel nicht gefunden.");
    }

    private Mannschaft getMannschaftByName(String name) {
        for (Mannschaft mannschaft : mannschaftenList) {
            if (mannschaft.getName().equals(name)) {
                return mannschaft;
            }
        }
        return null;
    }

    @FXML
    public void handleAddBenutzer() {
        String username = benutzerNameField.getText();
        String password = benutzerPasswortField.getText();
        Benutzer.BenutzerRolle rolle = benutzerRolleField.getValue();

        // Check if user exists
        for (Benutzer benutzer : benutzerList) {
            if (benutzer.getUsername().equals(username)) {
                benutzerMsg.setStyle("-fx-text-fill: red;");
                showMessage(benutzerMsg, "Benutzer existiert bereits.");
                return;
            }
        }

        // Add user
        Benutzer newBenutzer = new Benutzer(username, password, rolle);
        newBenutzer.setRolle(rolle);
        benutzerList.add(newBenutzer);
        benutzerTable.setItems(benutzerList);
        benutzerMsg.setStyle("-fx-text-fill: green;");
        showMessage(benutzerMsg, "Benutzer erfolgreich hinzugefügt.");
    }

    @FXML
    public void handleUpdateBenutzer() {
        // Get the selected user
        String username = benutzerNameField.getText();
        Benutzer selectedBenutzer = getBenutzerByName(username);

        if (selectedBenutzer != null) {
            // Update user details
            String password = benutzerPasswortField.getText();
            Benutzer.BenutzerRolle rolle = benutzerRolleField.getValue();
            Boolean gesperrt = benutzerGesperrtField.isSelected();

            selectedBenutzer.setPassword(password);
            selectedBenutzer.setRolle(rolle);
            selectedBenutzer.setGesperrt(gesperrt);

            benutzerTable.refresh();
            benutzerMsg.setStyle("-fx-text-fill: green;");
            showMessage(benutzerMsg, "Benutzer erfolgreich aktualisiert.");
        } else {
            benutzerMsg.setStyle("-fx-text-fill: red;");
            showMessage(benutzerMsg, "Ausgewählter Benutzer nicht gefunden.");
        }
    }

    @FXML
    public void handleDeleteBenutzer() {
        // Get the selected user
        String username = benutzerNameField.getText();
        Benutzer selectedBenutzer = getBenutzerByName(username);

        if (selectedBenutzer != null) {
            // Remove the user from the list
            benutzerList.remove(selectedBenutzer);
            benutzerTable.setItems(benutzerList);
            benutzerMsg.setStyle("-fx-text-fill: green;");
            showMessage(benutzerMsg, "Benutzer erfolgreich gelöscht.");
        } else {
            benutzerMsg.setStyle("-fx-text-fill: red;");
            showMessage(benutzerMsg, "Ausgewählter Benutzer nicht gefunden.");
        }
    }


    private Benutzer getBenutzerByName(String username) {
        for (Benutzer benutzer : benutzerList) {
            if (benutzer.getUsername().equals(username)) {
                return benutzer;
            }
        }
        return null;
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