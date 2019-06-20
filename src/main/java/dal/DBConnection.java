package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;

public final class DBConnection {
    private static final String CONNECTIONSTRING = "jdbc:mysql://localhost:3306/quizs3?useSSL=false";
    private static Connection connection = null;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //private static Statement stm = null;


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(CONNECTIONSTRING, "root", "EightFiveTwo.852");

//            stm = connection.createStatement();
//            String query = "CREATE TABLE mytesttable3 (myfield INT)";
//            stm.execute(query);

        } catch (SQLException ex) {
            System.out.println("Error: unable to connect to database.");
            ex.printStackTrace();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return connection;

    }
}

