package ca.ubc.cs304.g49.delegates;

/**
 * Delegate for the methods involved in interacting with command line. This is
 * following the example from the tutorial.
 */
public interface CommandLineUiDelegate {
  // TODO: Add functions here as needed.
  boolean makeNewCustomer(String dlicense, Long cellNum, String name, String address);
}
