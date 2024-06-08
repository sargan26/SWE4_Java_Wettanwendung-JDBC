package src.main.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void update(Object observable, Object updateMsg) throws RemoteException;
}


