package org.sustaining.sustaining_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is a classed the beans are supposed to use to get a connection with the database.
 * @author Adrian
 */
public class ConnectionFactory {
    
    private static boolean initialized = false;
    
    private static void initialize(){
        try {
            // This is needed for some java Imlementations
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.out.println("ConnectionFactory.initialize: " + ex.getMessage());
        }
    }
    
    /**
     * Use this function to get a connection to the database.
     * @return A connection to the database. 
     */
    public static Connection getConnection() throws SQLException{
        
        if(!initialized){
            initialize();
            initialized = true;
        }
        return DriverManager.getConnection("jdbc:mysql://172.17.0.1:3306/sustain?user=sustain-user&password=TcHXhv2D3y2YhmC7");
    }
}
