package src.main.controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import src.main.client.Client1;
import src.main.classes.Benutzer;
import src.main.data.BenutzerDaoImpl;
import src.main.server.EuroBetService;

import java.rmi.RemoteException;

public class LoginController {
    // Parameters
    private final double successMsgDuration = 0.35;
    private final double failureMsgDuration = 3.0;
    private final double fadeOutDuration = 0.2;

    // --- Data ---
    private EuroBetService euroBetService;
    private Benutzer eingeloggterBenutzer = null;
    private BenutzerDaoImpl benutzerDaoImpl;
    private ObservableList<Benutzer> benutzerList;


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    public void initialize() {
        // Data
        euroBetService = Client1.getEuroBetService();
        try {
            benutzerList = FXCollections.observableArrayList(euroBetService.getAllBenutzer());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Add key listener to usernameField and passwordField
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLoginButtonAction();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLoginButtonAction();
            }
        });
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        for (Benutzer benutzer : benutzerList) {
            if (benutzer.getUsername().equals(username) && benutzer.getPassword().equals(password) && !benutzer.isGesperrt()) {
                showMessage("Login erfolgreich!", "green", successMsgDuration);
                eingeloggterBenutzer = benutzer;

                PauseTransition pause = new PauseTransition(Duration.seconds(successMsgDuration));
                pause.setOnFinished(event -> {
                    try {
                        if (benutzer.isAdmin()) {
                            Client1.showVerwaltungScene();
                        } else if (benutzer.isUser()) {
                            Client1.showWettanwendungScene();
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

    public void setEuroBetService(EuroBetService euroBetService) {
        this.euroBetService = euroBetService;
    }
}
