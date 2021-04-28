package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.Scanner;

public class CodingChallengeViewer {

  public static void printOption(){
    System.out.println("MATH CODING CHALLENGE");
    System.out.println("OPTION 1: is given number Odd or Even using modulo operator");
    System.out.println("OPTION 2: is given number Odd or Even using bitwise operator");
    System.out.println("OPTION 3: swap 2 numbers using bitwise operator");
    System.out.println("OPTION 4: swap 2 numbers using arithmetic operator");
    System.out.println("OPTION 5: count the number of prime of a given number using Sieve of Eratosthenes");
    System.out.println("OPTION 6: find the missing number in a series using manual summation");
    System.out.println("OPTION 7: find the missing number in a series using Gauss Formula");
    System.out.println("OPTION 8: find the missing number in a series using Hash Set");
    System.out.println("OPTION 9: generate fibonacci series of n using recursion");
    System.out.println("OPTION 10: generate fibonacci series of n using dynamic programming\n");
    System.out.println("STRING CODING CHALLENGE");
    System.out.println("OPTION 11: parse a string into an integer");
    System.out.println("OPTION 12: convert a string into an integer");
    System.out.println("OPTION 13: rotate a string based on direction and index");
  }

  public static int getInputNumber(Scanner scanner) {
    System.out.print("Enter Number: ");
    //int inputNum = scanner.nextInt();
    return scanner.nextInt();
  }

  public static String getInputString(Scanner scanner) {
    System.out.print("Enter String: ");
    //int inputNum = scanner.nextInt();
    return scanner.next();
  }

  // for manual testing/running of the program
  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int option;

    do {
      printOption();
      System.out.print("\nEnter Valid Option: ");
      option = scanner.nextInt();
    } while (option < 1 || option > 13);

    int inputNumber = 0;
    int[] testArray = {};
    String inputString = "";
    if (option > 10) {
      inputString = getInputString(scanner);
    } else if (option > 5 || option <9) {
      testArray = new int[]{3, 1, 2, 6, 4, 5, 7, 9};
    } else {
      inputNumber = getInputNumber(scanner);
    }

    switch (option) {
      case 1:
        System.out.println(
            MathChallenge.isEven(inputNumber) ? "Number " + inputNumber + " is Even" : "Number " + inputNumber + " is Odd");
        break;
      case 2:
        System.out
            .println(MathChallenge.isOdd(inputNumber) ? "Number " + inputNumber + " is Even" : "Number " + inputNumber + " is Odd");
        break;
      case 3:
        MathChallenge.swapNumbersXOR(inputNumber, getInputNumber(scanner));
        break;
      case 4:
        MathChallenge.swapNumbersArithmetic(inputNumber, getInputNumber(scanner));
        break;
      case 5:
        int numOfPrime = MathChallenge.countPrime(inputNumber);
        System.out.println("There are " + numOfPrime + " prime numbers in " + inputNumber);
        break;
      case 6:
        System.out.println("This is the test array using manual summation: ");
        System.out.println(Arrays.toString(testArray));
        int missingNum = MathChallenge.findMissingNumberSum(testArray);
        System.out.println("The missing number in the array is: " + missingNum);
        break;
      case 7:
        System.out.println("This is the test array using Gauss Formula: ");
        System.out.println(Arrays.toString(testArray));
        int missingNum1 = MathChallenge.findMissingNumberGauss(testArray);
        System.out.println("The missing number in the array is: " + missingNum1);
        break;
      case 8:
        System.out.println("This is the test array using Hash Set: ");
        System.out.println(Arrays.toString(testArray));
        int missingNum2 = MathChallenge.findMissingNumberHashSet(testArray);
        System.out.println("The missing number in the array is: " + missingNum2);
        break;
      case 9:
        MathChallenge.printFib(inputNumber, true);
        break;
      case 10:
        MathChallenge.printFib(inputNumber, false);
        break;
      case 11:
        System.out.println(StringChallenge.parseToInteger(inputString));
        break;
      case 12:
        System.out.println(StringChallenge.convertToInteger(inputString));
        break;
      case 13:
        String newString = StringChallenge.rotateStringFromIndex(inputString, 2, 'L');
        System.out.println(newString);
        break;
      default:
        System.out.println("Wrong Entry \n ");
        break;
    }
    scanner.close();
  }

}

