# Quick Sort

## What is Quick Sort?

Quick Sort is an **in-place, comparison-based sorting algorithm** that applies Divide and Conquer via partitioning. Developed by Tony Hoare in 1959, it is one of the most widely used sorting algorithms in practice — the standard library sort in many languages (C's `qsort`, Java's `Arrays.sort` for primitives) is Quick Sort or a derivative.

Its key advantage over Merge Sort is that it requires no auxiliary array — sorting happens entirely within the original array, making it highly cache-efficient.

---

## Partitioning Strategy (Lomuto Scheme)

This implementation uses the **Lomuto partition scheme**, which selects the **last element** as the pivot.

```
Array:  [64, 34, 25, 12, 22, 11, 90]
Pivot = 90 (last element)

Partitioning:
  All elements < 90 stay on the left
  All elements > 90 go to the right
  90 is placed in its final sorted position

Result: [64, 34, 25, 12, 22, 11 | 90 |]
         ← smaller than pivot →   pivot   ← larger (none here)
```

After partitioning, the pivot is in its **final, correct sorted position**. No further moves are needed for it — we only recurse on the two sides.

---

## Divide and Conquer Breakdown

```
Original: [64, 34, 25, 12, 22, 11, 90]

DIVIDE (partition around pivot):
  pivot = 90  → [64, 34, 25, 12, 22, 11] [90] []
  pivot = 11  → [] [11] [64, 34, 25, 12, 22]
  pivot = 22  → [12] [22] [64, 34, 25]
  pivot = 25  → [12] [22] [25] [64, 34]
  pivot = 34  → [12] [22] [25] [34] [64]

COMBINE:
  In-place partitioning means no merge step needed.
  When recursion bottoms out, the array is sorted.

Result: [11, 12, 22, 25, 34, 64, 90]
```

**Step-by-step logic:**
1. **Base case** — sub-array of size 0 or 1 is already sorted; return.
2. **Divide** — call `partition()` to place the pivot in its final position. This rearranges elements so everything left of the pivot is ≤ pivot and everything right is ≥ pivot.
3. **Conquer** — recursively call `quickSort` on the left sub-array and the right sub-array.
4. **Combine** — nothing to do; the pivot is already placed and each partition is independently sorted in place.

---

## Recursive Behaviour

The depth of the recursion tree determines performance:
- **Best / Average case** — pivot lands near the middle, creating two roughly equal halves → depth ≈ log n → O(n log n) total.
- **Worst case** — pivot always lands at one end (e.g., array already sorted + last-element pivot) → depth = n → O(n²) total.

The worst case can be mitigated in production by using:
- **Randomised pivot** — pick a random element instead of always the last.
- **Median-of-three** — use the median of the first, middle, and last elements as the pivot.
- **Introsort** — hybrid that switches to Heap Sort when recursion depth exceeds a threshold (used by C++ `std::sort`).

---

## Complexity Analysis

| Case | Time | Explanation |
|------|------|-------------|
| Best | O(n log n) | Pivot always splits array in half |
| Average | O(n log n) | Expected over uniformly random inputs |
| Worst | O(n²) | Pivot always the smallest or largest element |
| Space | O(log n) | Recursive call stack (in-place sorting, no aux array) |

---

## Key Properties

- **In-place** — no auxiliary array needed, only O(log n) stack space.
- **Cache-friendly** — sequential memory access during partitioning benefits from CPU cache lines.
- **Not stable** — equal elements may swap relative order during partitioning.
- **Practically fast** — despite identical asymptotic complexity, Quick Sort often outperforms Merge Sort in practice due to lower constant factors and better cache behaviour.

---

## Example Execution

```
=== Quick Sort Demo ===
Before: [64, 34, 25, 12, 22, 11, 90]
After:  [11, 12, 22, 25, 34, 64, 90]
```

### How to Run

```bash
cd quick-sort
javac QuickSort.java
java  QuickSort
```
