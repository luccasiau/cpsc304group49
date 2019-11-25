package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.sql.Date;

/**
 * Class for handling Vehicles in memory.
 *
 * This does not do any fancy operation and will mostly be used for
 * quick storage.
 */
public class VehicleModel {
  private String vlicense;
  private String vtname;
  private int odometer;
  private String status;
  private String colour;
  private String location;
  private String city;

  private Date start;
  private Date end;

  public VehicleModel(
      String vlicense,
      String vtname,
      int odometer,
      String status,
      String colour,
      String location,
      String city) {
    this.vlicense = vlicense;
    this.vtname = vtname;
    this.odometer = odometer;
    this.status = status;
    this.colour = colour;
    this.location = location;
    this.city = city;
  }

  public String getVlicense() {
    return vlicense;
  }

  public String getVtname() {
    return vtname;
  }

  public int getOdometer() {
    return odometer;
  }

  public String getStatus() {
    return status;
  }

  public String getColour() {
    return colour;
  }

  public String getLocation() {
    return location;
  }

  public String getCity() {
    return city;
  }

  public Date getStartDate(){
    return start;
  }

  public Date getEndDate(){
    return end;
  }
  public void readVehicleInfo(BufferedReader reader) {
     vtname = Util.genericStringRead(
            reader,
            "Enter car type's name, must be one of [SUV] [Sedan] [Crossover] [Coupe] [Convertible]: ",
            FieldSizes.MAXIMUM_VTNAME_SIZE,
            true);
     location = Util.genericStringRead(
            reader,
            "Enter location: ",
            FieldSizes.MAXIMUM_LOCATION_SIZE,
            true);
     city = Util.genericStringRead(
            reader,
            "Enter branch city: ",
            FieldSizes.MAXIMUM_CITY_SIZE,
            true);
     start = Util.genericDateRead(
            reader,
            "Enter start date (yyyy-mm-dd): ",
            true,
            Date.valueOf("1990-01-01"));
     if (start == null) return;
     end = Util.genericDateRead(
            reader,
            "Enter end date (yyyy-mm-dd): ",
            true,
            start);

  }

}
