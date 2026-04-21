# Merge Sort

## What is Merge Sort?

Merge Sort is a **stable, comparison-based sorting algorithm** that applies the Divide and Conquer paradigm to achieve guaranteed O(n log n) time complexity on any input. It was invented by John von Neumann in 1945 and remains widely used today — particularly in situations where stability matters or external (disk-based) sorting is required.

---

## Divide and Conquer Breakdown

```
Original array: [38, 27, 43, 3, 9, 82, 10]

DIVIDE phase:
                [38, 27, 43, 3, 9, 82, 10]
                /                          \
        [38, 27, 43]                [3, 9, 82, 10]
         /        \                  /           \
      [38]     [27, 43]          [3, 9]       [82, 10]
                /    \            /   \         /    \
             [27]   [43]        [3]   [9]     [82]  [10]

CONQUER + COMBINE (merging back up):
             [27]   [43]        [3]   [9]     [10]  [82]
                \    /            \   /         \    /
             [27, 43]           [3, 9]         [10, 82]
                 |                  \           /
            [27, 38, 43]          [3, 9, 10, 82]
                        \        /
               [3, 9, 10, 27, 38, 43, 82]   ← sorted!
```

**Step-by-step logic:**
1. **Base case** — an array of 0 or 1 elements is already sorted; return immediately.
2. **Divide** — find the midpoint `mid = (left + right) / 2` and split the array into `array[left..mid]` and `array[mid+1..right]`.
3. **Conquer** — call `mergeSort` recursively on each half.
4. **Combine** — call `merge()`, which compares elements from both halves one by one and writes them back in sorted order using a temporary array.

---

## Complexity Analysis

| Case | Time | Explanation |
|------|------|-------------|
| Best | O(n log n) | Recursion depth is always log n; each level does O(n) work |
| Average | O(n log n) | Same reasoning — input order does not affect the structure |
| Worst | O(n log n) | Unlike Quick Sort, there is no degenerate case |
| Space | O(n) | Temporary arrays allocated during each merge step |

The **log n** factor comes from the number of times we can halve the array before reaching size 1. At each level of recursion, every element is visited exactly once during the merge — giving the **n** factor. Multiplied together: **O(n log n)**.

The **O(n) space** requirement is a drawback compared to Quick Sort's O(log n). For very large datasets in memory-constrained environments, this matters.

---

## Key Properties

- **Stable** — equal elements appear in the same relative order in the output as in the input. Essential when sorting objects by one key while preserving the order of a previous sort.
- **Consistent** — always O(n log n), so it is predictable for time-critical applications.
- **Preferred for external sorting** — its sequential access pattern is ideal for reading/writing large files from disk.

---

## Example Execution

```
=== Merge Sort Demo ===
Before: [38, 27, 43, 3, 9, 82, 10]
After:  [3, 9, 10, 27, 38, 43, 82]
```

### How to Run

```bash
cd merge-sort
javac MergeSort.java
java  MergeSort
```
