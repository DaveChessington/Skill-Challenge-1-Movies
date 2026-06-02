/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  metaphorce.modelo;

/**
 *
 * @author David-PC
 */
public class Sala {
    private int id_sala;
    private String nombre;
    private int no_asientos;
    private int id_cinema;
    
    public Sala(){}
    
    public Sala(int no_asientos,String nombre, int id_cinema) {
        this.no_asientos = no_asientos;
        this.nombre=nombre;
        this.id_cinema = id_cinema;
    }

    public int getId_cinema() {
        return id_cinema;
    }

    public void setId_cinema(int id_cinema) {
        this.id_cinema = id_cinema;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public int getNo_asientos() {
        return no_asientos;
    }

    public void setNo_asientos(int no_asientos) {
        this.no_asientos = no_asientos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
