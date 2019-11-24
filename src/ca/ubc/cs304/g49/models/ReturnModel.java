package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.sql.Date;

public class ReturnModel {
    private String rentID;
    private Date returnDate;
    private int odometer;
    private double revenue;
    private boolean fullTank;

    public void readRentID(BufferedReader reader) {
        rentID = Util.genericStringRead(
                reader,
                "Enter your RentID",
                FieldSizes.MAXIMUM_RENTID_SIZE,
                false);
    }

    public void readReturnDate(BufferedReader reader, Date startdate) {
        returnDate = Util.genericDateRead(
                reader,
                "Enter return date (yyyy-mm-dd): ",
                startdate);
    }

    public void readOdometer(BufferedReader reader) {
        odometer = Util.readInteger(
                reader,
                false)
                .orElse(1000);
    }

    public void readRevenue(BufferedReader reader) {

    }

    public void readFullTank(BufferedReader reader) {

    }

    public String getRentID() { return rentID; }

    public Date getReturnDate() { return returnDate; }

    public int getOdometer() { return odometer; }

    public double getRevenue() { return revenue; }

    public boolean isFullTank() { return fullTank; }
}
