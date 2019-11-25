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
    public String getVtname() {
        return vtname;
    }

    private String vtname;
    private String features;

    public float getWeeklyRate() {
        return weeklyRate;
    }

    public float getDayRate() {
        return dayRate;
    }

    public float getHourRate() {
        return hourRate;
    }

    public float getKiloRate() {
        return kiloRate;
    }

    public float getWinsuranceRate() {
        return winsuranceRate;
    }

    public float getHinsuranceRate() {
        return hinsuranceRate;
    }

    public float getDinsuranceRate() {
        return dinsuranceRate;
    }

    private float weeklyRate;
    private float dayRate;
    private float hourRate;
    private float kiloRate;
    private float winsuranceRate;
    private float hinsuranceRate;
    private float dinsuranceRate;

    public VehicleTypeModel(String vtname,
                            String features,
                            float weeklyRate,
                            float dayRate,
                            float hourRate,
                            float kiloRate,
                            float winsuranceRate,
                            float dinsuranceRate,
                            float hinsuranceRate) {
        this.vtname = vtname;
        this.features = features;
        this.weeklyRate = weeklyRate;
        this.dayRate = dayRate;
        this.hourRate = hourRate;
        this.kiloRate = kiloRate;
        this.winsuranceRate = winsuranceRate;
        this.hinsuranceRate = hinsuranceRate;
        this.dinsuranceRate = dinsuranceRate;
    }

    public VehicleTypeModel() {
        vtname = null;
        dayRate = 0.0f;
        hourRate = 0.0f;
        kiloRate = 0.0f;
        winsuranceRate = 0.0f;
        hinsuranceRate = 0.0f;
        dinsuranceRate = 0.0f;
    }




}

