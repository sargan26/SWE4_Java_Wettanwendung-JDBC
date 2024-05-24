package src.main;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class LoginController {
    // Parameters
    private final double successMsgDuration = 0.6;
    private final double failureMsgDuration = 3.0;
    private final double fadeOutDuration = 0.3;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("admin")) {
            showMessage("Login erfolgreich!", "green", successMsgDuration);

            PauseTransition pause = new PauseTransition(Duration.seconds(successMsgDuration));
            pause.setOnFinished(event -> {
                try {
                    Launcher.showVerwaltungScene();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        } else {
            showMessage("Login fehlgeschlagen! Bitte versuchen Sie es erneut.", "red", failureMsgDuration);
        }
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
