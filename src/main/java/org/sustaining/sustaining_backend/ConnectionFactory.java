/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author Elev
 */
@Singleton
public class ConnectionFactory {
    
    @PostConstruct
    public void initialize(){
        try {
            // This is needed for some java Imlementations
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.out.println("ConnectionFactory.initialize: " + ex.getMessage());
        }
    }
    
    public Connection getConnection(){
        try{
            //Sustaining is misspelled because the database is mispselled
            return DriverManager.getConnection("jdbc:mysql://localhost/sustaning?user=root");
        } catch(Exception ex){
            System.out.println("ConnectionFactory.getConnection: " + ex.getMessage());
            return null;
        }
    }
}
