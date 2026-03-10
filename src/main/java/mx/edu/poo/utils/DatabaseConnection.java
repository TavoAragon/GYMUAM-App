package mx.edu.poo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Ajusta la URL si tu BD se llama GYMUAM (verifica)
    private static final String URL = "jdbc:mysql://localhost:3306/GYMUAM?useSSL=false&serverTimezone=UTC";
    private static final String USER = "aragondb";
    private static final String PASSWORD = "LTSI";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Metodo main para probar la conexión
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}