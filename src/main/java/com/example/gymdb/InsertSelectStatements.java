package com.example.gymdb;

import java.sql.*;

public class InsertSelectStatements {
    // static variable to act as arbritrary ID
    private static int id = 1;

    // Function to connect to MySQL database
    // Inserts gym_name based on URL
    static void insert(String gym_name) {
        DriverManager.setLoginTimeout(5);
        String insertString = "INSERT INTO gym VALUES (?,?,?)";
        final String URL = "jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_0b040dc21d79a35";
        final String USER = "bd4c73f56309eb";
        final String PASS = "c94c82ad";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(insertString)) {
            conn.setAutoCommit(false);
            stmt.setInt(1, id);
            stmt.setString(2, gym_name);
            // location
            stmt.setString(3, "LA");
            stmt.executeUpdate();
            conn.commit();
            System.out.println("Success. Gym ID: " + id + " Gym Name: " + gym_name + " Gym Location: LA");
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        id++;
    }

    void select() {
        // will do a similar thing as above but with select and
        // returns JSON
        return;
    }
}
