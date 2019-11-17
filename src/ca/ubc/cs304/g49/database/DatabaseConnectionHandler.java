package ca.ubc.cs304.g49.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handle database stuff
 */
public class DatabaseConnectionHandler {
  private static final String EXCEPTION_TAG = "[EXCEPTION]";

  private Connection connection = null;

  public DatabaseConnectionHandler() {
    try {
      // Load the Oracle JDBC driver
      // Following example from tutorial
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
     } catch (SQLException e) {
      System.out.println(EXCEPTION_TAG + " " + e.getMessage());
    }
  }

  public void close() {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      System.out.println(EXCEPTION_TAG + " " + e.getMessage());
    }
  }

  private void rollbackConnection() {
    try {
      connection.rollback();
    } catch (SQLException e) {
      System.out.println(EXCEPTION_TAG + " " + e.getMessage());
    }
  }
}
