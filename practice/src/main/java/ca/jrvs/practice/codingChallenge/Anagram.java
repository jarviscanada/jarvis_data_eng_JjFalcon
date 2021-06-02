package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

public class Anagram {
  public static boolean isAnagramSort(String str1, String str2) {
    if (str1.equals("") && str2.equals("")) {
      return true;
    } else if (str1.length() != str2.length()) {
      return false;
    }

    char[] firstArray = str1.toCharArray();
    char[] secondArray = str2.toCharArray();
    Arrays.sort(firstArray);
    Arrays.sort(secondArray);
    return Arrays.equals(firstArray, secondArray);
  }

  public static boolean isAnagramMap(String str1, String str2) {
    if (str1.equals("") && str2.equals("")) {
      return true;
    } else if (str1.length() != str2.length()) {
      return false;
    }

    // to do

    return true;
  }

}
