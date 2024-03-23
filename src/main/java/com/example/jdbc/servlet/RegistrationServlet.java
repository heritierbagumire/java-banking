package com.example.jdbc;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String username=req.getParameter("username");
        String email=req.getParameter("email");
        String password=req.getParameter("password");

        boolean inserted = insertUserData(username,email,password);
        if (!inserted) {
            PrintWriter out = res.getWriter();
            out.println("Couldn't insert the data into the database. Please try again.");
        } else {
            PrintWriter out = res.getWriter();
            out.println("Welcome inside our application");
        }
    }
    private boolean insertUserData(String username, String email, String password){
        String url = "jdbc:mysql://localhost:3306/banking";
        String user = "root";
        String accountPassword = "";
        try (Connection conn = DriverManager.getConnection(url, user, accountPassword)) {
            String sql = "INSERT INTO demo( username,email,password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, password);
                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute database operation: " + e.getMessage());
        }
    }
}
