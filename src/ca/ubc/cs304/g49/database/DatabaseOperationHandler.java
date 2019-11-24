package ca.ubc.cs304.g49.database;

import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;
import ca.ubc.cs304.g49.models.CustomerModel;
import ca.ubc.cs304.g49.models.RentModel;
import ca.ubc.cs304.g49.models.ReservationModel;
import ca.ubc.cs304.g49.models.VehicleModel;
import ca.ubc.cs304.g49.util.Util;

import java.sql.*;
import java.util.ArrayList;

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
  public VehicleModel fetchVehicleFromTypeAndBranch(String vtname, String location, String city) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement(
              "SELECT * FROM vehicle " +
                   "WHERE vtname = ?" +
                  "   AND location = ?" +
                  "   AND city = ?" +
                  "   AND status = ?");
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
  public ArrayList<VehicleModel> fetchAvailableVehicles(String vtname, String location, Date start, Date end){
    ArrayList<VehicleModel> result = new ArrayList<>();
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
              .prepareStatement(
                      "SELECT * FROM vehicle " +
                              "WHERE vtname = ?" +
                              "   AND location = ?");
      // FIXME: Add city to this query.
      ps.setString(1, vtname);
      ps.setString(2, location);
//      ps.setDate(3, start);
//      ps.setDate(4, end);

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
  @Override
  public int countActiveReservations(String vtname, String location, String city, Date start, Date end) {
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
                   "     (enddate >= ? AND enddate <= ?))");
      ps.setString(1, location);
      ps.setString(2, city);
      ps.setString(3, vtname);
      ps.setDate(4, start);
      ps.setDate(5, end);
      ps.setDate(6, start);
      ps.setDate(7, end);

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
  @Override
  public int countActiveRentalsNoConf(String vtname, String location, String city, Date start, Date end) {
    try {
      // FIXME: I need to set confno to NULL.
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
                   "      (R.enddate >= ? AND R.enddate <= ?))");
      ps.setString(1, location);
      ps.setString(2, city);
      ps.setString(3, vtname);
      ps.setDate(4, start);
      ps.setDate(5, end);
      ps.setDate(6, start);
      ps.setDate(7, end);

      // System.out.println("DEBUG: ps");

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
}
