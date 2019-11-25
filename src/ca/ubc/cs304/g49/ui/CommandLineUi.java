package ca.ubc.cs304.g49.ui;

import ca.ubc.cs304.g49.database.DatabaseConnectionHandler;
import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;
import ca.ubc.cs304.g49.models.*;
import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Handles user interaction with CLI.
 */
public class CommandLineUi {
  private static final int QUIT_INPUT = 6;  // FIXME: Update this as needed.

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
      System.out.println("2. [Customer] Make new reservation.");
      System.out.println("3. [Clerk] Make new rental.");
      System.out.println("4. [Customer] View number of available vehicles");
      System.out.println("5. [Clerk] Return a vehicle.");
      System.out.println("6. Quit.");
      System.out.print("Please choose one of the above options: ");

      inputOptional = Util.readInteger(bufferedReader, false);

      if (inputOptional.isPresent()) {
        switch (inputOptional.get()) {
          case 1:
            handleNewCustomer(Optional.empty());
            break;
          case 2:
            handleNewReservation(true);
            break;
          case 3:
            handleNewRent();
            break;
          case 4:
            handleAvailableVehicles();
            break;
          case 5:
            handleReturn();
            break;
          case 6:
            handleQuit();
            break;
          default:
            Util.printWarning("Input not within values.");
        }
      }
    }
  }

  /**
   * Handles new rent.
   * STRONG ASSUMPTION: This assumes that there will be car available for that
   * time at the desired branch.
   */
  private void handleNewRent() {
    System.out.println("\nNew Rental Form");
    RentModel rentModel = new RentModel();

    String in = "";
    while (!in.toLowerCase().equals("y") && !in.toLowerCase().equals("n")) {
      System.out.print("Is there a reservation made [y/n]? ");
      in = Util.readString(bufferedReader, 255, true).orElse("");
    }

    String confno = "";
    if (in.equals("y")) {
      rentModel.readConfno(bufferedReader);
      while (delegate.fetchReservation(rentModel.getConfno()) == null) {
        System.out.println("Reservation not found. Enter new confirmation number or type \"no\" in case");
        System.out.println("there is no reservation.");
        rentModel.readConfno(bufferedReader);
        if (rentModel.getConfno().toLowerCase().equals("no")) {
          rentModel.setConfno("");
          in = "n";
          break;
        }
      }
      confno = rentModel.getConfno();
    }

    ReservationModel reservationModel;
    if (in.equals("n")){
      reservationModel = handleNewReservation(false);
    } else {
      reservationModel = delegate.fetchReservation(confno);
    }

    if (reservationModel == null) {
      Util.printWarning("Failed to make rental.");
      return;
    }

    // Get information from reservation.
    rentModel.setFromReservation(reservationModel);

    // Get a valid single vehicle's license. ASSUMING ONE EXISTS.
    VehicleModel vehicleModel = delegate.fetchVehicleFromTypeAndBranch(
        rentModel.getVtname(), rentModel.getLocation(), rentModel.getCity());
    if (vehicleModel == null) {
      Util.printWarning("Could not find available vehicle. Backing out...");
      return;
    }
    rentModel.setVlicense(vehicleModel.getVlicense());

    // Read credit card information.
    rentModel.readCardInfo(bufferedReader);

    // Insert into database.
    if (delegate.insertRent(rentModel)) {
      while (!delegate.updateVehicleStatus(rentModel.getVlicense(), "R"));
      System.out.println("\nRental Registered!");
      System.out.printf("Rent ID: %s%n", rentModel.getRentid());
      System.out.printf("Vehicle's License: %s%n", rentModel.getVlicense());
      System.out.printf("Vehicle Type: %s%n", rentModel.getVtname());
      System.out.printf("Branch Location: %s%n", rentModel.getLocation());
      System.out.printf("Branch City: %s%n", rentModel.getCity());
      System.out.printf("Start Date: %s%n", rentModel.getStartdate());
      System.out.printf("End Date: %s%n", rentModel.getEnddate());
      System.out.printf("Card Name: %s%n", rentModel.getCardname());
      System.out.printf("Card Number: %s%n", rentModel.getCardno());
      System.out.printf("Card Exp. Date: %s%n", rentModel.getExpdate());
    } else {
      Util.printWarning("Rental registration failed.");
    }
  }

  /**
   * Creates new reservation and returns a model to it.
   * @param addToDb flag representing whether we want to put it in the table.
   * @return reservation model
   */
  private ReservationModel handleNewReservation(boolean addToDb) {
    if (addToDb) {
      System.out.println("\nNew Reservation Form");
    }
    ReservationModel reservationModel = new ReservationModel();
    reservationModel.readReservationInfo(bufferedReader);

    while (!delegate.dlicenseExist(reservationModel.getDlicense())) {
      System.out.println("\nThis driver's license is not affiliated with any customer.");
      int in = 3;
      while (in != 1 && in != 2) {
        System.out.print("Do you want to register a new customer [1] or enter a different license [2]? ");
        in = Util.readInteger(bufferedReader, false).orElse(3);
      }

      if (in == 2) {
        reservationModel.readDlicense(bufferedReader);
      } else {
        handleNewCustomer(Optional.of(reservationModel.getDlicense()));
      }
    }

    // System.out.println("DEBUG vehicle count");
    int vehicleCount = delegate.fetchAvailableVehicles(
        reservationModel.getVtname(),
        reservationModel.getLocation(),
        reservationModel.getCity()).size();
    // System.out.println("DEBUG vehicle count is " + vehicleCount);
    int busyCount = delegate.countActiveRentalsAndReservations(
        reservationModel.getVtname(),
        reservationModel.getLocation(),
        reservationModel.getCity(),
        reservationModel.getStartDate(),
        reservationModel.getEndDate());
    // System.out.println("DEBUG busy count is " + busyCount);

    if (busyCount >= vehicleCount) {
      System.out.println("No vehicles of that type available for those dates and branch. Sorry :(");
      return null;
    }

    if (!addToDb) {
      return reservationModel;
    }

    if (delegate.insertReservation(reservationModel)) {
      System.out.println("\nReservation successfully created!");
      System.out.printf("Confirmation number: %s%n", reservationModel.getConfno());
      System.out.printf(
          "Customer's Driver's License: %s%n", reservationModel.getDlicense());
      System.out.printf("Vehicle Type: %s%n", reservationModel.getVtname());
      System.out.printf("Branch location: %s%n", reservationModel.getLocation());
      System.out.printf("Branch city: %s%n", reservationModel.getCity());
      System.out.printf("Start date: %s%n", reservationModel.getStartDate().toString());
      System.out.printf("End date: %s%n", reservationModel.getEndDate().toString());
      return reservationModel;
    } else {
      Util.printWarning("Reservation registration failed!");
      return null;
    }
  }

  private void handleNewCustomer(Optional<String> dlicenseOptional) {
    System.out.println("\nNew Customer Form");
    CustomerModel customerModel = new CustomerModel();
    dlicenseOptional.ifPresent(customerModel::setDlicense);
    customerModel.readCustomerInfo(bufferedReader);

    if (delegate.insertCustomer(customerModel)) {
      System.out.println("Customer successfully registered!\n");
    } else {
      Util.printWarning("Customer registration failed.\n");
    }
  }

  private void handleAvailableVehicles() {
    //create empty model
    VehicleModel vm = new VehicleModel("", "", 0, "", "", "", "");
    vm.readVehicleInfo(bufferedReader);

    // FIXME: Add city
    ArrayList<VehicleModel> availVehicles = delegate.fetchAvailableVehicles(
        vm.getVtname(), vm.getLocation(), vm.getCity());

    // Removing excess vehicles.
    int toRemove = delegate.countActiveRentalsAndReservations(
        vm.getVtname(), vm.getLocation(), vm.getCity(), vm.getStartDate(), vm.getEndDate());

    while (availVehicles.size() > 0 && toRemove > 0) {
      toRemove--;
      availVehicles.remove(availVehicles.size() - 1);
    }

    int numVehicles = availVehicles.size();
    if(numVehicles > 0){
      System.out.printf("Currently: %d available vehicles%n", numVehicles);
      //check if they want to see the vehicles
      String response = Util.genericStringRead(
          bufferedReader,
          String.format("Would you like to see the %d vehicles? [y/n] ", numVehicles),
          FieldSizes.MAXIMUM_VTNAME_SIZE,
          false);

      if(response.toLowerCase().equals("y") || response.toLowerCase().equals("yes")) {
        for(VehicleModel availableVehicles : availVehicles){
          System.out.println(
              "vehicle license: " + availableVehicles.getVlicense() +
              " location: " + availableVehicles.getLocation() +
              " city: " + availableVehicles.getCity() +
              " type: " + availableVehicles.getVtname());
        }
      } else {
        System.out.println("Okay bye.");
      }

    } else {
      System.out.println("No available vehicles.");
    }
  }

  private void handleReturn() {
    ReturnModel returnModel = new ReturnModel();

    String in = "";
    while (!in.toLowerCase().equals("y") && !in.toLowerCase().equals("n")) {
      System.out.print("Do you have a rentID [y/n]? ");
      in = Util.readString(bufferedReader, 255, true).orElse("");
    }

    if (in.equals("y")) {
      returnModel.readRentID(bufferedReader);
      while (delegate.fetchRental(returnModel.getRentID()) == null) {
        System.out.println("Rental not found. Enter new rentID or type \"no\" in case");
        returnModel.readRentID(bufferedReader);
        if (returnModel.getRentID().toLowerCase().equals("no")) {
          returnModel.setRentID("");
          return;
        }
      }
    }

    RentModel rentalModel = delegate.fetchRental(returnModel.getRentID());
    // System.out.println("DEBUG: rentalModel = " + rentalModel);
    // System.out.println("DEBUG: rentalModel vlicense " + rentalModel.getVlicense());
    // System.out.println("DEBUG: rentalModel vtname " + rentalModel.getVtname());
    VehicleModel vehicle = null;
    while (vehicle == null) {
      vehicle = delegate.fetchVehicle(rentalModel.getVlicense());
    }
    VehicleTypeModel vehicleTypeModel = null;
    if (vehicleTypeModel == null) {
      vehicleTypeModel = delegate.fetchVehicleType(vehicle.getVtname());
    }

    returnModel.readOdometer(bufferedReader, vehicle.getOdometer());
    returnModel.readReturnDate(bufferedReader, rentalModel.getStartdate());
    returnModel.readFullTank(bufferedReader);
    returnModel.calculateRevenue(vehicleTypeModel, rentalModel.getStartdate(), returnModel.getReturnDate(), vehicle.getOdometer());

    // Insert into database.
    if (delegate.insertReturn(returnModel)) {
      while (!delegate.updateVehicleOdometer(rentalModel.getVlicense(), returnModel.getOdometer()));
      while (!delegate.updateVehicleStatus(rentalModel.getVlicense(), "A"));
      while (!delegate.updateReservationReturnDate(rentalModel.getConfno(), returnModel.getReturnDate()));
      while (!delegate.updateRentalReturnDate(rentalModel.getRentid(), returnModel.getReturnDate()));
      System.out.println("\nReturn Successful!");
      System.out.printf("Reservation confirmation number: %s%n", rentalModel.getRentid());
      System.out.printf("Date of Return: %s%n",returnModel.getReturnDate());
      System.out.printf("Total cost for rental: $%s%n", returnModel.getRevenue());
      System.out.println("Weekly costs: $" + returnModel.getWeeklyRateCharges() + "Weekly insurance costs: $"
              + returnModel.getWeeklyInsuranceRateCharges());
      System.out.println("Daily costs: $" + returnModel.getDayRateCharges() + "Daily insurance costs: $"
              + returnModel.getDailyInsuranceRateCharges());
      System.out.println("Hourly costs: $" + returnModel.getHourRateCharges() + "Hourly insurance costs: $"
              + returnModel.getHourlyInsuranceRateCharges());
      System.out.println("Distance costs per km: $" + returnModel.getKiloRateCharges());
    } else {
      Util.printWarning("Vehicle return failed.");
    }
  }

  private void handleQuit() {
    // TODO
    System.out.println("\n\nQuitting now. Bye!\n");
  }
}
