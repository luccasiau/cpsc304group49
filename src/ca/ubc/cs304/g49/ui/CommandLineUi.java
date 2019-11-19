package ca.ubc.cs304.g49.ui;

import ca.ubc.cs304.g49.database.DatabaseConnectionHandler;
import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;
import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * Handles user interaction with CLI.
 */
public class CommandLineUi {
  private static final int QUIT_INPUT = 2;  // FIXME: Update this as needed.

  private CommandLineUiDelegate delegate;
  private BufferedReader bufferedReader = null;

  public CommandLineUi(CommandLineUiDelegate delegate) {
    this.delegate = delegate;
  }

  /**
   * Login
   */
  public void login(DatabaseConnectionHandler dbHandler) {
    if (bufferedReader == null) {
      bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    // TODO: Add privacy thingy in password?
    // This is not super needed since password is a student number, so nothing
    // compromising. But it would be nice to have.
    boolean loggedIn = false;
    while (!loggedIn) {
      System.out.print("Enter username: ");
      String username = Util.readString(bufferedReader, 255).orElse("");

      System.out.print("Enter password: ");
      String password = Util.readString(bufferedReader, 255).orElse("");

      loggedIn = dbHandler.login(username, password);
      if (!loggedIn) {
        Util.printWarning("Invalid username/password.");
      }
      System.out.println("");
    }
  }

  /**
   * Displays simple menu.
   */
  public void showMenu() {
    if (bufferedReader == null) {
      bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }
    Optional<Integer> inputOptional = Optional.empty();

    while (inputOptional.isEmpty() || inputOptional.get() != QUIT_INPUT) {
      System.out.println();

      // FIXME: The "create customer account shouldn't be in the main menu, but I'm
      // leaving it here because (1) it's simple and (2) it's needed.
      System.out.println("1. Create customer account.");
      System.out.println("2. Quit.");
      System.out.print("Please choose one of the above options: ");

      inputOptional = Util.readInteger(bufferedReader, false);

      System.out.println(" ");

      if (inputOptional.isPresent()) {
        switch (inputOptional.get()) {
          case 1:
            handleNewCustomer();
            break;
          case 2:
            handleQuit();
            break;
          default:
            Util.printWarning("Input not within values.");
        }
      }
    }
  }

  private void handleNewCustomer() {
    String dLicense;
    while (true) {
      System.out.print("Enter customer's driver's license number: ");
      Optional<String> dLicenseOptional =
          Util.readString(bufferedReader, FieldSizes.MAXIMUM_DLICENSE_SIZE);
      if (dLicenseOptional.isPresent()) {
        dLicense = dLicenseOptional.get();
        break;
      }
    }

    Long cellNum;
    while (true) {
      System.out.print("Enter customer's cell phone number: ");
      Optional<Long> cellNumOptional =
          Util.readLong(bufferedReader, true);
      if (cellNumOptional.isPresent()) {
        cellNum = cellNumOptional.get();
        break;
      }
    }

    String name;
    while (true) {
      System.out.print("Enter customer's name: ");
      Optional<String> nameOptional =
          Util.readString(bufferedReader, FieldSizes.MAXIMUM_CUSTOMER_NAME_SIZE);
      if (nameOptional.isPresent()) {
        name = nameOptional.get();
        break;
      }
    }

    String address;
    while (true) {
      System.out.print("Enter customer's address: ");
      Optional<String> addressOptional =
          Util.readString(bufferedReader, FieldSizes.MAXIMUM_ADDRESS_SIZE);
      if (addressOptional.isPresent()) {
        address = addressOptional.get();
        break;
      }
    }

    if (delegate.makeNewCustomer(dLicense, cellNum, name, address)) {
      System.out.println("Customer successfully registered.");
    } else {
      Util.makeWarning("Customer registration failed.");
    }
  }

  private void handleQuit() {
    // TODO
  }
}
