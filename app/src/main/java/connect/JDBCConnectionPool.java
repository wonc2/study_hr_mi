package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnectionPool {
    private List<Connection> connectionPool;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    public JDBCConnectionPool(String url, String user, String password, int poolSize) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.poolSize = poolSize;
        this.connectionPool = new ArrayList<>(poolSize);
        createConnectionPool();
    }

    private void createConnectionPool() {
        try {
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionPool.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating connection pool", e);
        }
    }

    public synchronized Connection getConnection() {
        if (connectionPool.isEmpty()) {
            throw new RuntimeException("No available connections");
        }
        return connectionPool.remove(connectionPool.size() -1);
    }

    public synchronized void releaseConnection(Connection connection) {
        connectionPool.add(connection);
    }

    public void closePool() {
        for (Connection connection : connectionPool) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connectionPool.clear();
    }
}
