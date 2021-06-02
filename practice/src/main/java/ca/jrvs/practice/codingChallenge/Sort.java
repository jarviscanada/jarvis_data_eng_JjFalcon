package ca.jrvs.practice.codingChallenge;

public class Sort {

  /*
   * Utility method to swap two numbers in array
   */
  public static void swap(int[] array, int from, int to){
    int temp = array[from];
    array[from] = array[to];
    array[to] = temp;
  }

  // O(n²)
  public static void bubbleSort(int[] numbers) {
    for (int i = 0; i < numbers.length; i++) {
      for (int j = numbers.length -1; j > i; j--) {
        if (numbers[j] < numbers[j - 1]) {
          swap(numbers, j, j-1);
        }
      }
    }
  }

  /**
   * O(n²)
   * Stable sort (the relative order of items with equal keys does not change)
   * In-place insertionSort
   * @param input
   */
  public static void insertionSortImperative(int[] input) {
    for (int i = 1; i < input.length; i++) {
      int key = input[i];
      int j = i - 1;
      while (j >= 0 && input[j] > key) {
        input[j + 1] = input[j];
        j = j - 1;
      }
      input[j + 1] = key;
    }
  }

  //It's a unstable sort, meaning that the relative order of equal sort items is not preserved
  // O(n log n)
  public void quickSort(int arr[], int begin, int end) {
    if (begin < end) {
      int partitionIndex = partition(arr, begin, end);

      quickSort(arr, begin, partitionIndex-1);
      quickSort(arr, partitionIndex+1, end);
    }
  }

  private int partition(int arr[], int begin, int end) {
    int pivot = arr[end];
    int i = (begin-1);

    for (int j = begin; j < end; j++) {
      if (arr[j] <= pivot) {
        i++;

        int swapTemp = arr[i];
        arr[i] = arr[j];
        arr[j] = swapTemp;
      }
    }

    int swapTemp = arr[i+1];
    arr[i+1] = arr[end];
    arr[end] = swapTemp;

    return i+1;
  }

  // It's a stable sort, which means that the order of equal elements is the same in the input and output
  // O(log(n log n)
  public static void mergeSort(int[] a, int n) {
    if (n < 2) {
      return;
    }
    int mid = n / 2;
    int[] l = new int[mid];
    int[] r = new int[n - mid];

    for (int i = 0; i < mid; i++) {
      l[i] = a[i];
    }
    for (int i = mid; i < n; i++) {
      r[i - mid] = a[i];
    }
    mergeSort(l, mid);
    mergeSort(r, n - mid);

    merge(a, l, r, mid, n - mid);
  }

  public static void merge(
      int[] a, int[] l, int[] r, int left, int right) {

    int i = 0, j = 0, k = 0;
    while (i < left && j < right) {
      if (l[i] <= r[j]) {
        a[k++] = l[i++];
      } else {
        a[k++] = r[j++];
      }
    }
    while (i < left) {
      a[k++] = l[i++];
    }
    while (j < right) {
      a[k++] = r[j++];
    }
  }

}
