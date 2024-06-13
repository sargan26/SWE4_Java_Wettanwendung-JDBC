package src.main.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import src.main.data.DatenManager;

public class EuroBetServer {
    public static void main(String[] args) {
        try {
            // Create the datamanger, load example data
            DatenManager datenManager = new DatenManager();
            datenManager.loadExampleBenutzer();
            datenManager.loadExampleSpiele();
            datenManager.loadExampleTipps();

            // Create the service and bind it to the registry
            EuroBetServiceImpl euroBetService = new EuroBetServiceImpl(datenManager);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("EuroBetService", euroBetService);
            System.out.println("EuroBetServer started");
        } catch (Exception e) {
            System.out.println("EuroBetServer failed to start:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
