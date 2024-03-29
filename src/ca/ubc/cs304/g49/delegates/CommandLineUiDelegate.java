package ca.ubc.cs304.g49.delegates;

import ca.ubc.cs304.g49.models.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Delegate for the methods involved in interacting with command line. This is
 * following the example from the tutorial.
 */
public interface CommandLineUiDelegate {
  // TODO: Add functions here as needed.
  boolean insertCustomer(CustomerModel customerModel);
  boolean insertReservation(ReservationModel reservationModel);
  boolean insertRent(RentModel rentModel);
  boolean insertReturn(ReturnModel returnModel);
  boolean dlicenseExist(String dlicense);
  boolean confnoExist(String confno);
  boolean updateVehicleStatus(String vlicense, String status);
  boolean updateVehicleOdometer(String vlicense, int odometer);
  boolean updateReservationReturnDate(String confno, Date returnDate);
  boolean updateRentalReturnDate(String rentID, Date returnDate);
  ReservationModel fetchReservation(String confno);
  RentModel fetchRental(String rentId);
  ReturnModel fetchReturn(String rentId);
  VehicleTypeModel fetchVehicleType(String vtname);
  VehicleModel fetchVehicle(String vlicense);
  VehicleModel fetchVehicleFromTypeAndBranch(String vtname, String location, String city);
  ArrayList<VehicleModel> fetchAllVehicles();
  ArrayList<VehicleModel>  fetchAvailableVehicles(
      String vtname, String location, String city);
  int countActiveRentalsAndReservations(
      String vtname, String location, String city, Date start, Date end);

  // daily return report (vtname + branch / branch / company wide)
  void generateReturnReportPerVehicleBranch(Date curr);
  void  generateReturnReportBranch(Date date);
  void  generateReturnCompany(Date date);

  //return for branch
  void generateReturnForBranchByVehicle(String location, String city, Date date);
  void generateReturnForBranch(String location, String city, Date date);

    // daily rental report (vtname + branch / branch / company wide)
    void generateRentalReportPerVehicleBranch(Date curr);
    void  generateRentalReportBranch(Date date);
    void  generateRentalCompany(Date date);

    //rental for branch
    void generateRentalForBranchByVehicle(String location, String city, Date date);
    void generateRentalForBranch(String location, String city, Date date);
}
