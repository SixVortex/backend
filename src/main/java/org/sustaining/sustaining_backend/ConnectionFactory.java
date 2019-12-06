package org.sustaining.sustaining_backend;

import java.sql.Connection;
import java.sql.DriverManager;

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
    public static Connection getConnection(){
        
        if(!initialized){
            initialize();
            initialized = true;
        }
        
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/sustain?user=sustain&password=CO7fdAQXMxytT7Pn");
        } catch(Exception ex){
            System.out.println("ConnectionFactory.getConnection: " + ex.getMessage());
            return null;
        }
    }
}
