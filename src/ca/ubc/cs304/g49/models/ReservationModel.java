package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.sql.Date;
import java.util.Optional;

public class ReservationModel {
  private String confno;
  private String vtname;
  private String dlicense;
  private String location;
  private String city;
  private Date startDate;
  private Date endDate;

  public ReservationModel() {
    vtname = null;
    dlicense = null;
    location = null;
    city = null;
    startDate = null;
    endDate = null;
    confno = Util.randomHash(10);
  }

  public String getVtname() {
    return vtname;
  }

  public String getDlicense() {
    return dlicense;
  }

  public String getLocation() {
    return location;
  }

  public String getCity() {
    return city;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getConfno() {
    return confno;
  }

  public void readReservationInfo(BufferedReader reader) {
    readDlicense(reader);
    readVtname(reader);
    readLocation(reader);
    readCity(reader);
    readStartDate(reader);
    readEndDate(reader);
  }

  public void readDlicense(BufferedReader reader) {
    dlicense = Util.genericStringRead(
        reader,
        "Enter customer's driver's license number: ",
        FieldSizes.MAXIMUM_DLICENSE_SIZE,
        false);
  }

  private void readVtname(BufferedReader reader) {
    vtname = Util.genericStringRead(
        reader,
        "Enter vehicle type name: ",
        FieldSizes.MAXIMUM_VTNAME_SIZE,
        false);
  }

  private void readLocation(BufferedReader reader) {
    location = Util.genericStringRead(
        reader, "Enter branch location: ", FieldSizes.MAXIMUM_LOCATION_SIZE, false);
  }

  private void readCity(BufferedReader reader) {
    city = Util.genericStringRead(
        reader, "Enter branch location: ", FieldSizes.MAXIMUM_CITY_SIZE, false);
  }

  private void readStartDate(BufferedReader reader) {
    startDate = Util.genericDateRead(
        reader,
        "Enter start date (yyyy-mm-dd): ",
        Date.valueOf("1990-01-01"));
  }

  private void readEndDate(BufferedReader reader) {
    endDate = Util.genericDateRead(reader, "Enter end date (yyyy-mm-dd): ", startDate);
  }
}
