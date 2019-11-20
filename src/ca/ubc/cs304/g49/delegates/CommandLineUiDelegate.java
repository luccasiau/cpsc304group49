package ca.ubc.cs304.g49.delegates;

import ca.ubc.cs304.g49.models.CustomerModel;
import ca.ubc.cs304.g49.models.ReservationModel;

/**
 * Delegate for the methods involved in interacting with command line. This is
 * following the example from the tutorial.
 */
public interface CommandLineUiDelegate {
  // TODO: Add functions here as needed.
  boolean insertCustomer(CustomerModel customerModel);
  boolean insertReservation(ReservationModel reservationModel);
  boolean dlicenseExist(String dlicense);
}
