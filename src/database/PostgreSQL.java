package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Moraes
 */
public class PostgreSQL {
    
    public static Connection conn;
    
    public void connect(String database, String username, String password) throws SQLException {
        try {
            String url = "jdbc:postgresql://localhost:5432/"+database;
            Class.forName("org.postgresql.Driver").newInstance();
            if(username == null) {
                username = "postgresql";
            }
            
            if(password == null) {
                password = "";
            }
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void disconnect() throws SQLException {
        if(!PostgreSQL.conn.isClosed()){
            PostgreSQL.conn.close();
        }
    }
}
