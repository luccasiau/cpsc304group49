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
            System.out.printf("Company: total count:%d total revenue $%d%n", count, revenue);
        }

        public void printReportBranch(){
                System.out.printf("Num of vehicles returned %d revenue: $%d location: %s city: %s%n",
                        count, revenue, location, city);
            }


        public void printReportBranchVehicleType(){
            System.out.printf("Num of vehicles returned %d revenue: $%d vehicle category: %s location: %s city: %s%n",
                    count, revenue, typename, location, city);
        }


}
