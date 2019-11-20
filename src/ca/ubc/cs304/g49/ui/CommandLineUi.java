package ca.ubc.cs304.g49.ui;

import ca.ubc.cs304.g49.database.DatabaseConnectionHandler;
import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;
import ca.ubc.cs304.g49.models.CustomerModel;
import ca.ubc.cs304.g49.models.ReservationModel;
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
      String username = Util.readString(bufferedReader, 255, true).orElse("");

      System.out.print("Enter password: ");
      String password = Util.readString(bufferedReader, 255, true).orElse("");

      loggedIn = dbHandler.login(username, password);
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
            handleNewCustomer(Optional.empty());
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

  private void handleNewReservation() {
    ReservationModel reservationModel = new ReservationModel();
    reservationModel.readReservationInfo(bufferedReader);

    while (!delegate.dlicenseExist(reservationModel.getDlicense())) {
      System.out.println("This driver's license is not affiliated with any customer.");
      int in = 3;
      while (in != 1 && in != 2) {
        System.out.print("Do you want to register a new customer [1] or enter a different license [2]? [1/2] ");
        in = Util.readInteger(bufferedReader, false).orElse(3);
      }

      if (in == 2) {
        reservationModel.readDlicense(bufferedReader);
      } else {
        handleNewCustomer(Optional.of(reservationModel.getDlicense()));
      }
    }

    if (delegate.insertReservation(reservationModel)) {
      System.out.println("Reservation successfully created!");
      System.out.printf("Confirmation number: %s%n", reservationModel.getConfno());
      System.out.printf(
          "Customer's Driver's License: %s%n", reservationModel.getDlicense());
      System.out.printf("Vehicle Type: %s%n", reservationModel.getVtname());
      System.out.printf("Branch location: %s%n", reservationModel.getLocation());
      System.out.printf("Branch city: %s%n", reservationModel.getCity());
      System.out.printf("Start date: %s%n", reservationModel.getStartDate().toString());
      System.out.printf("End date: %s%n", reservationModel.getEndDate().toString());
    } else {
      Util.printWarning("Reservation registration failed!");
    }
  }

  private void handleNewCustomer(Optional<String> dlicenseOptional) {
    CustomerModel customerModel = new CustomerModel();
    dlicenseOptional.ifPresent(customerModel::setDlicense);
    customerModel.readCustomerInfo(bufferedReader);

    if (delegate.insertCustomer(customerModel)) {
      System.out.println("Customer successfully registered.");
    } else {
      Util.printWarning("Customer registration failed.");
    }
  }

  private void handleQuit() {
    // TODO
  }
}
