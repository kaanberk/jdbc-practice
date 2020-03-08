package jdbctests;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        //connection string
        String dbUrl = "jdbc:oracle:thin:@3.82.231.156:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

        /*   3 important steps in JDBC
                Connection -->  Helps our java project connect to database
                Statement -->   Helps to write and execute SQL query
                ResultSet -->   A DataStructure where we can store the data that came from database
        */

        //create connection to database
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);

        //create statement object
        Statement statement =  connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from employees");

        //move pointer to next row
//        resultSet.next();
//        System.out.println(resultSet.getString("region_name"));

        while(resultSet.next()){
            System.out.println(resultSet.getString(2)+"-"+
                    resultSet.getString(3)+"-"+
                    resultSet.getInt("salary")) ;
        }

        //close connection
        resultSet.close();
        statement.close();
        connection.close();


    }
}
