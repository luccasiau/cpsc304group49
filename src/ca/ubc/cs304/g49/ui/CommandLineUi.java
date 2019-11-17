package ca.ubc.cs304.g49.ui;

import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;
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

  public CommandLineUi(CommandLineUiDelegate delegate) {
    this.delegate = delegate;
  }

  /**
   * Displays simple menu.
   */
  public void showMenu() {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
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
    // TODO
  }

  private void handleQuit() {
    // TODO
  }
}
