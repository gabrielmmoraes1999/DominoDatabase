package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Moraes
 */
public class Sybase {
    
    public static Connection conn;
    
    public void connect(String dns, String username, String password) throws SQLException {
        try {
            System.loadLibrary("dbjdbc16");
            Class.forName("sybase.jdbc4.sqlanywhere.IDriver").newInstance();
            conn = DriverManager.getConnection("jdbc:sqlanywhere:DNS="+dns+";UID="+username+";PWD="+password);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void disconnect() throws SQLException {
        if(!conn.isClosed()){
            conn.close();
        }
    }
}
