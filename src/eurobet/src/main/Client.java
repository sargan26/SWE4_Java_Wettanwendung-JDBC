package src.main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.LogManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.main.server.EuroBetService;
import src.main.server.Observer;

public class Client extends Application implements Observer {
    private static Stage primaryStage;
    //private static DatenManager datenManager;
    private static EuroBetService euroBetService;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Client.primaryStage = primaryStage;

        // Establish connection to registry and lookup service
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        euroBetService = (EuroBetService) registry.lookup("EuroBetService");
        showLoginScene();
    }

    public static EuroBetService getEuroBetService() {
        return euroBetService;
    }

    public static void showLoginScene() throws Exception {
        Parent root = FXMLLoader.load(Client.class.getResource("/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void showVerwaltungScene() throws Exception {
        Parent root = FXMLLoader.load(Client.class.getResource("/verwaltung.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);
    }

    public static void showWettanwendungScene() throws Exception {
        Parent root = FXMLLoader.load(Client.class.getResource("/wettanwendung.fxml"));
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

    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {
        System.out.println("Client received update: " + updateMsg);
    }
}
