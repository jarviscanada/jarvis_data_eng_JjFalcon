package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Practice {

  // [] fibResults stores the already calculated fib of num.
  static int fibDynamic(int num, int[] fibResults) {
    // check if fib of current num has already been calculated
    if (fibResults[num] != 0) return fibResults[num];

    // base
    if (num == 0 || num == 1) return num;

    // else calculate num's fib and store it
    fibResults[num] = fibDynamic(num - 1, fibResults) + fibDynamic(num - 2, fibResults);

    return fibResults[num];
  }

  static void printFib(int num) {
    for (int i = 0; i < num; i++) {
      // set fibs of the current iteration
      int[] fibResults = new int[i + 1];
      System.out.print(fibDynamic(i, fibResults) + " ");
    }
  }

  static long factorial(long num) {
    long results = 1;
    for (int i = 1; i <= num; i++) {
      results = results * i;
    }
    return results;
    // return (num==1 ? 1 : num*factorial(num-1));
  }

  // repeating from 0 to n-1 of n
  static void printRepeating(int arr[]) {
    int size = arr.length;
    int i;
    System.out.println("The repeating elements are : ");

    for (i = 0; i < size; i++) {
      int j = Math.abs(arr[i]);
      if (arr[j] >= 0) arr[j] = -arr[j];
      else System.out.print(j + " ");
    }
  }

  static void swapNum(int x, int y) {
    System.out.println("x is: " + x + " y is: " + y);
    x = x + y;
    y = x - y;
    x = x - y;
    System.out.println("x is: " + x + " y is: " + y);
  }

  public static int countPrime(int number) {
    // create an array of boolean from 0 to number and determine whether its
    // corresponding index is prime or not
    // boolean data types are automatically initialized to false
    boolean[] primeArray = new boolean[number];

    // mathematically there is no prime number greater than the current number and its square root
    for (int i = 2; i * i < primeArray.length; i++) {
      if (!primeArray[i]) {
        for (int j = i; j * i < primeArray.length; j++) {
          // index is not prime
          primeArray[i * j] = true;
        }
      }
    }

    // counts how many trues [corresponding prime index number] in the Array
    int primeCount = 0;
    for (int i = 2; i < primeArray.length; i++) {
      if (!primeArray[i]) {
        primeCount++;
      }
    }
    return primeCount;
  }

  public static int convertString(String num) {
    // check if negative
    boolean isNegative = false;

    int result = 0;
    int i = 0;
    while (i < num.length() && num.charAt(i) > '0' && num.charAt(i) < '9') {
      result = result * 10 + (num.charAt(i++) - '0');
    }
    return isNegative ? -result : result;
  }

  static void findMinMax(int[] elements) {
    int min = elements[0];
    int max = elements[0];
    for (int i = 1; i < elements.length; i++) {
      if (elements[i] < min) {
        min = elements[i];
      } else if (elements[i] > max) {
        max = elements[i];
      }
    }
    System.out.println("min and max are: " + min + " " + max);
  }

  static boolean isPalindrome(String input) {
    if (input == null || input.isEmpty()) {
      return true;
    }
    char[] array = input.toCharArray();
    StringBuilder sb = new StringBuilder(input.length());
    for (int i = input.length() - 1; i >= 0; i--) {
      sb.append(array[i]);
    }
    String reverseOfString = sb.toString();
    return input.equals(reverseOfString);
  }

  static char[] getDuplicateChars(String input) {
    Set<Character> uniqueChars = new HashSet<>();
    Set<Character> duplicateChars = new HashSet<>();
    StringBuilder duplicates = new StringBuilder();
    for (char ch : input.toCharArray()) {
      if (Character.isAlphabetic(ch)) {
        if (!uniqueChars.add(ch)) {
          duplicateChars.add(ch);
          duplicates.append(ch);
          System.out.println(ch);
        }
      }
    }
    return duplicateChars.toString().toCharArray();
  }

  public static boolean containsDuplicate(int[] elements) {
    for (int i = 0; i < elements.length - 1; i++) {
      for (int j = i + 1; j < elements.length; j++) {
        if (elements[i] == elements[j]) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean containsDuplicateSet(int[] numbers) {
    Set<Integer> uniqueSet = new HashSet<>();
    for (int elem : numbers) {
      if (!uniqueSet.add(elem)) {
        return true;
      }
    }
    return false;
  }

  static int findDupes(int[] elements) {
    Set<Integer> found = new HashSet<>();
    for (int elem : elements) {
      if (!found.add(elem)) {
        return elem;
      }
    }
    return -1;
  }

  static int[] twoSumBruteForce(int[] nums, int target) {
    for (int i = 0; i < nums.length - 1; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) {
          return new int[]{i, j};
        }
      }
    }
    return new int[]{-1, -1};
  }

  static int[] twoSumSorted(int[] nums, int target) {
    int firstIndex = 0;
    int secondIndex = nums.length - 1;
    while (firstIndex < secondIndex) {
      int sum = nums[firstIndex] + nums[secondIndex];
      if (sum == target) {
        return new int[]{firstIndex, secondIndex};
      } else if (sum < target) {
        firstIndex++;
      } else {
        secondIndex--;
      }
    }
    return new int[]{-1, -1};
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


  static int removeElementToBack(int[] nums, int val) {
    int backOfArray = nums.length;

    for (int i = 0; i < backOfArray; i++) {
      if (nums[i] == val) {
        int swap = nums[i];
        nums[i] = nums[--backOfArray];
        nums[backOfArray] = swap;
        i--;
      }
    }
    return backOfArray;
    }

  static int removeElementSlideDown(int[] nums, int val) {
    int newLen = nums.length;
    for (int i = 0; i < newLen; i++) {
      if (nums[i] == val) {
        for (int j = i; j < newLen - 1; j++) {
          nums[j] = nums[j + 1];
        }
        newLen--;
        i--;
      }
    }
    System.out.println(Arrays.toString(nums));
    return newLen;
  }

  static void copyArray(){
  int arr1[] = { 0, 1, 2, 3, 4, 5 };
  int arr2[] = { 5, 10, 20, 30, 40, 50 };

  // copies an array from the specified source array
      System.arraycopy(arr1, 0, arr2, 0, 2);
      System.out.print("array2 = ");
      System.out.print(arr2[0] + " ");
      System.out.print(arr2[1] + " ");
      System.out.print(arr2[2] + " ");
      System.out.print(arr2[3] + " ");
      System.out.print(arr2[4] + " ");
      System.out.print(arr2[5] + " ");

      //array2 = 0 1 20 30 40 50
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

    copyArray();
  }
}
