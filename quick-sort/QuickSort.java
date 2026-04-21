/**
 * QuickSort.java
 * Implements Quick Sort using the Divide and Conquer paradigm.
 *
 * Divide and Conquer Strategy:
 *   1. DIVIDE   - Choose a pivot and partition the array around it
 *   2. CONQUER  - Recursively sort elements before and after the pivot
 *   3. COMBINE  - No merging needed; the array is sorted in place
 *
 * Time Complexity:
 *   Best Case:    O(n log n) — pivot consistently splits array in half
 *   Average Case: O(n log n) — expected over random inputs
 *   Worst Case:   O(n²)      — when pivot is always the smallest/largest element
 *
 * Space Complexity: O(log n) — recursive call stack (in-place sorting)
 */
public class QuickSort {

    /**
     * Public entry point. Sorts the given array in-place using Quick Sort.
     *
     * @param array the integer array to sort
     */
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) return;
        quickSort(array, 0, array.length - 1);
    }

    /**
     * DIVIDE & CONQUER — partitions and recursively sorts sub-arrays.
     *
     * @param array the array being sorted
     * @param low   the starting index of the current sub-array
     * @param high  the ending index of the current sub-array
     */
    private static void quickSort(int[] array, int low, int high) {
        // Base case: sub-array of size 0 or 1 is already sorted
        if (low >= high) return;

        // --- DIVIDE ---
        // Partition the array and get the final sorted position of the pivot
        int pivotIndex = partition(array, low, high);

        // --- CONQUER ---
        // Recursively sort the sub-array to the LEFT of the pivot
        quickSort(array, low, pivotIndex - 1);
        // Recursively sort the sub-array to the RIGHT of the pivot
        quickSort(array, pivotIndex + 1, high);

        // --- COMBINE ---
        // Nothing to combine — partitioning sorts in place
    }

    /**
     * PARTITION step — rearranges elements around a chosen pivot.
     *
     * Strategy: Lomuto partition scheme
     *   - Use the LAST element as the pivot
     *   - Move all elements smaller than pivot to its left
     *   - Move all elements greater than pivot to its right
     *   - Place pivot in its final sorted position
     *
     * @param array the array being partitioned
     * @param low   start index
     * @param high  end index (pivot is array[high])
     * @return the final sorted index of the pivot
     */
    private static int partition(int[] array, int low, int high) {
        // Choose the last element as the pivot
        int pivot = array[high];

        // i tracks the boundary between "smaller than pivot" and "larger than pivot"
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // If current element is less than or equal to pivot, it belongs on the left
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j); // Move this element to the left partition
            }
        }

        // Place the pivot in its correct sorted position
        swap(array, i + 1, high);

        // Return the pivot's final index
        return i + 1;
    }

    /**
     * Swaps two elements in the array.
     *
     * @param array the array
     * @param i     index of the first element
     * @param j     index of the second element
     */
    private static void swap(int[] array, int i, int j) {
        int temp  = array[i];
        array[i]  = array[j];
        array[j]  = temp;
    }

    /** Quick demo of QuickSort */
    public static void main(String[] args) {
        int[] data = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("=== Quick Sort Demo ===");
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
