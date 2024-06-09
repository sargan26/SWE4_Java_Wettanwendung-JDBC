package src.main.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.collections.ObservableList;
import src.main.classes.Benutzer;
import src.main.classes.Mannschaft;
import src.main.classes.Spiel;
import src.main.classes.Tipp;
import src.main.data.DatenManager;
import src.main.server.Observer;
import src.main.server.Observable;

public class EuroBetServiceImpl extends UnicastRemoteObject implements EuroBetService, Observable {
    private final DatenManager datenManager;
    private final List<Observer> observers;
    private final ScheduledExecutorService scheduler;

    public EuroBetServiceImpl(DatenManager datenManager) throws RemoteException {
        this.datenManager = datenManager;
        this.observers = new ArrayList<>();

        this.scheduler = Executors.newScheduledThreadPool(1);
        startGameChecker();
    }

    private void startGameChecker() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                checkGames();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private void checkGames() throws RemoteException {
        List<Spiel> spiele = datenManager.getSpieleDao().getAll();
        for (Spiel spiel : spiele) {
            if (!spiel.getSpielBeendet() && LocalDateTime.now().isAfter(spiel.getEndezeit())) {
                spiel.setSpielBeendet(true);
                updateSpiel(spiel);
                gameFinished(spiel);
            }
        }
    }

    @Override
    public List<Benutzer> getAllBenutzer() throws RemoteException {
        return datenManager.getBenutzerDao().getAll();
    }

    @Override
    public void addBenutzer(Benutzer benutzer) throws RemoteException {
        datenManager.getBenutzerDao().add(benutzer);
    }

    @Override
    public Benutzer getBenutzerByName(String username) throws RemoteException {
        return datenManager.getBenutzerDao().getBenutzerByName(username);
    }

    @Override
    public Benutzer getBenutzerById(int id) throws RemoteException {
        return datenManager.getBenutzerDao().getBenutzerById(id);
    }

    @Override
    public void updateBenutzer(Benutzer benutzer) throws RemoteException {
        datenManager.getBenutzerDao().update(benutzer);
    }

    @Override
    public void deleteBenutzer(Benutzer benutzer) throws RemoteException {
        datenManager.getBenutzerDao().delete(benutzer);
    }

    @Override
    public void deleteBenutzerById(int id) throws RemoteException {
        datenManager.getBenutzerDao().deleteById(id);
    }

    @Override
    public void deleteBenutzerByName(String username) throws RemoteException {
        datenManager.getBenutzerDao().deleteByName(username);
    }


    @Override
    public List<Mannschaft> getAllMannschaften() throws RemoteException {
        return datenManager.getMannschaftenDao().getAll();
    }

    @Override
    public void addMannschaft(Mannschaft mannschaft) throws RemoteException {
        datenManager.getMannschaftenDao().add(mannschaft);
    }

    @Override
    public Mannschaft getMannschaftByName(String name) throws RemoteException {
        return datenManager.getMannschaftenDao().getMannschaftByName(name);
    }

    @Override
    public Mannschaft getMannschaftById(int id) throws RemoteException {
        return datenManager.getMannschaftenDao().getMannschaftById(id);
    }

    @Override
    public void updateMannschaft(Mannschaft mannschaft) throws RemoteException {
        datenManager.getMannschaftenDao().update(mannschaft);
    }

    @Override
    public void deleteMannschaft(Mannschaft mannschaft) throws RemoteException {
        datenManager.getMannschaftenDao().delete(mannschaft);
    }

    @Override
    public void deleteMannschaftByName(String name) throws RemoteException {
        datenManager.getMannschaftenDao().deleteMannschaftByName(name);
    }

    @Override
    public List<Spiel> getAllSpiele() throws RemoteException {
        return datenManager.getSpieleDao().getAll();
    }

    @Override
    public void addSpiel(Spiel spiel) throws RemoteException {
        datenManager.getSpieleDao().add(spiel);
    }

    @Override
    public Spiel getSpielById(int id) throws RemoteException {
        return datenManager.getSpieleDao().getSpielById(id);
    }

    @Override
    public void updateSpiel(Spiel spiel) throws RemoteException {
        datenManager.getSpieleDao().update(spiel);
    }

    @Override
    public void deleteSpiel(Spiel spiel) throws RemoteException {
        datenManager.getSpieleDao().delete(spiel);
    }

    @Override
    public void deleteSpielById(int id) throws RemoteException {
        datenManager.getSpieleDao().deleteById(id);
    }

    @Override
    public List<Tipp> getAllTipps() throws RemoteException {
        return datenManager.getTippsDao().getAll();
    }

    @Override
    public void addTipp(Tipp tipp) throws RemoteException {
        datenManager.getTippsDao().add(tipp);
    }

    @Override
    public Tipp getTippById(int id) throws RemoteException {
        return datenManager.getTippsDao().getTippById(id);
    }

    @Override
    public void updateTipp(Tipp tipp) throws RemoteException {
        datenManager.getTippsDao().update(tipp);
    }

    @Override
    public void deleteTipp(Tipp tipp) throws RemoteException {
        datenManager.getTippsDao().delete(tipp);
    }

    @Override
    public void deleteTippById(int id) throws RemoteException {

    }

    @Override
    public void updateScores() throws RemoteException {
        datenManager.updateScores();
    }

    @Override
    public void printHello() throws RemoteException {
        System.out.println("printHello: Hello from EuroBetServiceImpl");
    }

    @Override
    public void addObserver(Observer observer) throws RemoteException {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) throws RemoteException {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object updateMsg) throws RemoteException {
        for (Observer observer : observers) {
            observer.update(this, updateMsg);
        }
    }

    // Call this method when a game finished
    public void gameFinished(Spiel spiel) throws RemoteException {
        notifyObservers(spiel);
    }


}
