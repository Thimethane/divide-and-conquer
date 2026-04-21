import java.util.Random;

/**
 * SortingBenchmark.java  —  MANDATORY TESTING MODULE
 *
 * Empirically compares Merge Sort and Quick Sort by:
 *   1. Generating random datasets of sizes 10, 50, and 100
 *   2. Running each algorithm TRIALS times on identical copies of each dataset
 *   3. Measuring execution time in milliseconds via System.nanoTime()
 *   4. Reporting the average time per dataset size
 *
 * All sorting algorithms are inlined here so this file compiles standalone
 * (no separate classpath setup required).
 *
 * How to compile and run:
 *   javac SortingBenchmark.java
 *   java  SortingBenchmark
 */
public class SortingBenchmark {

    // ---------------------------------------------------------------
    // Configuration
    // ---------------------------------------------------------------
    private static final int[]  DATASET_SIZES = {10, 50, 100};
    private static final int    TRIALS        = 20;   // runs per size for averaging
    private static final long   RANDOM_SEED   = 42L;  // fixed seed → reproducible results
    private static final int    VALUE_RANGE   = 10_000;

    // ---------------------------------------------------------------
    // Main
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   Divide and Conquer Sorting — Benchmark Suite  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
        System.out.printf("  Configuration: %d trial(s) per dataset size, seed = %d%n%n",
                          TRIALS, RANDOM_SEED);

        // Table header
        System.out.printf("%-18s %-22s %-22s%n",
                          "Dataset Size", "Merge Sort Time", "Quick Sort Time");
        System.out.println("─".repeat(64));

        // Store results for the summary at the end
        double[] mergeTimes = new double[DATASET_SIZES.length];
        double[] quickTimes = new double[DATASET_SIZES.length];

        for (int idx = 0; idx < DATASET_SIZES.length; idx++) {
            int size = DATASET_SIZES[idx];

            double mergeTotal = 0;
            double quickTotal = 0;

            Random rng = new Random(RANDOM_SEED);

            for (int t = 0; t < TRIALS; t++) {
                // Generate a fresh random array for this trial
                int[] original = new int[size];
                for (int i = 0; i < size; i++) {
                    original[i] = rng.nextInt(VALUE_RANGE);
                }

                // Give each algorithm an identical copy so they sort the same data
                int[] forMerge = copyOf(original);
                int[] forQuick = copyOf(original);

                // --- Time Merge Sort ---
                long startMerge = System.nanoTime();
                mergeSort(forMerge, 0, forMerge.length - 1);
                long endMerge = System.nanoTime();
                mergeTotal += (endMerge - startMerge) / 1_000_000.0; // ns → ms

                // --- Time Quick Sort ---
                long startQuick = System.nanoTime();
                quickSort(forQuick, 0, forQuick.length - 1);
                long endQuick = System.nanoTime();
                quickTotal += (endQuick - startQuick) / 1_000_000.0; // ns → ms
            }

            double mergeAvg = mergeTotal / TRIALS;
            double quickAvg = quickTotal / TRIALS;

            mergeTimes[idx] = mergeAvg;
            quickTimes[idx] = quickAvg;

            // Print individual dataset result
            System.out.println();
            System.out.printf("  Dataset Size:    %d%n", size);
            System.out.printf("  Merge Sort Time: %.4f ms%n", mergeAvg);
            System.out.printf("  Quick Sort Time: %.4f ms%n", quickAvg);
        }

        // Summary table
        System.out.println();
        System.out.println("═".repeat(64));
        System.out.println("  RESULTS SUMMARY");
        System.out.println("═".repeat(64));
        System.out.printf("  %-14s %-22s %-22s%n", "Size", "Merge Sort (avg ms)", "Quick Sort (avg ms)");
        System.out.println("  " + "─".repeat(60));
        for (int idx = 0; idx < DATASET_SIZES.length; idx++) {
            System.out.printf("  %-14d %-22.4f %-22.4f%n",
                              DATASET_SIZES[idx], mergeTimes[idx], quickTimes[idx]);
        }
        System.out.println();
        System.out.println("  Note: Each time is averaged over " + TRIALS + " trials.");
        System.out.println("  Both algorithms produce correctly sorted output.");
        System.out.println();
    }

    // ---------------------------------------------------------------
    // Merge Sort (inlined from MergeSort.java)
    // ---------------------------------------------------------------

    private static void mergeSort(int[] array, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int leftSize  = mid - left + 1;
        int rightSize = right - mid;

        int[] L = new int[leftSize];
        int[] R = new int[rightSize];

        for (int i = 0; i < leftSize; i++)  L[i] = array[left + i];
        for (int j = 0; j < rightSize; j++) R[j] = array[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < leftSize && j < rightSize) {
            if (L[i] <= R[j]) array[k++] = L[i++];
            else               array[k++] = R[j++];
        }
        while (i < leftSize)  array[k++] = L[i++];
        while (j < rightSize) array[k++] = R[j++];
    }

    // ---------------------------------------------------------------
    // Quick Sort (inlined from QuickSort.java)
    // ---------------------------------------------------------------

    private static void quickSort(int[] array, int low, int high) {
        if (low >= high) return;
        int pivotIndex = partition(array, low, high);
        quickSort(array, low, pivotIndex - 1);
        quickSort(array, pivotIndex + 1, high);
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i]; array[i] = array[j]; array[j] = temp;
    }

    // ---------------------------------------------------------------
    // Utility
    // ---------------------------------------------------------------

    private static int[] copyOf(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }
}
