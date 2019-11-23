package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.sql.Date;
import java.util.Optional;

/**
 * Class for handling reservations in memory.
 *
 * This has a readReservationInfo method that facilitates reading input from a
 * provided BufferedReader.
 */
public class VehicleTypeModel {
    private String vtname;
    private String features;
    private double dayRate;
    private double hourRate;
    private double kiloRate;
    private double winsuranceRate;
    private double hinsuranceRate;
    private double dinsuranceRate;

    public VehicleTypeModel() {
        vtname = null;
        dayRate = 0.0;
        hourRate = 0.0;
        kiloRate = 0.0;
        winsuranceRate = 0.0;
        hinsuranceRate = 0.0;
        dinsuranceRate = 0.0;
    }




}

