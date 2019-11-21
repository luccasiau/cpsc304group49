package ca.ubc.cs304.g49.models;

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
}
