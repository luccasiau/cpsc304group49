package ca.ubc.cs304.g49.controller;

import ca.ubc.cs304.g49.database.DatabaseConnectionHandler;

/**
 * Main class responsible for controlling the application.
 */
public class Controller {
  private DatabaseConnectionHandler databaseConnectionHandler;

  public Controller() {
    databaseConnectionHandler = new DatabaseConnectionHandler();
  }

  /**
   * Main
   */
  public static void main(String args[]) {
    // TODO
  }
}
