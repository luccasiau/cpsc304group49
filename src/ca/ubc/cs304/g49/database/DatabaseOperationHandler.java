package ca.ubc.cs304.g49.database;

import ca.ubc.cs304.g49.delegates.CommandLineUiDelegate;
import ca.ubc.cs304.g49.models.CustomerModel;
import ca.ubc.cs304.g49.models.ReservationModel;
import ca.ubc.cs304.g49.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DatabaseOperationHandler implements CommandLineUiDelegate {
  private DatabaseConnectionHandler dbConnectionHandler;

  public DatabaseOperationHandler(DatabaseConnectionHandler dbConnectionHandler) {
    this.dbConnectionHandler = dbConnectionHandler;
  }

  @Override
  public boolean insertCustomer(CustomerModel model) {
    try {
      // FIXME: Is this safe to sql-injection?
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
    return false;  // stub
  }

  @Override
  public boolean dlicenseExist(String dlicense) {
    try {
      PreparedStatement ps = dbConnectionHandler.getConnection()
          .prepareStatement("SELECT * FROM customer WHERE dlicense = ?");
      ps.setString(1, dlicense);

      ResultSet rs = ps.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      dbConnectionHandler.rollbackConnection();
      Util.printException(e.getMessage());
      return false;
    }
  }
}
