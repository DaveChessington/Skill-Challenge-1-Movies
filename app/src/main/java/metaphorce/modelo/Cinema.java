/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  metaphorce.modelo;

/**
 *
 * @author David-PC
 */
public class Cinema {
    private int id_cinema;
    private String nombre;

    @Override
    public String toString() {
        return "Cinema{" + "id_cinema=" + id_cinema + ", nombre=" + nombre + '}';
    }
    
    public Cinema(){}
    
    public Cinema(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_cinema() {
        return id_cinema;
    }

    public void setId_cinema(int id_cinema) {
        this.id_cinema = id_cinema;
    }
    
    
}
