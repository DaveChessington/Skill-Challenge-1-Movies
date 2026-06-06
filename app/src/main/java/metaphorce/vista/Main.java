/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package metaphorce.vista;

import metaphorce.modelo.Database;
import metaphorce.modelo.Pelicula;

/**
 *
 * @author David-PC
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pelicula miPelicula = new Pelicula(
                "Inception",
                "Christopher Nolan",
                true,
                "Un ladrón que roba secretos...",
                148
        );
        Database db=new Database();
        //db.addPelicula(miPelicula);
        //db.deletePelicula(2);
        System.out.println("Movie with id 1:");
        System.out.println(db.getPelicula(2));
        System.out.println("Movies not available");
        db.changeAvailability(1);
        System.out.println(db.getAvailabilityPeliculas(false));
        System.out.println("Movies available");
        db.changeAvailability(1);
        System.out.println("All movies:");
        System.out.println(db.ListPeliculas());
    }
    
}
