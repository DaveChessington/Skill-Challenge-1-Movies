/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  metaphorce.modelo;

/**
 *
 * @author David-PC
 */
public class Funcion {
    private int id_funcion;
    private String dia;
    private String hora;
    private boolean disponible;
    private int asientos_disponibles;
    private int id_pelicula;
    private int id_sala;

    public Funcion(){}
    
    public Funcion(String dia, String hora, boolean disponible, int asientos_disponibles, int id_pelicula, int id_sala) {
        this.dia = dia;
        this.hora = hora;
        this.disponible = disponible;
        this.asientos_disponibles = asientos_disponibles;
        this.id_pelicula = id_pelicula;
        this.id_sala = id_sala;
    }

    public int getId_funcion() {
        return id_funcion;
    }

    public void setId_funcion(int id_funcion) {
        this.id_funcion = id_funcion;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getAsientos_disponibles() {
        return asientos_disponibles;
    }

    public void setAsientos_disponibles(int asientos_disponibles) {
        this.asientos_disponibles = asientos_disponibles;
    }

    public int getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }
    
    
}
