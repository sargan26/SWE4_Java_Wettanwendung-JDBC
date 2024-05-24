package src.main;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VerwaltungController {
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
    private TextField anstosszeitField;
    @FXML
    private TextField mannschaft1Field;
    @FXML
    private TextField mannschaft2Field;
    @FXML
    private TextField spielortField;
    @FXML
    private TextField tore1Field;
    @FXML
    private TextField tore2Field;
    @FXML
    private TextField endzeitField;
    @FXML
    private javafx.scene.control.CheckBox spielBeendetField;

    @FXML
    public void initialize() {
        // Set up the columns in the table
        anstosszeitColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnstosszeit()));
        // Formats the date and time in the "Anstosszeit" column
        setDateTimeCellFactory(anstosszeitColumn);
        mannschaft1Column.setCellValueFactory(new PropertyValueFactory<>("mannschaft1"));
        mannschaft2Column.setCellValueFactory(new PropertyValueFactory<>("mannschaft2"));
        spielortColumn.setCellValueFactory(new PropertyValueFactory<>("spielort"));
        tore1Column.setCellValueFactory(new PropertyValueFactory<>("tore1"));
        tore2Column.setCellValueFactory(new PropertyValueFactory<>("tore2"));
        spielBeendetColumn.setCellValueFactory(new PropertyValueFactory<>("spielBeendet"));
        endezeitColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndezeit()));
        setDateTimeCellFactory(endezeitColumn);

        ObservableList<Spiel> spiele = FXCollections.observableArrayList(
                new Spiel(LocalDateTime.now(), "Mannschaft 1", "Mannschaft 2", "Spielort 1", 3, 2, LocalDateTime.now().plusMinutes(90), true),
                new Spiel(LocalDateTime.now().plusDays(1), "Mannschaft 3", "Mannschaft 4", "Spielort 2")
        );

        gamesTable.setItems(spiele);

        addGamesTableListener();
    }

    private void addGamesTableListener() {
        gamesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected game: " + newValue);
                Spiel selectedGame = gamesTable.getSelectionModel().getSelectedItem();
                anstosszeitField.setText(selectedGame.getAnstosszeit().toString());
                mannschaft1Field.setText(selectedGame.getMannschaft1());
                mannschaft2Field.setText(selectedGame.getMannschaft2());
                spielortField.setText(selectedGame.getSpielort());
                tore1Field.setText(String.valueOf(selectedGame.getTore1()));
                tore2Field.setText(String.valueOf(selectedGame.getTore2()));
                if (selectedGame.getEndezeit() != null) {
                    endzeitField.setText(selectedGame.getEndezeit().toString());
                }
                spielBeendetField.setSelected(selectedGame.getSpielBeendet());
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
}