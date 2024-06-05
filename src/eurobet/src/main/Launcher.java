package src.main;

import java.util.logging.LogManager;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.main.data.DatenManager;

public class Launcher extends Application {
    private static Stage primaryStage;
    private static DatenManager datenManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Launcher.primaryStage = primaryStage;
        datenManager = new DatenManager();
        datenManager.loadExampleBenutzer();
        datenManager.loadExampleSpiele();
        datenManager.loadExampleTipps();
        showLoginScene();
    }

    public static DatenManager getDatenManager() {
        return datenManager;
    }

    public static void showLoginScene() throws Exception {
        Parent root = FXMLLoader.load(Launcher.class.getResource("/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void showVerwaltungScene() throws Exception {
        Parent root = FXMLLoader.load(Launcher.class.getResource("/verwaltung.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);
    }

    public static void showWettanwendungScene() throws Exception {
        Parent root = FXMLLoader.load(Launcher.class.getResource("/wettanwendung.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    public static void main (String[] args) {
        try {
            LogManager.getLogManager ().reset ();   // turn off JDK logging
            launch (args);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
