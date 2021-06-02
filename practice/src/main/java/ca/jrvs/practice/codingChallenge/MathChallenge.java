package ca.jrvs.practice.codingChallenge;

import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

/**
 * This program solves some common math problems and provides static methods for public
 * consumption.
 */
public class MathChallenge {

  /**
   * Returns whether the given number is odd or even using modulo operator
   *
   * @param number to determine if its odd or even
   * @return "even" or "odd"
   */
  public static boolean isEven(int number) {
    return ((number % 2) == 0);
  }

  /**
   * Returns whether the given number is odd or even using bitwise operator.
   *
   * @param number to determine if its odd or even
   * @return "even" or "odd"
   */
  public static boolean isOdd(int number) {
    return ((number & 1) != 1);
  }

  /**
   * Swaps two numbers using XOR | bitwise operator.
   * <p>
   * Bitwise XOR operator compares bits of two operands and returns false or 0 if they are equal and
   * returns true or 1 if they are not equal.
   *
   * @param x, y the numbers to swap
   */
  public static void swapNumbersXOR(int x, int y) {
    System.out.println("Using XOR");
    System.out.println("Before the swap: x is: " + x + " y is: " + y);
    x = x ^ y;
    y = x ^ y;
    x = x ^ y;
    System.out.println("After the swap: x is: " + x + " y is: " + y);
  }

  /**
   * Swaps two numbers using arithmetic operator.
   *
   * @param x, y the numbers to swap
   */
  public static void swapNumbersArithmetic(int x, int y) {
    System.out.println("Using Arithmetic");
    System.out.println("Before the swap: x is: " + x + " y is: " + y);
    x = x + y;
    y = x - y;
    x = x - y;
    System.out.println("After the swap: x is: " + x + " y is: " + y);
  }

  /**
   * Count the number of prime numbers up to the given input number using Sieve of Eratosthenes
   * Time Complexity: O(n^2)
   *
   * @param number to determine the number of prime numbers within this range
   * @return total number of primes numbers
   */
  public static int countPrime(int number) {
    // create an array of boolean from 0 to number and determine whether its
    // corresponding index is prime or not
    // boolean data types are automatically initialized to false
    boolean[] primeArray = new boolean[number];

    // mathematically there is no prime number greater than the current number and its square
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

  /**
   * Find the missing number is an array of sequential numbers using Summation 
   * Missing Number = Sum of the first n natural numbers less Sum of the elements in the given array 
   * Time Complexity: O(n)
   *
   * @param numArray to determine which number is missing in the series of natural numbers
   * @return the missing number in the array
   */
  public static int findMissingNumberSum(int[] numArray) {
    int n, sumNatural, sumArray;
    n = numArray.length;

    sumNatural = 0;
    for (int i = 0; i <= n; i++) {
      sumNatural += i;
    }

    sumArray = 0;
    for (int i = 0; i < n; i++) {
      sumArray += i;
    }
    return sumNatural - sumArray;
  }

  /**
   * Find the missing number is an array of sequential numbers using the Gauss formula 
   * Gauss Formula: n*(n+1)/2 Missing Number = Sum of the first n natural numbers less 
   * Sum of the elements in the given array 
   * Time Complexity: O(n)
   *
   * @param numArray to determine which number is missing in the series of natural numbers
   * @return the missing number in the array
   */
  public static int findMissingNumberGauss(int[] numArray) {
    int n, sumNatural;
    n = numArray.length;
    sumNatural = (n * (n + 1)) / 2;

    // subtract all the numbers [sum] in the current array from expected sum.
    for (int i = 0; i < n; i++) {
      sumNatural -= i;
    }
    return sumNatural;
  }

  /**
   * Find the missing number is an array of sequential numbers using sets 
   * Time Complexity: O(n)
   *
   * @param numArray to determine which number is missing in the series of natural numbers
   * @return the missing number in the array
   */
  public static int findMissingNumberHashSet(int[] numArray) {
    Set<Integer> numSet = new HashSet<>();
    for (int num : numArray) {
      numSet.add(num);
    }

    int expectedNumCount = numArray.length + 1;
    for (int x = 1; x < expectedNumCount; x++) {
      if (!numSet.contains(x)) {
        return x;
      }
    }
    return -1;
  }

  /**
   * Find the fibonacci number of a given "n" using recursion 
   * Time Complexity: O(2^n)
   *
   * @return the nth fibo number
   */
  private static int fibRecursion(int n) {
    // base case
    if (n <= 1) {
      return n;
    }

    // recursion
    return fibRecursion(n - 1) + fibRecursion(n - 2);
  }

  /**
   * Find the fibonacci number of a given "n" using dynamic programming 
   * Time Complexity: O(n)
   *
   * @param n          to determine the fibo number at that position
   * @param fibResults to hold the already calculated fibo number of the nth position
   * @return the nth fibo number
   */
  private static int fibDynamic(int n, int[] fibResults) {
    if (fibResults[n] != 0) {
      return fibResults[n];
    }

    // base case
    if (n <= 1) {
      return n;
    }

    // recursion
    fibResults[n] = fibDynamic(n - 1, fibResults) + fibDynamic(n - 2, fibResults);

    return fibResults[n];
  }

  /**
   * Print the fibonacci series of a given "n"
   *
   * @param flag to determine whether to use recursion or dynamic programming
   * @param n    to determine the nth series of the fibonaci number
   */
  public static void printFib(int n, boolean flag) {

    if (flag) {
      System.out.println("Fibo using recursion: ");
      for (int i = 0; i < n; i++) {
        System.out.print(fibRecursion(i) + " ");
      }
    } else {
      System.out.println("Fibo using dynamic programming: ");
      for (int i = 0; i < n; i++) {
        int[] fibResults = new int[i + 1];
        System.out.print(fibDynamic(i, fibResults) + " ");
      }
    }
  }
}
