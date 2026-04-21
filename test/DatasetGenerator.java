import java.util.Random;

/**
 * DatasetGenerator.java
 *
 * Helper class that generates random integer arrays for benchmarking.
 * Centralises dataset creation so SortingBenchmark stays clean and
 * test runs remain reproducible by fixing the random seed.
 */
public class DatasetGenerator {

    private final Random random;

    /**
     * Creates a generator with a fixed seed for reproducible tests.
     *
     * @param seed any long value; same seed always produces the same arrays
     */
    public DatasetGenerator(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Creates a generator with a random seed (non-reproducible).
     */
    public DatasetGenerator() {
        this.random = new Random();
    }

    /**
     * Generates an array of the requested size filled with random integers
     * in the range [0, maxValue).
     *
     * @param size     number of elements
     * @param maxValue upper bound (exclusive) for each element
     * @return a new randomly populated int array
     */
    public int[] generate(int size, int maxValue) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue);
        }
        return array;
    }

    /**
     * Convenience overload — generates integers in [0, 10 000).
     *
     * @param size number of elements
     * @return a new randomly populated int array
     */
    public int[] generate(int size) {
        return generate(size, 10_000);
    }

    /**
     * Returns a deep copy of the given array so each algorithm sorts
     * identical input without affecting each other's test data.
     *
     * @param original the source array
     * @return an independent copy
     */
    public static int[] copyOf(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }
}
