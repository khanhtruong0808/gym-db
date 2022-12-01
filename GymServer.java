import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

class GymServer {
    public static Connection dbConnection = null;

public static void main(String[] args) throws Exception {
    getConnection();

    if(dbConnection == null){
        return;
    }

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
//   queryList.remove(queryList);
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
                    ".net:3306/heroku_71b8fb4417fe1c3";
            Properties info = new Properties();
            info.put("user", "b6ea9084c13b7f");
            info.put("password", "088a9ca9");
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
//        Connection dbConnection = getConnection();
        PreparedStatement statement = dbConnection.prepareStatement("SELECT " +
                "fname, lname FROM smallTest");

        ResultSet result = statement.executeQuery();

        ArrayList<String> array = new ArrayList<String>();

        while(result.next()){
            array.add(result.getString("fname") + " "+ result.getString(
                    "lname"));
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
    System.out.print("Type fname: ");
    String var1 = getInpt.nextLine();
    System.out.print("Type lname: ");
    String var2 = getInpt.nextLine();
    try {
//        Connection dbConnection = getConnection();
        PreparedStatement posted = dbConnection.prepareStatement("INSERT INTO" +
                " smallTest VALUES('"+var1 + "','" + var2 + "')");
        posted.executeUpdate();
    } catch(Exception e){
        System.out.println("An error occurred while INSERTING");
        e.printStackTrace();
    }
}

public static void delete() throws Exception{
    Scanner getInpt = new Scanner(System.in);
    System.out.print("Type the first name of the row data you want to delete:" +
            " ");
    String var1 = getInpt.nextLine();

    try {
//        Connection dbConnection = getConnection();
        PreparedStatement deleteing = dbConnection.prepareStatement("DELETE " +
                "from" +
                " smallTest WHERE fname='"+var1+"'");
            deleteing.executeUpdate();
        } catch(Exception e){
            System.out.println("An error occurred while DELETING");
            e.printStackTrace();
        }
    }
}