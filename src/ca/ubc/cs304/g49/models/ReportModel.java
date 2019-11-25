package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportModel {
        private Date date;
//        private HashMap<String, Integer> categoryPerVehicleType; // Category -> numRevenue
//        private HashMap<String, Integer> revenuePerCategory;  // Category -> revenue
        private String location;
        private String city;
        private Integer count;
        private Integer revenue;
        private String typename;
        public ReportModel(Date d, String loc, String cit, String vt, Integer cnt, Integer rev) {
            date = d;
            location = loc;
            city = cit;
            count = cnt;
            typename = vt;
            revenue = rev;
        }
        public Integer getRevenue(){
            return revenue;
        }

        public void printReportCompany(){
            System.out.printf("Company count: %d    Total revenue $%d%n", count, revenue);
        }

        public void printReportBranch(){
            System.out.printf("Location: %s    Vehicles returned: %d    Revenue: $%d %n",
                    location, count, revenue);
            }

        // location city vehiclename num vehicles revenue
        public void printReportBranchVehicleType(){
            System.out.printf("Location: %s    Vehicles name: %s    Vehicles returned: %d    Revenue: $%d %n",
                    location, typename, count, revenue);
        }


}
