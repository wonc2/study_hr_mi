package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnectionPool {
    private String url;
    private String user;
    private String password;

    public JDBCConnectionPool() {
        this.url = "jdbc:mysql://192.168.0.85/java_mysql";
        this.user = "root";
        this.password = "cocolabhub";
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
