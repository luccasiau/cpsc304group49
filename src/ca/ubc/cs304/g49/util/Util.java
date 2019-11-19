package ca.ubc.cs304.g49.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class Util {
  private static final String EXCEPTION_TAG = "[EXCEPTION]";
  private static final String WARNING_TAG = "[WARNING]";

  public static Optional<Integer> readInteger(BufferedReader reader, boolean allowEmpty) {
    String line = null;
    Optional<Integer> ret = Optional.empty();
    try {
      line = reader.readLine();
      ret = Optional.of(Integer.parseInt(line));
    } catch (IOException e) {
      printException(e.getMessage());
    } catch (NumberFormatException e) {
      if (allowEmpty && line.length() == 0) ret = Optional.of(0);
      else printException(e.getMessage());
    }

    return ret;
  }

  public static Optional<Long> readLong(BufferedReader reader, boolean allowEmpty) {
    String line = null;
    Optional<Long> ret = Optional.empty();
    try {
      line = reader.readLine();
      ret = Optional.of(Long.parseLong(line));
    } catch (IOException e) {
      printException(e.getMessage());
    } catch (NumberFormatException e) {
      if (allowEmpty && line.length() == 0) ret = Optional.of(0L);
      else printException(e.getMessage());
    }

    return ret;
  }

  public static Optional<String> readString(BufferedReader reader, int maxSize, boolean allowEmpty) {
    String line = null;

    try {
      line = reader.readLine();
    } catch (IOException e) {
      printException(e.getMessage());
    }

    if (line.length() > maxSize) {
      printWarning(String.format("Maximum allowed size is %d", maxSize));
      return Optional.empty();
    }
    if (line.length() == 0 && !allowEmpty) {
      printWarning("Field cannot be empty.");
      return Optional.empty();
    }

    return Optional.of(line);
  }

  public static String makeWarning(String message) {
    return String.format("%s %s", WARNING_TAG, message);
  }

  public static void printWarning(String message) {
    System.out.println(makeWarning(message));
  }

  public static void printException(String message) {
    System.out.println(String.format("%s %s", EXCEPTION_TAG, message));
  }
}
