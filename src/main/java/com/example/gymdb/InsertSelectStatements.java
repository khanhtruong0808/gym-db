package com.example.gymdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertSelectStatements {
    // static variable to act as arbritrary ID
    private static int id = InsertSelectStatements.setID();
    static final String URL = "jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_0b040dc21d79a35";
    static final String USER = "bd4c73f56309eb";
    static final String PASS = "c94c82ad";

    // Function to connect to MySQL database
    // Inserts gym_name based on URL
    static void insert(String gym_name) {
        DriverManager.setLoginTimeout(5);
        String insertString = "INSERT INTO gym VALUES (?,?,?)";
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

    static void select() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gym", ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = stmt.executeQuery();
            boolean hasMoreRows = rs.first();
            if (!hasMoreRows) {
                System.out.println("No rows returned");
            }
            while (hasMoreRows) {
                int id = rs.getInt(1);
                String gym_name = rs.getString(2);
                String location = rs.getString(3);
                System.out.println(
                        "Gym ID: " + Integer.toString(id) + " Gym Name: " + gym_name + " Gym Location: " + location);
                hasMoreRows = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int setID() {
        String sqlString = "SELECT MAX(id) FROM gym";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlString)) {

            ResultSet result = stmt.executeQuery();

            result.next();
            int tempid = result.getInt("MAX(id)") + 1;
            return tempid;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
