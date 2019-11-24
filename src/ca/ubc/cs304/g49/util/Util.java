package ca.ubc.cs304.g49.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Optional;
import java.util.Random;

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

  public static Optional<Boolean> readBoolean(BufferedReader reader, boolean allowEmpty) {
    String line = null;
    Optional<Boolean> ret = Optional.empty();
    try {
      line = reader.readLine();
      ret = Optional.of(Boolean.parseBoolean(line));
    } catch (IOException e) {
      printException(e.getMessage());
    } catch (NumberFormatException e) {
      if (allowEmpty && line.length() == 0) ret = Optional.of(Boolean.TRUE);
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

  public static String genericStringRead(
      BufferedReader reader, String message, int maxLength, boolean allowEmpty) {
    while (true) {
      System.out.print(message);
      Optional<String> fieldOptional =
          Util.readString(reader, maxLength, allowEmpty);
      if (fieldOptional.isPresent()) {
        return fieldOptional.get();
      }
    }
  }

  public static Integer genericIntegerRead(BufferedReader reader, String message, boolean allowEmpty, int minValue) {
    while (true) {
      System.out.print(message);
      Integer integer = readInteger(
                reader,
                false)
                .orElse(1000);

      if (integer < minValue) {
        Util.printWarning("Odometer reading at time of return cannot be less than odometer at time of rental");
        continue;
      }

      return integer;
    }
  }

  public static Date genericDateRead(
      BufferedReader reader, String message, Date minDate) {
    while (true) {
      System.out.print(message);
      try {
        Date date = Date.valueOf(Util.readString(reader, 10, false).orElse(""));
        if (date.compareTo(minDate) < 0) {
          Util.printWarning("Date has to be at least " + minDate.toString());
          continue;
        }
        return date;
      } catch (IllegalArgumentException e) {
        Util.printException(e.getMessage());
      }
    }
  }

  /**
   * Generates a random alphanumerical (uppercase only) string with a certain length.
   * @param length: length of final random string
   */
  public static String randomHash(int length) {
    String alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    char[] buffer = new char[length];
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      buffer[i] = alphanum.charAt(random.nextInt(alphanum.length()));
    }
    return new String(buffer);
  }
}
