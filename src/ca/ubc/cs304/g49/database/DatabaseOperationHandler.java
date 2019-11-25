package ca.ubc.cs304.g49.database;

import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;
import ca.ubc.cs304.g49.models.*;
import ca.ubc.cs304.g49.util.Util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


public class DatabaseOperationHandler implements CommandLineUiDelegate {
  private DatabaseConnectionHandler dbConnectionHandler;

  public DatabaseOperationHandler(DatabaseConnectionHandler dbConnectionHandler) {
    this.dbConnectionHandler = dbConnectionHandler;
  }

  @Override
  public boolean insertCustomer(CustomerModel model) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("INSERT INTO customer VALUES (?, ?, ?, ?)");
      ps.setString(1, model.getDlicense());
      ps.setString(2, model.getName());
      if (model.getCellNum() == 0) {
        ps.setNull(3, Types.INTEGER);
      } else {
        ps.setLong(3, model.getCellNum());
      }
      if (model.getAddress().length() == 0) {
        ps.setNull(4, Types.VARCHAR);
      } else {
        ps.setString(4, model.getAddress());
      }

      ps.executeUpdate();
      dbConnectionHandler.getConnection().commit();
      ps.close();
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }

    return true;
  }

  @Override
  public boolean insertReservation(ReservationModel reservationModel) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("INSERT INTO reservation VALUES (?, ?, ?, ?, ?, ?, ?)");

      ps.setString(1, reservationModel.getConfno());
      ps.setString(2, reservationModel.getDlicense());
      ps.setString(3, reservationModel.getVtname());
      ps.setString(4, reservationModel.getLocation());
      ps.setString(5, reservationModel.getCity());
      ps.setDate(6, reservationModel.getStartDate());
      ps.setDate(7, reservationModel.getEndDate());

      ps.executeUpdate();
      dbConnectionHandler.getConnection().commit();
      ps.close();

    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public boolean insertRent(RentModel rentModel) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("INSERT INTO rent VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
      ps.setString(1, rentModel.getRentid());
      ps.setString(2, rentModel.getVlicense());
      ps.setString(3, rentModel.getDlicense());
      if (rentModel.getConfno() == null || rentModel.getConfno().length() == 0) {
        ps.setNull(4, Types.VARCHAR);
      } else {
        ps.setString(4, rentModel.getConfno());
      }
      ps.setDate(5, rentModel.getStartdate());
      ps.setDate(6, rentModel.getEnddate());
      ps.setString(7, rentModel.getCardname());
      ps.setString(8, rentModel.getCardno());
      ps.setInt(9, rentModel.getExpdate());

      ps.executeUpdate();
      dbConnectionHandler.getConnection().commit();
      ps.close();

      return true;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean insertReturn(ReturnModel returnModel) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement("INSERT INTO return VALUES (?, ?, ?, ?, ?)");
      ps.setString(1, returnModel.getRentID());
      ps.setDate(2, returnModel.getReturnDate());
      ps.setInt(3, returnModel.getOdometer());
      ps.setBoolean(4, returnModel.isFullTank());
      ps.setFloat(5, returnModel.getRevenue());

      ps.executeUpdate();
      dbConnectionHandler.getConnection().commit();
      ps.close();

      return true;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean dlicenseExist(String dlicense) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("SELECT * FROM customer WHERE dlicense = ?");
      ps.setString(1, dlicense);

      ResultSet rs = ps.executeQuery();

      boolean hasNext = rs.next();
      dbConnectionHandler.getConnection().commit();
      ps.close();

      return hasNext;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean confnoExist(String confno) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("SELECT * FROM reservation WHERE confno = ?");
      ps.setString(1, confno);

      ResultSet rs = ps.executeQuery();

      boolean hasNext = rs.next();
      dbConnectionHandler.getConnection().commit();
      ps.close();

      return hasNext;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean updateVehicleStatus(String vlicense, String status) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("UPDATE vehicle SET status = ? WHERE vlicense = ?");
      ps.setString(1, status);
      ps.setString(2, vlicense);

      int rowCount = ps.executeUpdate();
      if (rowCount == 0) {
        Util.printWarning(
            String.format("Vehicle with license %s does not exist!", vlicense));
      }

      dbConnectionHandler.getConnection().commit();
      ps.close();
      return true;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean updateVehicleOdometer(String vlicense, int odometer) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement("UPDATE vehicle SET odometer = ? WHERE vlicense = ?");
      ps.setInt(1, odometer);
      ps.setString(2, vlicense);

      int rowCount = ps.executeUpdate();
      if (rowCount == 0) {
        Util.printWarning(
                String.format("Vehicle with license %s does not exist!", vlicense));
      }

      dbConnectionHandler.getConnection().commit();
      ps.close();
      return true;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean updateReservationReturnDate(String confno, Date returnDate) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement("UPDATE reservation SET enddate = ? WHERE confno = ?");
      ps.setDate(1, returnDate);
      ps.setString(2, confno);

      int rowCount = ps.executeUpdate();
      if (rowCount == 0) {
        Util.printWarning(
                String.format("Reservation with confno %s does not exist!", confno));
      }

      dbConnectionHandler.getConnection().commit();
      ps.close();
      return true;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean updateRentalReturnDate(String rentID, Date returnDate) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement("UPDATE rent SET enddate = ? WHERE rentid = ?");
      ps.setDate(1, returnDate);
      ps.setString(2, rentID);

      int rowCount = ps.executeUpdate();
      if (rowCount == 0) {
        Util.printWarning(
                String.format("Rental with rentID %s does not exist!", rentID));
      }

      dbConnectionHandler.getConnection().commit();
      ps.close();
      return true;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }

  @Override
  public ReservationModel fetchReservation(String confno) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("SELECT * FROM reservation WHERE confno = ?");
      ps.setString(1, confno);

      ResultSet rs = ps.executeQuery();

      ReservationModel reservationModel = null;
      if (rs.next()) {
        // System.out.println("DEBUG: Found something!");
        reservationModel = new ReservationModel(
            rs.getString("confno"),
            rs.getString("vtname"),
            rs.getString("dlicense"),
            rs.getString("location"),
            rs.getString("city"),
            rs.getDate("startdate"),
            rs.getDate("enddate"));
      }

      rs.close();
      ps.close();
      return reservationModel;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return null;
    }
  }

  @Override
  public RentModel fetchRental(String rentId) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement("SELECT * FROM rent WHERE rentid = ?");
      ps.setString(1, rentId);

      ResultSet rs = ps.executeQuery();

      RentModel rentModel = null;
      if (rs.next()) {
        rentModel = new RentModel(
                rs.getString("rentid"),
                rs.getString("vlicense"),
                rs.getString("dlicense"),
                rs.getString("confno"),
                rs.getDate("startdate"),
                rs.getDate("enddate"),
                rs.getString("cardname"),
                rs.getString("cardno"),
                rs.getInt("expdate"));
      }
      rs.close();
      ps.close();
      return rentModel;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return null;
    }
  }

  @Override
  public ReturnModel fetchReturn(String rentId) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement("SELECT * FROM return WHERE rentid = ?");
      ps.setString(1, rentId);

      ResultSet rs = ps.executeQuery();

      ReturnModel returnModel = null;
      if (rs.next()) {
        returnModel = new ReturnModel(
                rs.getString("rentid"),
                rs.getDate("returnDate"),
                rs.getInt("odometer"),
                rs.getFloat("revenue"),
                rs.getBoolean("fullTank"));
      }
      rs.close();
      ps.close();
      return returnModel;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return null;
    }
  }

  @Override
  public VehicleTypeModel fetchVehicleType(String vtname) {
    try {
      // System.out.println("DEBUG: vtname = " + vtname);
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement("SELECT * FROM vehicletype WHERE vtname = ?");
      ps.setString(1, vtname);

      ResultSet rs = ps.executeQuery();

      VehicleTypeModel vehicleTypeModel = null;
      if (rs.next()) {
        // System.out.println("DEBUG: FOUND SOMETHING");
        vehicleTypeModel = new VehicleTypeModel(
                rs.getString("vtname"),
                rs.getString("features"),
                rs.getFloat("weeklyRate"),
                rs.getFloat("dailyRate"),
                rs.getFloat("hourlyRate"),
                rs.getFloat("kiloRate"),
                rs.getFloat("weeklyInsRate"),
                rs.getFloat("dailyInsRate"),
                rs.getFloat("hourlyInsRate"));
        // System.out.println("DEBUG vtmodel = " + vehicleTypeModel);
      }

      rs.close();
      ps.close();
      return vehicleTypeModel;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return null;
    }
  }

  @Override
  public VehicleModel fetchVehicle(String vlicense) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement("SELECT * FROM vehicle WHERE vlicense = ?");
      ps.setString(1, vlicense);
      ResultSet rs = ps.executeQuery();

      VehicleModel vehicle = null;
      if (rs.next()) {
        vehicle = new VehicleModel(
                rs.getString("vlicense"),
                rs.getString("vtname"),
                rs.getInt("odometer"),
                rs.getString("status"),
                rs.getString("colour"),
                rs.getString("location"),
                rs.getString("city"));
      }

      rs.close();
      ps.close();
      return vehicle;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return null;
    }
  }

  @Override
  public VehicleModel fetchVehicleFromTypeAndBranch(String vtname, String location, String city) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement(
              "SELECT * FROM vehicle " +
                   "WHERE vtname = ?" +
                  "   AND location = ?" +
                  "   AND city = ?" +
                  "   AND status = ?" +
                  "   ORDER BY vlicense DESC");
      ps.setString(1, vtname);
      ps.setString(2, location);
      ps.setString(3, city);
      ps.setString(4, "A");

      ResultSet rs = ps.executeQuery();

      VehicleModel vehicleModel = null;
      if (rs.next()) {
        vehicleModel = new VehicleModel(
            rs.getString("vlicense"),
            rs.getString("vtname"),
            rs.getInt("odometer"),
            rs.getString("status"),
            rs.getString("colour"),
            rs.getString("location"),
            rs.getString("city"));
      }

      rs.close();
      ps.close();
      return vehicleModel;
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return null;
    }
  }

  @Override
  public ArrayList<VehicleModel> fetchAvailableVehicles(
      String vtname, String location, String city) {
    ArrayList<VehicleModel> result = new ArrayList<>();
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement(
                      "SELECT * FROM vehicle " +
                              "WHERE vtname = ?" +
                              "   AND location = ? AND city = ?" +
                              "   ORDER BY vlicense ASC");
      // FIXME: Add city to this query.
      ps.setString(1, vtname);
      ps.setString(2, location);
      ps.setString(3, city);

      ResultSet rs = ps.executeQuery();

      while(rs.next()){
        VehicleModel newVehicle =  new VehicleModel(rs.getString("vlicense"),
        rs.getString("vtname"),
                rs.getInt("odometer"),
                rs.getString("status"),
                rs.getString("colour"),
                rs.getString("location"),
                rs.getString("city"));
        result.add(newVehicle);
      }

      dbConnectionHandler.getConnection().commit();
      rs.close();
      ps.close();

    } catch (Exception e){
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return result;
    }

    return result;
  }

  /**
   * Counts the number of current reservations that intersect the time period
   * defined by [start, end] for that current location & city.
   */
  private int countActiveReservations(String vtname, String location, String city, Date start, Date end) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement(
              "SELECT COUNT(*) AS num FROM reservation" +
                   " WHERE location = ? " +
                   " AND   city = ? " +
                   " AND   vtname = ? " +
                   " AND ( " +
                   "     (startdate >= ? AND startdate <= ?) " +
                   "     OR " +
                   "     (enddate >= ? AND enddate <= ?) " +
                   "     OR " +
                   "     (enddate >= ? AND startdate <= ?))");
      ps.setString(1, location);
      ps.setString(2, city);
      ps.setString(3, vtname);
      ps.setDate(4, start);
      ps.setDate(5, end);
      ps.setDate(6, start);
      ps.setDate(7, end);
      ps.setDate(8, end);
      ps.setDate(9, start);

      // System.out.println("DEBUG: " + ps);

      ResultSet rs = ps.executeQuery();

      int cnt = 0;
      if (rs.next()) {
        cnt = rs.getInt("num");
      }

      dbConnectionHandler.getConnection().commit();
      rs.close();
      ps.close();

      // System.out.println("DEBUG. Resevation About to return: " + cnt);
      return cnt;
    } catch (Exception e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException("Reservation:" + e.getMessage());
      return 0;
    }
  }

  /**
   * Count number of rentals made for that type, date, and branch that
   * did not have any reservation confirmation number.
   */
  private int countActiveRentalsNoConf(String vtname, String location, String city, Date start, Date end) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement(
              "SELECT COUNT(*) as num FROM rent R, vehicle V" +
                   " WHERE R.vlicense = V.vlicense " +
                   " AND R.confno IS NULL " +
                   " AND   V.location = ? " +
                   " AND   V.city = ? " +
                   " AND   V.vtname = ? " +
                   " AND ( " +
                   "      (R.startdate >= ? AND R.startdate <= ?) " +
                   "      OR " +
                   "      (R.enddate >= ? AND R.enddate <= ?) " +
                   "      OR " +
                   "      (R.enddate >= ? AND R.startdate <= ?))");
      ps.setString(1, location);
      ps.setString(2, city);
      ps.setString(3, vtname);
      ps.setDate(4, start);
      ps.setDate(5, end);
      ps.setDate(6, start);
      ps.setDate(7, end);
      ps.setDate(8, end);
      ps.setDate(9, start);

      ResultSet rs = ps.executeQuery();

      int cnt = 0;
      if (rs.next()) {
        cnt = rs.getInt("num");
      }

      dbConnectionHandler.getConnection().commit();
      rs.close();
      ps.close();

      // System.out.println("DEBUG. Rent about to return: " + cnt);
      return cnt;
    } catch (Exception e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException("Rental: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public int countActiveRentalsAndReservations(
      String vtname, String location, String city, Date start, Date end) {
    return countActiveRentalsNoConf(vtname, location, city, start, end) +
        countActiveReservations(vtname, location, city, start, end);
  }

  public void generateReturnCompany(Date date){
    HashSet<ReportModel>reports = new HashSet<ReportModel>();
    try {
      //Grouping by branch & vehicle type
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement(
                      "SELECT count(*), sum(R2.revenue) " +
                              "FROM Rent R, Return R2 " +
                              "WHERE R2.rentId = R.rentId AND R2.returnDate = ? "
              );
      ps.setDate(1, date); //set today date

      ResultSet rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        while(rs.next()) { //for each row


          ReportModel newReport = new ReportModel(date,
                  "", //loc
                  "",  //city
                  "", // vtname
                  rs.getInt(1), //count
                  rs.getInt(2)); //revenue
          reports.add(newReport);

        }
        for(ReportModel rm :  reports){
          rm.printReportCompany();
        }


      } else {
        System.out.printf("\nNo returned vehicles for date %s\n", date.toString());
      }
      dbConnectionHandler.getConnection().commit();
      rs.close();
      ps.close();
    } catch (Exception e){
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
    }}


  public void generateReturnReportBranch(Date date){
    HashSet<ReportModel>reports = new HashSet<ReportModel>();
    try {
      //Grouping by branch & vehicle type
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement(
                      "SELECT V.location, V.city, count(*), sum(R2.revenue) " +
                              "FROM Rent R, Vehicle V, Return R2 " +
                              "WHERE R2.rentId = R.rentId AND R2.returnDate = ? AND R.vlicense = V.vlicense " +
                              "GROUP BY V.location, V.city"
              );
      ps.setDate(1, date); //set today date

      ResultSet rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        while(rs.next()) { //for each row
          ReportModel newReport = new ReportModel(date,
                  rs.getString(1), //loc
                  rs.getString(2),  //city
                  "", // vtname
                  rs.getInt(3), //count
                  rs.getInt(4)); //revenue
          reports.add(newReport);
        }
        for(ReportModel rm :  reports){
          rm.printReportBranch();
        }

      } else {
        System.out.printf("\nNo returned vehicles for date %s\n", date.toString());
      }
      dbConnectionHandler.getConnection().commit();
      rs.close();
      ps.close();
    } catch (Exception e){
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
    }
  }
  @Override
  /**
   * @param date for which to generate return report
   * returns list of vehicles that were returned today
   * by getting list of vid of rentals that ended today
   */
  public void generateReturnReportPerVehicleBranch(Date date){
//    String date = new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString();
      HashSet<ReportModel>reports = new HashSet<ReportModel>();
      try {
        //Grouping by branch & vehicle type
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement(
                      "SELECT V.location, V.city, V.vtname, count(*), sum(R2.revenue) " +
                              "FROM Rent R, Vehicle V, Return R2 " +
                              "WHERE R2.rentId = R.rentId AND R2.returnDate = ? AND R.vlicense = V.vlicense " +
                              "GROUP BY V.location, V.city, V.vtname "
                              );
      ps.setDate(1, date); //set today date

      ResultSet rs = ps.executeQuery();
      if (rs.isBeforeFirst()) {
        while(rs.next()) { //for each row

          ReportModel newReport = new ReportModel(date,
                  rs.getString(1), //loc
                  rs.getString(2),  //city
                  rs.getString(3), // vtname
                  rs.getInt(4), //count
                  rs.getInt(5)); //revenue
          reports.add(newReport);

        }
        for(ReportModel rm :  reports){
          rm.printReportBranchVehicleType();
        }
        } else {
        System.out.printf("\nNo returned vehicles for date %s\n", date.toString());
      }
      dbConnectionHandler.getConnection().commit();
      rs.close();
      ps.close();
    } catch (Exception e){
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
    }
  }

  /**
   * generate daily return for branch, group by vtname
   * @param location
   * @param city
   * @param date
   */
 public void generateReturnForBranchByVehicle(String location, String city, Date date){
   HashSet<ReportModel>reports = new HashSet<ReportModel>();
   try {
     //Grouping by branch & vehicle type
     PreparedStatement ps = dbConnectionHandler.getConnection()
             .prepareStatement(
                     "SELECT V.vtname, count(*), sum(R2.revenue) " +
                             "FROM Rent R, Vehicle V, Return R2 " +
                             "WHERE R2.rentId = R.rentId AND R.vlicense = V.vlicense AND R2.returnDate = ? AND R.vlicense = V.vlicense AND V.location = ? AND V.city = ?" +
                             "GROUP BY V.vtname "
             );
     ps.setDate(1, date);
     ps.setString(2, location);
     ps.setString(3, city);
     ResultSet rs = ps.executeQuery();
     if (rs.isBeforeFirst()) {
       while(rs.next()) { //for each row

         ReportModel newReport = new ReportModel(date,
                 location, //loc
                 city,  //city
                 rs.getString(1), // vtname
                 rs.getInt(2), //count
                 rs.getInt(3)); //revenue
         reports.add(newReport);

       }
       System.out.printf("Returns for Branch %s, %s%n", location, city);
       for(ReportModel rm :  reports) {
         rm.printReturnReportBranch();
       }
     } else {
       System.out.printf("\nNo returned vehicles for date %s\n", date.toString());
     }
     dbConnectionHandler.getConnection().commit();
     rs.close();
     ps.close();
   } catch (Exception e){
     dbConnectionHandler.rollbackConnection();
     Util.printException(e.getMessage());
   }
  }
 public void generateReturnForBranch(String location, String city, Date date){
   HashSet<ReportModel>reports = new HashSet<ReportModel>();
   try {
     //Grouping by branch & vehicle type
     PreparedStatement ps = dbConnectionHandler.getConnection()
             .prepareStatement(
                     "SELECT count(*), sum(R2.revenue) " +
                             "FROM Rent R, Return R2, Vehicle V " +
                             "WHERE R2.rentId = R.rentId AND R.vlicense = V.vlicense AND R2.returnDate = ? AND V.location = ? AND V.city = ?"
             );
     ps.setDate(1, date);
     ps.setString(2, location);
     ps.setString(3, city);
     ResultSet rs = ps.executeQuery();
     if (rs.isBeforeFirst()) {
       while(rs.next()) { //for each row

         ReportModel newReport = new ReportModel(date,
                 location, //loc
                 city,  //city
                 "", // vtname
                 rs.getInt(1), //count
                 rs.getInt(2)); //revenue
         reports.add(newReport);

       }
       System.out.printf("Returns for Branch %s, %s%n", location, city);
       for(ReportModel rm :  reports){
         rm.printReturnEntireReportBranch();
       }


     } else {
       System.out.printf("\nNo returned vehicles for date %s\n", date.toString());
     }
     dbConnectionHandler.getConnection().commit();
     rs.close();
     ps.close();
   } catch (Exception e){
     dbConnectionHandler.rollbackConnection();
     Util.printException(e.getMessage());
   }
 }

}
