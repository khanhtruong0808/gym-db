import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class GymServer {
    private static final String URL = "jdbc:mysql://us-cdbr-east-06.cleardb" +
            ".net:3306/heroku_0b040dc21d79a35";
    private static final String USER = "bd4c73f56309eb";
    private static String PASS = "c94c82ad";
    private static int currentId;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        // Current main code is purely to test functions
        setCurrentId();

        Scanner getChar = new Scanner(System.in);
        String input;

        System.out.println("Would you like to insert an item? Y/N");
        input = getChar.nextLine();
        while (input.toLowerCase().charAt(0) != 'n') {
            insert();

            System.out.println();
            System.out.println("Would you like to INSERT another item? Y/N");
            input = getChar.nextLine();
        }

        System.out.println("Current Table Data: ");
        ArrayList<String> queryList = select();
        if (queryList != null) {
            for (String name : queryList) {
                System.out.println(name);
            }
        }
        System.out.println();
        System.out.println();

        System.out.println("Would you like to DELETE an item? Y/N");
        input = getChar.nextLine();
        while (input.toLowerCase().charAt(0) != 'n') {
            delete();

            System.out.println();
            System.out.println("Would you like to DELETE another item? Y/N");
            input = getChar.nextLine();
        }

        System.out.println();
        System.out.println("Current Table Data: ");
        queryList = select();
        if (queryList != null) {
            for (String name : queryList) {
                System.out.println(name);
            }
        }
    }


    public static ArrayList<String> select() throws Exception {

        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String sqlString = "SELECT * FROM gym";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement statement = conn.prepareStatement(sqlString);) {

            ResultSet result = statement.executeQuery();

            ArrayList<String> array = new ArrayList<String>();

            while (result.next()) {
                array.add(result.getInt("id") + " " +
                        result.getString("gym_name") + " " +
                        result.getString("location"));
            }

            conn.close();
            return array;
        } catch (SQLException e) {
            System.out.println("There was a problem with your query");
            e.printStackTrace();
        }

        return null;
    }

    public static void insert() throws SQLException {

        System.out.print("Type a Gym name: ");
        String gymName = scanner.nextLine();

        String sqlString = "INSERT INTO gym VALUES('" + (currentId++) + "', '"
                + gymName + "'," + "'city name here')";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement statement = conn.prepareStatement(sqlString);) {

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred while INSERTING");
            e.printStackTrace();
        }

    }

    public static void delete() throws SQLException {

        System.out.print("Type the gym name of the row data you want to " +
                "delete: ");
        String gymName = scanner.nextLine();

        String sqlString = "DELETE from gym WHERE gym_name='" + gymName + "'";

        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement deleting = conn.prepareStatement(sqlString);) {

            deleting.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred while DELETING");
            e.printStackTrace();
        }
    }

    public static void setCurrentId() throws SQLException {
        String sqlString = "SELECT MAX(id) FROM gym";
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        try (Connection conn = DriverManager.getConnection(URL, USER,
                PASS);
             PreparedStatement statement = conn.prepareStatement(sqlString);) {

            ResultSet result = statement.executeQuery();

            result.next();
            currentId = result.getInt("MAX(id)") + 1;
        } catch (SQLException e) {
            System.out.println("There was a problem with your id query");
            e.printStackTrace();
        }
    }
}