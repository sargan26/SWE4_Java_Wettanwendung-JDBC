package src.main.controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import src.main.Launcher;
import src.main.classes.Benutzer;
import src.main.data.BenutzerDao;
import src.main.data.DatenManager;

public class LoginController {
    // Parameters
    private final double successMsgDuration = 0.6;
    private final double failureMsgDuration = 3.0;
    private final double fadeOutDuration = 0.3;

    // --- Data ---
    private DatenManager datenManager;
    private BenutzerDao benutzerDao;
    private ObservableList<Benutzer> benutzerList;


    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    public void initialize() {
        // Data
        datenManager = Launcher.getDatenManager();
        benutzerDao = datenManager.getBenutzerDao();
        benutzerList = benutzerDao.getAll();
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        for (Benutzer benutzer : benutzerList) {
            if (benutzer.getUsername().equals(username) && benutzer.getPassword().equals(password) && !benutzer.isGesperrt()) {
                showMessage("Login erfolgreich!", "green", successMsgDuration);

                PauseTransition pause = new PauseTransition(Duration.seconds(successMsgDuration));
                pause.setOnFinished(event -> {
                    try {
                        if (benutzer.isAdmin()) {
                            datenManager.setEingeloggterBenutzer(benutzer);
                            Launcher.showVerwaltungScene();
                        } else if (benutzer.isUser()) {
                            datenManager.setEingeloggterBenutzer(benutzer);
                            Launcher.showWettanwendungScene();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                pause.play();
                return;
            }
        }

        showMessage("Login fehlgeschlagen! Bitte versuchen Sie es erneut.", "red", failureMsgDuration);
    }

    private void showMessage(String message, String color, double duration) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: " + color + ";");
        messageLabel.setOpacity(1.0); // Set the opacity back to 1

        PauseTransition pause = new PauseTransition(Duration.seconds(duration - fadeOutDuration));
        pause.setOnFinished(event -> fadeOutMessage());
        pause.play();
    }

    private void fadeOutMessage() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeOutDuration), messageLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> messageLabel.setText(""));
        fadeTransition.play();
    }
}
