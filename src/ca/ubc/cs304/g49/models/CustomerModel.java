package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.util.Optional;

public class CustomerModel {
  private String dlicense;
  private String name;
  private long cellNum;
  private String address;

  public String getDlicense() {
    return dlicense;
  }

  public String getName() {
    return name;
  }

  public long getCellNum() {
    return cellNum;
  }

  public String getAddress() {
    return address;
  }

  public void readCustomerInfo(BufferedReader reader) {
    readDlicense(reader);
    readName(reader);
    readCellNum(reader);
    readAddress(reader);
  }

  private void readDlicense(BufferedReader reader) {
    while (true) {
      System.out.print("Enter customer's driver's license number: ");
      Optional<String> dLicenseOptional =
          Util.readString(reader, FieldSizes.MAXIMUM_DLICENSE_SIZE, false);
      if (dLicenseOptional.isPresent()) {
        dlicense = dLicenseOptional.get();
        break;
      }
    }
  }

  private void readName(BufferedReader reader) {
    while (true) {
      System.out.print("Enter customer's name: ");
      Optional<String> nameOptional =
          Util.readString(reader, FieldSizes.MAXIMUM_CUSTOMER_NAME_SIZE, false);
      if (nameOptional.isPresent()) {
        name = nameOptional.get();
        break;
      }
    }
  }

  private void readCellNum(BufferedReader reader) {
    while (true) {
      System.out.print("Enter customer's cell phone number: ");
      Optional<Long> cellNumOptional =
          Util.readLong(reader, true);
      if (cellNumOptional.isPresent()) {
        cellNum = cellNumOptional.get();
        break;
      }
    }
  }

  private void readAddress(BufferedReader reader) {
    while (true) {
      System.out.print("Enter customer's address: ");
      Optional<String> addressOptional =
          Util.readString(reader, FieldSizes.MAXIMUM_ADDRESS_SIZE, true);
      if (addressOptional.isPresent()) {
        address = addressOptional.get();
        break;
      }
    }
  }
}
