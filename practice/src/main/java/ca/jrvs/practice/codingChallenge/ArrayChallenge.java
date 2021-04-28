package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class ArrayChallenge {

  public static int[] twoSumBruteForce(int[] nums, int target) {
    return null;
  }

  public static int[] twoSumSorted(int[] nums, int target) {
    return null;
  }

  public static int[] twoSumMap(int[] nums, int target) {
    return null;
  }

  public static boolean containsDuplicateScan(int[] nums) {

    return false;
  }

  public static boolean containsDuplicateSet(int[] nums) {

    return false;
  }

  public static boolean containsDuplicateStream(int[] nums) {
    return !(IntStream.of(nums).distinct().count() == nums.length);
  }

  public int findDuplicateSort(int[] nums) {
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 1; i++) {
      if (nums[i] == nums[i + 1]) {
        return nums[i];
      }
    }
    return -1;
  }

  public static int findDuplicateSet(int[] nums) {

    return -1;
  }

  public static int[] findMinMaxForeach(int[] nums) {
    return null;
  }

  public static int[] findMinMaxStream(int[] nums) {
    return null;
  }

  public static int[] findMinMaxCollections(int[] nums) {
    return null;
  }

}
