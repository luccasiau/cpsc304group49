package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.sql.Date;
import java.util.Optional;

/**
 * Class for handling Rents in memory.
 *
 * This has fields that are beyond the table (vtname, location, and city)
 * because it simplifies some stuff.
 */
public class RentModel {
  private String rentid;
  private String vlicense;
  private String dlicense;
  private String confno;
  private Date startdate;
  private Date enddate;
  private String cardname;
  private String cardno;
  private int expdate;
  private String vtname;    // this is not in the table
  private String location;  // this is not in the table
  private String city;      // this is not in the table

  public RentModel() {
    rentid = Util.randomHash(FieldSizes.MAXIMUM_RENTID_SIZE);
  }

  public void readConfno(BufferedReader reader) {
    confno = Util.genericStringRead(
        reader,
        "Enter reservation confirmation number: ",
        FieldSizes.MAXIMUM_CONFNO_SIZE,
        false);
  }

  public void readCardInfo(BufferedReader reader) {
    readCardname(reader);
    readCardno(reader);
    readExpdate(reader);
  }

  public void setFromReservation(ReservationModel reservation) {
    if (reservation == null) return;
    dlicense = reservation.getDlicense();
    startdate = reservation.getStartDate();
    enddate = reservation.getEndDate();
    vtname = reservation.getVtname();
    location = reservation.getLocation();
    city = reservation.getCity();
  }

  public void setVlicense(String vlicense) {
    this.vlicense = vlicense;
  }

  public void setConfno(String confno) {
    this.confno = confno;
  }

  public String getRentid() {
    return rentid;
  }

  public String getVlicense() {
    return vlicense;
  }

  public String getDlicense() {
    return dlicense;
  }

  public String getConfno() {
    return confno;
  }

  public Date getStartdate() {
    return startdate;
  }

  public Date getEnddate() {
    return enddate;
  }

  public String getCardname() {
    return cardname;
  }

  public String getCardno() {
    return cardno;
  }

  public int getExpdate() {
    return expdate;
  }

  public String getVtname() {
    return vtname;
  }

  public String getLocation() {
    return location;
  }

  public String getCity() {
    return city;
  }

  private void readCardno(BufferedReader reader) {
    cardno = Util.genericStringRead(
        reader,
        "Enter Credit Card Number: ",
        FieldSizes.MAXIMUM_CARDNO_SIZE,
        false);
  }

  private void readCardname(BufferedReader reader) {
    cardname = Util.genericStringRead(
        reader,
        "Enter Credit Card Name: ",
        FieldSizes.MAXIMUM_CARDNAME_SIZE,
        false);
  }

  private void readExpdate(BufferedReader reader) {
    while (true) {
      System.out.print("Enter Credit Card Expiration Date [yy/mm]: ");
      Optional<String> expdateOptional =
          Util.readString(reader, 5, false);
      int aux = -1;
      if (expdateOptional.isPresent()) aux = parseExpDate(expdateOptional.get());
      if (aux != -1) {
        expdate = aux;
        return;
      }
      System.out.print("Invalid Entry. ");
    }
  }

  private int parseExpDate(String expDate) {
    if (expDate.length() != 5) return -1;
    if (expDate.charAt(2) != '/') return -1;
    String[] vals = expDate.split("/");

    try {
      int yr = Integer.parseInt(vals[0]);
      int mo = Integer.parseInt(vals[1]);

      if (mo < 1 || mo > 12) return -1;
      return 100*yr + mo;
    } catch (NumberFormatException e) {
      return -1;
    } catch (Exception e) {
      return -1;
    }
  }
}
