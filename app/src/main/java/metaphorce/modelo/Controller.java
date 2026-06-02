/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  metaphorce.modelo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author David-PC
 */
public class Controller {
    String DB="Cinemas";
    String URL="jdbc:mysql://localhost:3306/%s".formatted(DB);
    String USER="root";
    String PASSWORD="root";

    Connection conexion = null;

    public Controller() {
    }

    public Connection getConection() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Successfully connected to db");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conexion;
    }

    public void closeConection() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("con succesfully closed");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
