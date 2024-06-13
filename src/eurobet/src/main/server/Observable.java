package src.main.server;

import java.rmi.RemoteException;

public interface Observable {
    void addObserver(Observer observer) throws RemoteException;

    void removeObserver(Observer observer) throws RemoteException;

    void notifyObservers(Object updateMsg) throws RemoteException;
}