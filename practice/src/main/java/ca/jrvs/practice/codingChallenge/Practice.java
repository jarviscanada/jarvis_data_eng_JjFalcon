package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Practice {


  static long factorial(long num) {
    long results = 1;
    for (int i = 1; i <= num; i++) {
      results = results * i;
    }
    return results;
    // return (num==1 ? 1 : num*factorial(num-1));
  }


  static int[] twoSumMap(int[] nums, int target) {
    Map<Integer, Integer> indexMap = new HashMap<>();
    int toFind;
    for (int i = 0; i < nums.length; i++) {
      toFind = target - nums[i];
      if (indexMap.containsKey(toFind)) {
        return new int[]{indexMap.get(toFind), i};
      } else {
        indexMap.put(nums[i], i);
      }
    }
    return new int[]{-1, -1};
  }

  public static void main(String[] args) {
    // printFib(4);
    // System.out.println(factorial(4));
    // swapNum(5, 4);
    //    int prime = countPrime(12);
    //    System.out.println(prime);

    int arr[] = {1, 4, 3, 6, 3, 6, 4};
    String text = "mississipi";


     //printRepeating(arr);
    // findDuplicates(arr);
    // findMinMax(arr);
    // System.out.println("Is there duplicate? " + containsDuplicateSet(arr));
    //char[] results;
   // results = getDuplicateChars(text);

    // copyArray();
  }
}
