package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.sql.Date;

public class ReturnModel {
    private String rentID;
    private Date returnDate;
    private int odometer;
    private Boolean fullTank;
    private float revenue;

    public float getWeeklyRateCharges() {
        return weeklyRateCharges;
    }

    public float getDayRateCharges() {
        return dayRateCharges;
    }

    public float getHourRateCharges() {
        return hourRateCharges;
    }

    public float getKiloRateCharges() {
        return kiloRateCharges;
    }

    public float getWeeklyInsuranceRateCharges() {
        return weeklyInsuranceRateCharges;
    }

    public float getHourlyInsuranceRateCharges() {
        return hourlyInsuranceRateCharges;
    }

    public float getDailyInsuranceRateCharges() {
        return dailyInsuranceRateCharges;
    }

    private float weeklyRateCharges = 0.0f;
    private float dayRateCharges = 0.0f;
    private float hourRateCharges = 0.0f;
    private float kiloRateCharges = 0.0f;
    private float weeklyInsuranceRateCharges = 0.0f;
    private float hourlyInsuranceRateCharges = 0.0f;
    private float dailyInsuranceRateCharges = 0.0f;

    public ReturnModel() {}

    public ReturnModel(String rentID, Date returnDate, int odometer, float revenue, Boolean fullTank) {
        this.rentID = rentID;
        this.returnDate = returnDate;
        this.odometer = odometer;
        this.revenue = revenue;
        this.fullTank = fullTank;
    }

    public void setRentID(String rentID) {
        this.rentID = rentID;
    }

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

    public void readOdometer(BufferedReader reader, int prevOdometer) {
        odometer = Util.genericIntegerRead(
                reader,
                "Enter odometer reading: ",
                false,
                prevOdometer);
    }

    public void calculateRevenue(VehicleTypeModel vehicleTypeModel, Date startDate, Date returnDate, int startingOdometer) {
        long hoursInMS = 1000 * 60 * 60;
        long daysInMS = hoursInMS * 24;
        long weeksInMS = daysInMS * 7;
        long millisecondsElapsed = returnDate.getTime() - startDate.getTime();

        long weeksElapsed = millisecondsElapsed / weeksInMS;
        millisecondsElapsed = millisecondsElapsed % weeksInMS;

        long daysElapsed = millisecondsElapsed / daysInMS;
        millisecondsElapsed = millisecondsElapsed % daysInMS;

        long hoursElapsed = millisecondsElapsed / hoursInMS;

        int distanceTravelled = odometer - startingOdometer;

        weeklyRateCharges = weeksElapsed * vehicleTypeModel.getWeeklyRate();
        weeklyInsuranceRateCharges = weeksElapsed * vehicleTypeModel.getWinsuranceRate();
        dayRateCharges = daysElapsed * vehicleTypeModel.getDayRate();
        dailyInsuranceRateCharges = daysElapsed * vehicleTypeModel.getDinsuranceRate();
        hourRateCharges = hoursElapsed * vehicleTypeModel.getHourRate();
        hourlyInsuranceRateCharges = hoursElapsed * vehicleTypeModel.getHinsuranceRate();
        kiloRateCharges = distanceTravelled * vehicleTypeModel.getKiloRate();

        revenue = weeklyRateCharges
                + weeklyInsuranceRateCharges
                + dayRateCharges
                + dailyInsuranceRateCharges
                + hourRateCharges
                + hourlyInsuranceRateCharges
                + kiloRateCharges;
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