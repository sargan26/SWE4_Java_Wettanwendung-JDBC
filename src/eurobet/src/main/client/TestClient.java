package src.main.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import src.main.classes.Benutzer;
import src.main.server.EuroBetService;

public class TestClient {
    public static void main(String[] args) {
        try {
            // Connection to registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            EuroBetService euroBetService = (EuroBetService) registry.lookup("EuroBetService");

            // Call method to get all Benutzer from server
           List<Benutzer> benutzerListe = euroBetService.getAllBenutzer();

            // Print all Benutzer
            for (Benutzer benutzer : benutzerListe) {
                System.out.println(benutzer.getUsername());
            }
        } catch (Exception e) {
            System.err.println("TestClient exception: " + e.toString());
            e.printStackTrace();
        }
    }
}