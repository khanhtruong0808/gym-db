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
        // SQL Insert string to insert gym_id, gym_name, location
        String insertString = "INSERT INTO gym VALUES (?,?,?)";
        // connects and automatically closes sql connection
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(insertString)) {
            conn.setAutoCommit(false);
            // set variables in prepared statements
            stmt.setInt(1, id);
            stmt.setString(2, gym_name);
            // random index from 0 - 4
            double rand = Math.random() * 10;
            String location = locations[(int) rand];
            stmt.setString(3, location);
            // commits sql statement
            stmt.executeUpdate();
            conn.commit();
            System.out.println("Success. Gym ID: " + id + " Gym Name: " + gym_name + " Gym Location: " + location);
        } catch (Exception e) {
            // prints out error if any
            System.out.println("Error:" + e);
        }
    }

    // Queries DB, returns ArrayList of all gyms
    static ArrayList<Gym> select() {
        ArrayList<Gym> gyms = new ArrayList<Gym>();
        // connects and automatically closes sql connection
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gym", ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)) {
            // iterator for query results
            ResultSet rs = stmt.executeQuery();
            boolean hasMoreRows = rs.first();
            // if there are no rows, print so in console
            if (!hasMoreRows) {
                System.out.println("No rows returned");
            }
            // loop all rows and add to array list
            while (hasMoreRows) {
                int id = rs.getInt(1);
                String gym_name = rs.getString(2);
                String location = rs.getString(3);
                gyms.add(new Gym(id, gym_name, location));
                hasMoreRows = rs.next();
            }
        } catch (SQLException e) {
            // prints out error if any
            e.printStackTrace();
        }
        return gyms;
    }

    // Queries DB, returns current next available id number
    private static int setID() {
        // sql string for query to get the highest current id
        String sqlString = "SELECT MAX(id) FROM gym";
        // connects and makes prepared statement
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(sqlString)) {
            // Executes prepared statement
            ResultSet result = stmt.executeQuery();

            // Gets result as an int and sums by 1
            result.next();
            int tempid = result.getInt("MAX(id)") + 1;
            
            // Returns tempid (next available id)
            return tempid;
        } catch (SQLException e) {
            // prints out error
            e.printStackTrace();
        }
        // defaults id to 1 if error occurs
        return 1;
    }
}
