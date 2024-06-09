package src.main.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import src.main.classes.Benutzer;
import src.main.classes.Mannschaft;
import src.main.classes.Spiel;
import src.main.classes.Tipp;

public interface EuroBetService extends Remote {
    // Benutzer
    List<Benutzer> getAllBenutzer() throws RemoteException;
    void addBenutzer(Benutzer benutzer) throws RemoteException;
    Benutzer getBenutzerByName(String username) throws RemoteException;
    Benutzer getBenutzerById(int id) throws RemoteException;
    void updateBenutzer(Benutzer benutzer) throws RemoteException;
    void deleteBenutzer(Benutzer benutzer) throws RemoteException;
    void deleteBenutzerById(int id) throws RemoteException;
    void deleteBenutzerByName(String username) throws RemoteException;

    // Mannschaft
    List<Mannschaft> getAllMannschaften() throws RemoteException;
    void addMannschaft(Mannschaft mannschaft) throws RemoteException;
    Mannschaft getMannschaftByName(String name) throws RemoteException;
    Mannschaft getMannschaftById(int id) throws RemoteException;
    void updateMannschaft(Mannschaft mannschaft) throws RemoteException;
    void deleteMannschaft(Mannschaft mannschaft) throws RemoteException;
    void deleteMannschaftByName(String name) throws RemoteException;

    // Spiel
    List<Spiel> getAllSpiele() throws RemoteException;
    void addSpiel(Spiel spiel) throws RemoteException;
    Spiel getSpielById(int id) throws RemoteException;
    void updateSpiel(Spiel spiel) throws RemoteException;
    void deleteSpiel(Spiel spiel) throws RemoteException;
    void deleteSpielById(int id) throws RemoteException;

    // Tipp
    List<Tipp> getAllTipps() throws RemoteException;
    void addTipp(Tipp tipp) throws RemoteException;
    Tipp getTippById(int id) throws RemoteException;
    void updateTipp(Tipp tipp) throws RemoteException;
    void deleteTipp(Tipp tipp) throws RemoteException;
    void deleteTippById(int id) throws RemoteException;

    // Zustäzlich
    void updateScores() throws RemoteException;
    void printHello() throws RemoteException;

}