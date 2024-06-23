package src.main.classes;

import java.io.Serializable;

public class Mannschaft implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double strength;

    public Mannschaft(String name, double strength) {
        this.name = name;
        this.strength = strength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "Mannschaft{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", strength=" + strength +
                '}' + "\n";
    }
}