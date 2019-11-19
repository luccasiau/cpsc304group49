package ca.ubc.cs304.g49.database;

import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;
import ca.ubc.cs304.g49.models.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperationHandler implements CommandLineUiDelegate {
  private DatabaseConnectionHandler dbConnectionHandler;

  public DatabaseOperationHandler(DatabaseConnectionHandler dbConnectionHandler) {
    this.dbConnectionHandler = dbConnectionHandler;
  }

  @Override
  public boolean makeNewCustomer(CustomerModel model) {
    // TODO: Actually add this to table.
    System.out.println("New customer info received");
    System.out.println("Name: " + model.getName());
    System.out.println("Address: " + model.getAddress());
    System.out.println("Cell phone number: " + model.getCellNum());
    System.out.println("Driver's license: " + model.getDlicense());

    /*
    try {
      // TODO FINISH
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("INSERT INTO Customers VALUES (?, ?, ?, ?, ?)");
    } catch (SQLException e) {
      e.printStackTrace();
    }*/

    return false;
  }
}
