package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.sql.Date;

public class ReturnModel {
    private String rentID;
    private Date returnDate;
    private int odometer;
    private float revenue;
    private Boolean fullTank;

    public void readRentID(BufferedReader reader) {
        rentID = Util.genericStringRead(
                reader,
                "Enter RentID: ",
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
        System.out.print("Enter odometer reading: ");
        odometer = Util.readInteger(
                reader,
                false)
                .orElse(1000);
    }

    public void calculateRevenue(VehicleTypeModel vehicleTypeModel, Date startDate, Date returnDate) {
        long hoursInMS = 1000 * 60 * 60;
        long daysInMS = hoursInMS * 24;
        long weeksInMS = daysInMS * 7;
        long millisecondsElapsed = returnDate.getTime() - startDate.getTime();

        long weeksElapsed = millisecondsElapsed / weeksInMS;
        millisecondsElapsed = millisecondsElapsed % weeksInMS;

        long daysElapsed = millisecondsElapsed / daysInMS;
        millisecondsElapsed = millisecondsElapsed % daysInMS;

        long hoursElapsed = millisecondsElapsed / hoursInMS;

        revenue = weeksElapsed * vehicleTypeModel.getWeeklyRate()
                + weeksElapsed * vehicleTypeModel.getWinsuranceRate()
                + daysElapsed * vehicleTypeModel.getDayRate()
                + daysElapsed * vehicleTypeModel.getDinsuranceRate()
                + hoursElapsed * vehicleTypeModel.getHourRate()
                + hoursElapsed * vehicleTypeModel.getHinsuranceRate();
    }

    public void readFullTank(BufferedReader reader) {
        System.out.print("Is the tank full? [True] [False] ");
        fullTank = Util.readBoolean(
                reader,
                false)
                .orElse(Boolean.TRUE);
    }

    public String getRentID() { return rentID; }

    public Date getReturnDate() { return returnDate; }

    public int getOdometer() { return odometer; }

    public float getRevenue() { return revenue; }

    public boolean isFullTank() { return fullTank; }
}
