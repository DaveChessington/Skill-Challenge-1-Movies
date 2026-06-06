/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metaphorce.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author David-PC
 */
public class Database {

    Controller control = new Controller();

    //CRUD peliculas
    public void addPelicula(Pelicula p) {
        Connection con = control.getConection();
        String query = "INSERT into Peliculas (titulo,director,sinopsis,duracion,disponible) Values(?,?,?,?,?) ";
        String comp="SELECT * FROM Peliculas where titulo=?";
        try {
            PreparedStatement c=con.prepareStatement(comp);
            c.setString(1,p.getTitulo());
            ResultSet res = c.executeQuery();
            if (res.next()){
                throw new Exception("Movie already registered");
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDirector());
            ps.setString(3, p.getSinopsis());
            ps.setInt(4, p.getDuracion());
            ps.setBoolean(5, p.getDisponible());
            int filasAfectadas = ps.executeUpdate(); // Ejecuta la query en la BD
            if (filasAfectadas > 0) {
                System.out.println("movie added succesfully");
            }
            control.closeConection();
        } catch (Exception e) {
            if (con != null) {
                control.closeConection();
            }
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Pelicula> ListPeliculas() {
        Connection con = control.getConection();
        String query = "SELECT * FROM Peliculas";
        ArrayList<Pelicula> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Pelicula p = new Pelicula();
                int id_pelicula = Integer.parseInt(res.getString("id_pelicula"));
                String titulo = res.getString("titulo");
                String driector = res.getString("director");
                String sinopsis = res.getString("sinopsis");
                boolean disponible = res.getBoolean("disponible");
                int duracion = res.getInt("duracion");

                p.setId_pelicula(id_pelicula);
                p.setDirector(driector);
                p.setDuracion(duracion);
                p.setSinopsis(sinopsis);
                p.setTitulo(titulo);
                p.setDisponible(disponible);
                movies.add(p);
            }
            control.closeConection();
        } catch (Exception e) {
            if (con != null) {
                control.closeConection();
            }
            System.out.println(e.getMessage());
        }
        return movies;
    }

    public ArrayList<Pelicula> getAvailabilityPeliculas(boolean isAvailable) {
        Connection con = control.getConection();
        String query = "SELECT * FROM Peliculas where disponible=?";
        ArrayList<Pelicula> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setBoolean(1, isAvailable);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Pelicula p = new Pelicula();
                int id_pelicula = res.getInt("id_pelicula");
                String titulo = res.getString("titulo");
                String driector = res.getString("director");
                String sinopsis = res.getString("sinopsis");
                boolean disponible = res.getBoolean("disponible");
                int duracion = res.getInt("duracion");

                p.setId_pelicula(id_pelicula);
                p.setDirector(driector);
                p.setDuracion(duracion);
                p.setSinopsis(sinopsis);
                p.setTitulo(titulo);
                p.setDisponible(disponible);
                movies.add(p);
            }
            control.closeConection();
        } catch (Exception e) {
            if (con != null) {
                control.closeConection();
            }
            System.out.println(e.getMessage());
        }
        return movies;
    }

    public Pelicula getPelicula(int id) {
        Connection con = control.getConection();
        String query = "SELECT * FROM Peliculas where id_pelicula=?";
        Pelicula p = null;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                p = new Pelicula();

                p.setId_pelicula(res.getInt("id_pelicula"));
                p.setTitulo(res.getString("titulo"));
                p.setDirector(res.getString("director"));
                p.setSinopsis(res.getString("sinopsis"));
                p.setDisponible(res.getBoolean("disponible"));
                p.setDuracion(res.getInt("duracion"));
            }

            control.closeConection();
        } catch (Exception e) {
            if (con != null) {
                control.closeConection();
            }
            System.out.println(e.getMessage());
        }
        return p;
    }

    public void changeAvailability(int id) {
        Connection con = control.getConection();
        String query = "UPDATE Peliculas SET disponible = NOT disponible WHERE id_pelicula = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("updated movie");
            }
            control.closeConection();
        } catch (Exception e) {
            if (con != null) {
                control.closeConection();
            }
            System.out.println(e.getMessage());
        }
    }

    public void deletePelicula(int id) {
        Connection con = control.getConection();
        String query = "delete FROM Peliculas where id_pelicula=?";
        try {
            
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("deleted movie");
            }
            control.closeConection();
        } catch (Exception e) {
            if (con != null) {
                control.closeConection();
            }
            System.out.println(e.getMessage());
        }
    }
}
