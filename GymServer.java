import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

class GymServer {
    public static Connection dbConnection = null;
    public static int currentId;

public static void main(String[] args) throws Exception {
    getConnection();

    // If a connection wasn't established exit
    if(dbConnection == null){
        return;
    }

    setCurrentId();

    Scanner getChar = new Scanner(System.in);
    String input;

    System.out.println("Would you like to insert an item? Y/N");
    input = getChar.nextLine();
    while( input.toLowerCase().charAt(0) != 'n'){
        post();

        System.out.println();
        System.out.println("Would you like to INSERT another item? Y/N");
        input = getChar.nextLine();
    }

    System.out.println("Current Table Data: ");
    ArrayList<String> queryList = getTableData();
    if(queryList != null){
        for(String name: queryList){
            System.out.println(name);
        }
    }
    System.out.println();
    System.out.println();

    System.out.println("Would you like to DELETE an item? Y/N");
    input = getChar.nextLine();
   while( input.toLowerCase().charAt(0) != 'n'){
       delete();

       System.out.println();
       System.out.println("Would you like to DELETE another item? Y/N");
       input = getChar.nextLine();
   }

   System.out.println();
   System.out.println("Current Table Data: ");
   queryList = getTableData();
    if(queryList != null){
        for(String name: queryList){
            System.out.println(name);
        }
    }

    dbConnection.close();
}


public static void getConnection() throws Exception {
    if(dbConnection == null) {
        try {
            String url = "jdbc:mysql://us-cdbr-east-06.cleardb" +
                    ".net:3306/heroku_0b040dc21d79a35";
            Properties info = new Properties();
            info.put("user", "bd4c73f56309eb");
            info.put("password", "c94c82ad");
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url, info);

            if (dbConnection != null) {
                System.out.println("Successfully connected to MySQL database");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while connecting MySQL " +
                    "clear database");
            e.printStackTrace();
        }
    }
}

public static ArrayList<String> getTableData() throws Exception {
    try{
        PreparedStatement statement = dbConnection.prepareStatement("SELECT " +
                "* FROM gym");

        ResultSet result = statement.executeQuery();

        ArrayList<String> array = new ArrayList<String>();

        while(result.next()){
            array.add(result.getInt("id")+ " "+ result.getString(
                    "gym_name") + " " + result.getString("location"));
        }

        return array;
    } catch (Exception e) {
        System.out.println("There was a problem with your query");
        e.printStackTrace();
    }

    return null;
}

public static void post() throws Exception{
    Scanner getInpt = new Scanner(System.in);
    System.out.print("Type a Gym name: ");
    String gymName = getInpt.nextLine();
    try {
        PreparedStatement posted = dbConnection.prepareStatement("INSERT INTO" +
                " gym VALUES('" + currentId++ + "', '"+ gymName + "',"+
                "'city name here')");
        posted.executeUpdate();
    } catch(Exception e){
        System.out.println("An error occurred while INSERTING");
        e.printStackTrace();
    }
}

public static void delete() throws Exception{
    Scanner getInpt = new Scanner(System.in);
    System.out.print("Type the gym name of the row data you want to delete:" +
            " ");
    String gymName = getInpt.nextLine();

    try {
        PreparedStatement deleteing = dbConnection.prepareStatement("DELETE " +
                "from" +
                " gym WHERE gym_name='"+gymName+"'");
            deleteing.executeUpdate();
        } catch(Exception e){
            System.out.println("An error occurred while DELETING");
            e.printStackTrace();
        }
    }

    public static void setCurrentId() throws Exception {
        try{
            PreparedStatement statement = dbConnection.prepareStatement("SELECT " +
                    "MAX(id) FROM gym");

            ResultSet result = statement.executeQuery();

            currentId = result.getRow();
            currentId++;
        } catch (Exception e) {
            System.out.println("There was a problem with your id query");
            e.printStackTrace();
        }
    }
}