package src.main.classes;


import java.io.Serializable;

public class Benutzer implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum BenutzerRolle {
        ADMIN,
        USER
    }

    private static int idCounter = 0;
    private final int id;
    private String username;
    private String password;
    private BenutzerRolle rolle;
    private int punkte;
    private boolean gesperrt;

    public Benutzer(String username, String password, BenutzerRolle rolle) {
        this.id = idCounter++;
        this.username = username;
        this.password = password;
        this.punkte = 0;
        this.rolle = rolle;
        this.gesperrt = false;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BenutzerRolle getRolle() {
        return rolle;
    }

    public void setRolle(BenutzerRolle rolle) {
        this.rolle = rolle;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getPunkte() {
        return punkte;
    }

    public boolean isAdmin() {
        return rolle == BenutzerRolle.ADMIN;
    }

    public boolean isUser() {
        return rolle == BenutzerRolle.USER;
    }

    public boolean isGesperrt() {
        return gesperrt;
    }

    public void setGesperrt(boolean gesperrt) {
        this.gesperrt = gesperrt;
    }
}
