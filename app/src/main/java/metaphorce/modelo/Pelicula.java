/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  metaphorce.modelo;

/**
 *
 * @author David-PC
 */
public class Pelicula {
    private int id_pelicula;
    private String titulo;
    private String director;
    private Boolean disponible;
    private String sinopsis;
    private int duracion;

    @Override
    public String toString() {
        return "Pelicula{" + "id_pelicula=" + id_pelicula + ", titulo=" + titulo + ", director=" + director + ", disponible=" + disponible + ", sinopsis=" + sinopsis + ", duracion=" + duracion + '}';
    }
    
    public Pelicula(){}
    
    public Pelicula(String titulo, String director, Boolean disponible, String sinopsis, int duracion) {
        this.titulo = titulo;
        this.director = director;
        this.disponible = disponible;
        this.sinopsis = sinopsis;
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public boolean equals(Object obj){
        //Check if they belong to the same class
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Pelicula other=(Pelicula) obj;
        return this.getTitulo().equals(other.getTitulo()) && this.getDirector().equals(other.getDirector());
    }
}
