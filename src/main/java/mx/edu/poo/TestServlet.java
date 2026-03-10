package mx.edu.poo;

import mx.edu.poo.utils.DatabaseConnection;
import org.jetbrains.annotations.NotNull;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, @NotNull HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        try (Connection conn = DatabaseConnection.getConnection()) {
            out.println("<h2 style='color:green;'>Conectado a la base de datos</h2>");
        } catch (SQLException e) {
            out.println("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
        }
        out.println("</body></html>");
    }
}
