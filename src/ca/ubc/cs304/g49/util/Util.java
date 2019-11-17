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
      System.out.println(String.format("%s %s", EXCEPTION_TAG, e.getMessage()));
    } catch (NumberFormatException e) {
      if (allowEmpty && line.length() == 0) ret = Optional.of(0);
      else System.out.println(Util.WARNING_TAG + " Input not integer.");
    }

    return ret;
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
