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
    private int no_columnas;
    private int no_filas;
    private int no_asientos;
    private int id_cinema;
    Database db=new Database();
    public Sala(){}

    public Sala(int id_sala, String nombre, int no_columnas, int no_filas, int no_asientos, int id_cinema) {
        if (no_columnas>26){
            throw new IllegalArgumentException("no_columnas cannot be more than 26");
        }
        if (db.getCinema(id_cinema)==null){
            throw new IllegalArgumentException("chosen cinema doesnt exist");
        }
        this.id_sala = id_sala;
        this.nombre = nombre;
        this.no_asientos = no_asientos;
        this.id_cinema = id_cinema;
        this.no_columnas = no_columnas;
        this.no_filas = no_filas;
    }

    public int getNo_columnas() {
        return no_columnas;
    }

    public void setNo_columnas(int no_columnas) {
        if (no_columnas>26){
            throw new IllegalArgumentException("no_columnas cannot be more than 26");
        }
        this.no_columnas = no_columnas;
    }

    public int getNo_filas() {
        return no_filas;
    }

    public void setNo_filas(int no_filas) {
        this.no_filas = no_filas;
    }


    public int getId_cinema() {
        return id_cinema;
    }

    public void setId_cinema(int id_cinema) {
        if (db.getCinema(id_cinema)==null){
            throw new IllegalArgumentException("chosen cinema doesnt exist");
        }
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

    private void setNo_asientos(int no_asientos) {
        this.no_asientos = no_asientos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj){
        //Check if they belong to the same class
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Sala other=(Sala) obj;
        return this.getNombre().equals(other.getNombre()) &&
                this.getNo_asientos()==other.getNo_asientos() &&
                this.getNo_filas()==other.getNo_filas() &&
                this.getNo_columnas()==other.getNo_columnas() &&
                this.getId_cinema()== other.getId_cinema();
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id_sala=" + id_sala +
                ", nombre='" + nombre + '\'' +
                ", no_columnas=" + no_columnas +
                ", no_filas=" + no_filas +
                ", no_asientos=" + no_asientos +
                ", id_cinema=" + id_cinema +
                '}';
    }
}
