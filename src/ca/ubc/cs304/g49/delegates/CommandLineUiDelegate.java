package ca.ubc.cs304.g49.delegates;

import ca.ubc.cs304.g49.models.CustomerModel;
import ca.ubc.cs304.g49.models.RentModel;
import ca.ubc.cs304.g49.models.ReservationModel;
import ca.ubc.cs304.g49.models.VehicleModel;

/**
 * Delegate for the methods involved in interacting with command line. This is
 * following the example from the tutorial.
 */
public interface CommandLineUiDelegate {
  // TODO: Add functions here as needed.
  boolean insertCustomer(CustomerModel customerModel);
  boolean insertReservation(ReservationModel reservationModel);
  boolean insertRent(RentModel rentModel);
  boolean dlicenseExist(String dlicense);
  boolean confnoExist(String confno);
  boolean updateVehicleStatus(String vlicense, String status);
  ReservationModel fetchReservation(String confno);
  VehicleModel fetchVehicleFromTypeAndBranch(String vtname, String location, String city);
}
