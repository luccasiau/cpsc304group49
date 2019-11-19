package ca.ubc.cs304.g49.database;

import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;

public class DatabaseOperationHandler implements CommandLineUiDelegate {
  @Override
  public boolean makeNewCustomer(String dlicense, Long cellNum, String name, String address) {
    // TODO: Actually add this to table.
    System.out.println("New customer info received");
    System.out.println("Name: " + name);
    System.out.println("Address: " + address);
    System.out.println("Cell phone number: " + cellNum);
    System.out.println("Driver's license: " + dlicense);
    return false;
  }
}
