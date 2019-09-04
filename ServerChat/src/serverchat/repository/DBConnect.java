/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.repository;

import serverchat.model.AppUser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NMQ
 */
public class DBConnect {
    
    public static Connection conn;
     
    public static Connection getConnection() throws SQLException{
        if(conn == null){
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_chat", "root", "root");
        }
        return conn;
    }
}
