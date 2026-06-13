/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metaphorce.modelo;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
    }

    //CRUD cinemas
    public void addCinema(Cinema c){
        Connection con= control.getConection();
        String query="INSERT INTO Cinemas (nombre) VALUES(?)";
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,c.getNombre());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("cinema already added succesfully");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
    }

    public ArrayList<Cinema> ListCinemas(){
        Connection con=control.getConection();
        String query="SELECT * FROM Cinemas";
        ArrayList<Cinema> cines=new ArrayList<>();
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ResultSet res=ps.executeQuery();
            while (res.next()) {
                Cinema c=new Cinema();
                int id_cinema=res.getInt("id_cinema");
                String nombre=res.getString("nombre");

                c.setId_cinema(id_cinema);
                c.setNombre(nombre);

                cines.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
        return cines;
    }

    public Cinema getCinema(int id){
        Connection con=control.getConection();
        String query="SELECT * FROM Cinemas where id_cinema=?";
        ArrayList<Cinema> cines=new ArrayList<>();
        Cinema c=null;
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet res=ps.executeQuery();
            if (res.next()){
               c=new Cinema();
               c.setId_cinema(res.getInt("id_cinema"));
               c.setNombre(res.getString("nombre"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        control.closeConection();
        return c;
    }

    public void deleteCinema(int id){
        Connection con = control.getConection();
        String query = "delete FROM Cinemas where id_cinema=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("deleted cinema");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
    }

    //CRUD Salas de cine
    public void addSala(Sala s){
        Connection con=control.getConection();
        String query="INSERT INTO Salas (nombre, no_columnas, no_filas, id_cinema) VALUES(?,?,?,?)";
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,s.getNombre());
            ps.setInt(2,s.getNo_columnas());
            ps.setInt(3,s.getNo_filas());
            ps.setInt(4,s.getId_cinema());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("cinema already added succesfully");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        control.closeConection();

    }

    public ArrayList<Sala> ListSalas(){
        Connection con=control.getConection();
        String query="SELECT * FROM Salas";
        ArrayList<Sala> salas=new ArrayList<>();
        try{
            PreparedStatement ps= con.prepareStatement(query);
            ResultSet res=ps.executeQuery();
            while(res.next()){
                int id_sala=res.getInt("id_sala");
                String nombre =res.getString("nombre");
                int no_asientos=res.getInt("no_asientos");
                int no_columnas=res.getInt("no_columnas");
                int no_filas=res.getInt("no_filas");
                int id_cinema=res.getInt("id_cinema");
                Sala s=new Sala(id_sala,nombre,no_asientos,no_columnas,no_filas,id_cinema);
                salas.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
        return salas;
    }

    public Sala getSala(int id){
        Connection con=control.getConection();
        String query="SELECT * FROM Salas where =?";
        Sala s=null;
        try{
            PreparedStatement ps= con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet res=ps.executeQuery();
            if(res.next()){
                int id_sala=res.getInt("id_sala");
                String nombre =res.getString("nombre");
                int no_asientos=res.getInt("no_asientos");
                int no_columnas=res.getInt("no_columnas");
                int no_filas=res.getInt("no_filas");
                int id_cinema=res.getInt("id_cinema");
                s=new Sala(id_sala,nombre,no_asientos,no_columnas,no_filas,id_cinema);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
        return s;
    }

    public void deleteSala(int id){
        Connection con = control.getConection();
        String query = "delete FROM Salas where id_sala=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("deleted sala");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        control.closeConection();
    }


}
