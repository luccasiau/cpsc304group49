package ca.ubc.cs304.g49.controller;

import ca.ubc.cs304.g49.database.DatabaseConnectionHandler;
import ca.ubc.cs304.g49.database.DatabaseOperationHandler;
import ca.ubc.cs304.g49.ui.CommandLineUi;

/**
 * Main class responsible for controlling the application.
 */
public class Controller {
  private DatabaseConnectionHandler dbConnectionHandler;
  private DatabaseOperationHandler dbOperationHandler;

  public Controller() {
    dbConnectionHandler = new DatabaseConnectionHandler();
    dbOperationHandler = new DatabaseOperationHandler();
  }

  public void run() {
    CommandLineUi ui = new CommandLineUi(dbOperationHandler);
    ui.login(dbConnectionHandler);
    ui.showMenu();
  }

  /**
   * Main
   */
  public static void main(String args[]) {
    Controller controller = new Controller();
    controller.run();
  }
}
