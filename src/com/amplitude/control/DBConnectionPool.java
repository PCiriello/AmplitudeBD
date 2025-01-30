package com.amplitude.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DBConnectionPool {
	private static List<Connection> freeDbConnections;
	private static DatabaseConfig config;
	
	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("DB driver not found!" + e);
			System.err.println(e);
		}
	}
	
    public DBConnectionPool(DatabaseConfig config) {
        DBConnectionPool.config = config;
    }

	private static Connection createDBConnection(String username, String password) throws SQLException {
		Connection newConnection = null;
		String ip = "localhost";
		String port = "3306";
		String db = "amplitude";
		String params = "";
		newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db + params, username,
				password);
		newConnection.setAutoCommit(false);
		return newConnection;
	}

	public static synchronized Connection getConnection() throws SQLException {
	    Connection connection;
	    if (!freeDbConnections.isEmpty()) {
	        connection = freeDbConnections.get(0);
	        freeDbConnections.remove(0);
	        try {
	            if (connection.isClosed()) {
	                connection = DBConnectionPool.getConnection();
	            }
	        } catch (SQLException e) {
	            if (connection != null) {
	                connection.close();
	            }
	            connection = DBConnectionPool.getConnection();
	        }
	    } else {
	        String username = config.getUsername();
	        String password = config.getPassword();
	        connection = DBConnectionPool.createDBConnection(username, password);
	    }

	    return connection;
	}
	

	public static synchronized void releaseConnection(Connection connection) {
		DBConnectionPool.freeDbConnections.add(connection);
	}
}