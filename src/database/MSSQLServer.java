package database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Moraes
 */
public class MSSQLServer {
    public static Connection conn;
    
    public void connect() throws SQLServerException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setTransparentNetworkIPResolution(true);
        ds.setServerName("localhost");
        ds.setPortNumber(1434);
        ds.setEncrypt(false);
        ds.setIntegratedSecurity(true);
        MSSQLServer.conn = ds.getConnection();
    }
    
    public void disconnect() throws SQLException {
        if(!MSSQLServer.conn.isClosed()){
            MSSQLServer.conn.close();
        }
    }
}
