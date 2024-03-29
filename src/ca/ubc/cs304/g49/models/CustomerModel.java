package ca.ubc.cs304.g49.models;

import ca.ubc.cs304.g49.util.FieldSizes;
import ca.ubc.cs304.g49.util.Util;

import java.io.BufferedReader;
import java.util.Optional;

/**
 * Class for handling customers in memory.
 *
 * This has a readCustomerInfo method that will facilitate reading input from
 * a provided BufferedReader.
 */
public class CustomerModel {
  private String dlicense;
  private String name;
  private long cellNum;
  private String address;

  public void setDlicense(String dlicense) {
    this.dlicense = dlicense;
  }

  /**
   * Will ask the user to provide values to some fields via a reader.
   *
   * @param reader: some BufferedReader. Likely set to STDIN.
   */
  public void readCustomerInfo(BufferedReader reader) {
    if (dlicense == null) {
      readDlicense(reader);
    }
    readName(reader);
    readCellNum(reader);
    readAddress(reader);
  }

  private void readDlicense(BufferedReader reader) {
    dlicense = Util.genericStringRead(
        reader,
        "Enter customer's driver's license number: ",
        FieldSizes.MAXIMUM_DLICENSE_SIZE,
        false);
  }

  private void readName(BufferedReader reader) {
    name = Util.genericStringRead(
        reader,
        "Enter customer's name: ",
        FieldSizes.MAXIMUM_CUSTOMER_NAME_SIZE,
        false);
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
    address = Util.genericStringRead(
        reader,
        "Enter customer's address: ",
        FieldSizes.MAXIMUM_ADDRESS_SIZE,
        true);
  }

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
}
