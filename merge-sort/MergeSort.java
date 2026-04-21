/**
 * MergeSort.java
 * Implements Merge Sort using the Divide and Conquer paradigm.
 *
 * Divide and Conquer Strategy:
 *   1. DIVIDE   - Split the array into two halves
 *   2. CONQUER  - Recursively sort each half
 *   3. COMBINE  - Merge the two sorted halves into one sorted array
 *
 * Time Complexity:  O(n log n) — always, regardless of input
 * Space Complexity: O(n)       — requires auxiliary array for merging
 */
public class MergeSort {

    /**
     * Public entry point. Sorts the given array in-place using Merge Sort.
     *
     * @param array the integer array to sort
     */
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) return;
        mergeSort(array, 0, array.length - 1);
    }

    /**
     * DIVIDE & CONQUER — recursively splits and sorts the array.
     *
     * @param array the array being sorted
     * @param left  the starting index of the current sub-array
     * @param right the ending index of the current sub-array
     */
    private static void mergeSort(int[] array, int left, int right) {
        // Base case: a sub-array of size 0 or 1 is already sorted
        if (left >= right) return;

        // --- DIVIDE ---
        // Find the midpoint to split the array into two halves
        int mid = left + (right - left) / 2;

        // --- CONQUER ---
        // Recursively sort the left half
        mergeSort(array, left, mid);
        // Recursively sort the right half
        mergeSort(array, mid + 1, right);

        // --- COMBINE ---
        // Merge the two sorted halves back together
        merge(array, left, mid, right);
    }

    /**
     * COMBINE step — merges two sorted sub-arrays into one sorted section.
     *
     * Left sub-array:  array[left..mid]
     * Right sub-array: array[mid+1..right]
     *
     * @param array the full array
     * @param left  start index of the left half
     * @param mid   end index of the left half
     * @param right end index of the right half
     */
    private static void merge(int[] array, int left, int mid, int right) {
        // Calculate sizes of the two sub-arrays
        int leftSize  = mid - left + 1;
        int rightSize = right - mid;

        // Create temporary arrays to hold the two halves
        int[] leftArray  = new int[leftSize];
        int[] rightArray = new int[rightSize];

        // Copy data into temporary arrays
        for (int i = 0; i < leftSize; i++)
            leftArray[i] = array[left + i];
        for (int j = 0; j < rightSize; j++)
            rightArray[j] = array[mid + 1 + j];

        // Merge the two temporary arrays back into the original
        int i = 0;      // index for leftArray
        int j = 0;      // index for rightArray
        int k = left;   // index for merged position in array

        while (i < leftSize && j < rightSize) {
            // Pick the smaller element from each half
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy any remaining elements from the left half
        while (i < leftSize) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // Copy any remaining elements from the right half
        while (j < rightSize) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    /** Quick demo of MergeSort */
    public static void main(String[] args) {
        int[] data = {38, 27, 43, 3, 9, 82, 10};

        System.out.println("=== Merge Sort Demo ===");
        System.out.print("Before: ");
        printArray(data);

        sort(data);

        System.out.print("After:  ");
        printArray(data);
    }

    static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
