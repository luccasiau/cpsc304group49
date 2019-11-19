package ca.ubc.cs304.g49.database;

import ca.ubc.cs304.g49.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handle database connection stuff
 */
public class DatabaseConnectionHandler {
  private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";

  private Connection connection = null;

  public DatabaseConnectionHandler() {
    try {
      // Load the Oracle JDBC driver
      // Following example from tutorial
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
     } catch (SQLException e) {
      Util.printException(e.getMessage());
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public void rollbackConnection() {
    try {
      connection.rollback();
    } catch (SQLException e) {
      Util.printException(e.getMessage());
    }
  }

  public void close() {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      Util.printException(e.getMessage());
    }
  }

  public boolean login(String username, String password) {
    try {
      if (connection != null) {
        connection.close();
      }

      connection = DriverManager.getConnection(ORACLE_URL, username, password);
      connection.setAutoCommit(false);

      System.out.println("\nConnected to Oracle DB!");
      return true;
    } catch (SQLException e) {
      Util.printException(e.getMessage());
      return false;
    }
  }
}
