package com.example.gymdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsertSelectStatements {
    // static variable to act as arbritrary ID
    private static int id = InsertSelectStatements.setID();

    // MySQL connection info
    private static final String URL = "jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_0b040dc21d79a35";
    private static final String USER = "bd4c73f56309eb";
    private static final String PASS = "c94c82ad";

    // Gym locations to set
    private static final String[] locations = { "LA", "Sacramento", "Rancho Cordova", "Elk Grove", "Davis",
            "San Francisco", "San Jose", "Palo Alto", "Berkeley", "Stockton" };

    // Inserts gym_name to DB based on URL
    static void insert(String gym_name) {
        DriverManager.setLoginTimeout(5);
        String insertString = "INSERT INTO gym VALUES (?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(insertString)) {
            conn.setAutoCommit(false);
            stmt.setInt(1, id);
            stmt.setString(2, gym_name);
            // random index from 0 - 4
            double rand = Math.random() * 10;
            String location = locations[(int) rand];
            stmt.setString(3, location);
            stmt.executeUpdate();
            conn.commit();
            System.out.println("Success. Gym ID: " + id + " Gym Name: " + gym_name + " Gym Location: " + location);
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        id++;
    }

    // Queries DB, returns ArrayList of all gyms
    static ArrayList<Gym> select() {
        ArrayList<Gym> gyms = new ArrayList<Gym>();
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
                gyms.add(new Gym(id, gym_name, location));
                hasMoreRows = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gyms;
    }

    // Queries DB, returns current highest Gym ID + 1
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
