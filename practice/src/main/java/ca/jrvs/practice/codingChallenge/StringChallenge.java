package ca.jrvs.practice.codingChallenge;

public class StringChallenge {

  /**
   * Takes a number in string type and converts it to integer. If the string contains white spaces,
   * it removes them. If the string exceeds Integer limits, return limit. Else, throw exception.
   *
   * @param string
   * @return integer version of the valid string
   */
  public static int parseToInteger(String string) {
    int result = 0;
    if (!(string == null || string.isEmpty())) {
      // removes white spaces
      string = string.replaceAll("\\s+", "");

      if (Double.parseDouble(string) > Integer.MAX_VALUE) return Integer.MAX_VALUE;
      if (Double.parseDouble(string) < Integer.MIN_VALUE) return Integer.MIN_VALUE;

      try {
        result = Integer.parseInt(string);
      } catch (Exception e) {
        throw new NumberFormatException("Not a valid input: " + string);
      }
    }
    return result;
  }

  /**
   * Takes a number in string type and converts it to integer. If the string contains white spaces,
   * it removes them. If the string exceeds Integer limits, return limit. If the string starts with
   * alpha, return 0. If the string contains an alpha in between, return the number before it.
   *
   * @param string
   * @return integer version of the valid string
   */
  public static int convertToInteger(String string) {
    int result = 0;
    int i = 0;
    boolean isNegative = false;
    if (!(string == null || string.isEmpty())) {
      string = string.replaceAll("\\s+", "");
      // check for sign
      if (string.charAt(0) == '-') {
        isNegative = true;
        i++;
      }

      // also evaluates alpha-numeric string, exits at first encounter on non numeric character and
      // returns 0
      while (i < string.length() && string.charAt(i) >= '0' && string.charAt(i) <= '9') {
        if (result > Integer.MAX_VALUE / 10
            || (result == Integer.MAX_VALUE / 10
                && string.charAt(i) - '0' > Integer.MAX_VALUE % 10)) {
          return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        result = result * 10 + (string.charAt(i++) - '0');
      }
    }
    return isNegative ? -result : result;
  }

  /**
   * Takes string and rotates it in the given direction from given index.
   * @param string
   * @param index
   * @param direction
   * @return rotated string
   */
  public static String rotateStringFromIndex(String string, int index, char direction) {
    // rotates right
    if (Character.toUpperCase(direction) == 'R') index = string.length() - index;
    return (string == null || string.isEmpty() || index > string.length())
        ? null
        : string.substring(index) + string.substring(0, index);
  }
}
