/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoproject;

import java.sql.*;

/**
 *
 * @author Carita
 */
public class DatabaseConnection 
{

    public DatabaseConnection() 
    {
        try 
        {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, USER_ID, PASSWORD);

            System.out.println("Connected to DB!");
        } 
        catch (SQLException ex) 
        {
            System.err.println(ex.getMessage());
        } 
        catch (ClassNotFoundException ex) 
        {
            System.err.println(ex.getMessage());
        }
    }

    public Connection getDBConnection() 
    {
        return this.con;
    }

    public void disconnectFromDB() 
    {
        try 
        {
            con.close();
            System.out.println("Disconnected from DB!");

        } 
        catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
    }
    
    private Connection con;
    private final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private final String USER_ID = "system";
    private final String PASSWORD = "tiger";
}