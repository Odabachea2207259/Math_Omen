package serialization;

import java.io.Serializable;

public class User implements Serializable {
    private final String nombre;
    private final int puntos;

    public User(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "User: " + nombre + " Puntos" + puntos;
    }
}
