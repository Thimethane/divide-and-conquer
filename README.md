# Divide and Conquer Sorting Algorithms in Java

## Introduction

**Divide and Conquer** is a fundamental algorithm design paradigm that solves problems by:
1. **Dividing** the problem into smaller sub-problems of the same type
2. **Conquering** each sub-problem recursively until a base case is reached
3. **Combining** the sub-problem solutions into the final answer

Sorting algorithms are critical in large-scale systems — they underpin database query optimisation, search indexing, data analytics pipelines, and nearly every application that processes ordered data. The difference between an O(n²) and an O(n log n) sort can mean the difference between seconds and hours at scale.

This project implements and empirically compares two classic Divide and Conquer sorting algorithms: **Merge Sort** and **Quick Sort**.

---

## Algorithms Overview

### Merge Sort
| Property | Value |
|----------|-------|
| Strategy | Split → sort halves → merge |
| Time complexity | O(n log n) — always |
| Space complexity | O(n) — auxiliary array |
| Stable | Yes |

Steps:
1. **Divide** — split the array into two equal halves at the midpoint
2. **Conquer** — recursively sort each half
3. **Combine** — merge the two sorted halves into one sorted array

### Quick Sort
| Property | Value |
|----------|-------|
| Strategy | Partition around pivot → recurse |
| Time complexity | O(n log n) best/avg · O(n²) worst |
| Space complexity | O(log n) — call stack |
| Stable | No (Lomuto scheme) |

Steps:
1. **Divide** — choose a pivot and partition elements (smaller left, larger right)
2. **Conquer** — recursively sort each partition
3. **Combine** — nothing; the array is sorted in place

---

## Empirical Testing

Testing is performed using randomly generated datasets to reflect real-world conditions. All tests use a fixed random seed (`42`) for full reproducibility.

**Dataset sizes tested:** 10 · 50 · 100 elements  
**Trials per size:** 20 runs (averaged to reduce JVM noise)  
**Time measurement:** `System.nanoTime()` converted to milliseconds  
**Input generation:** `java.util.Random` with integers in `[0, 10 000)`

---

## Results

> Benchmark run on Java 21 (OpenJDK), Ubuntu 24, averaged over 20 trials.

| Dataset Size | Merge Sort Time | Quick Sort Time |
|:---:|:---:|:---:|
| 10 | 0.0042 ms | 0.0021 ms |
| 50 | 0.0191 ms | 0.0416 ms |
| 100 | 0.0144 ms | 0.0099 ms |

---

## Key Insights

**At size 10 — Quick Sort wins (0.0021 ms vs 0.0042 ms)**  
For very small arrays the overhead of Merge Sort's auxiliary array allocation outweighs any benefit. Quick Sort's in-place partitioning has less overhead at this scale.

**At size 50 — Merge Sort wins (0.0191 ms vs 0.0416 ms)**  
Mid-sized arrays introduce more variance. Quick Sort's Lomuto partition scheme can behave suboptimally with certain random sequences, pushing it toward its worst-case behaviour on some trials.

**At size 100 — Quick Sort wins (0.0099 ms vs 0.0144 ms)**  
With larger inputs, Quick Sort's cache efficiency and in-place nature give it a practical edge over Merge Sort's repeated auxiliary array allocation.

**General observations:**
- Both algorithms are O(n log n) on average, so differences at these small sizes are driven by constants and memory allocation patterns, not asymptotic complexity.
- Merge Sort is **stable** (equal elements preserve their original order) — an important property in multi-key sorting scenarios.
- Quick Sort's worst case (O(n²)) can be mitigated in production by using randomised pivot selection or the median-of-three strategy.
- For large datasets (millions of elements), Merge Sort is often preferred in external sorting (disk-based) because it accesses memory sequentially.

---

## Repository Structure

```
sorting-algorithms-divide-conquer/
├── README.md                    ← This file
├── merge-sort/
│   ├── MergeSort.java           ← Full implementation
│   └── README.md                ← Module documentation
├── quick-sort/
│   ├── QuickSort.java           ← Full implementation
│   └── README.md                ← Module documentation
├── test/
│   ├── SortingBenchmark.java    ← Mandatory benchmark (run this)
│   └── DatasetGenerator.java    ← Random dataset helper
└── results/
    └── execution_results.md     ← Captured output + analysis
```

---

## How to Run

### Run the Benchmark
```bash
cd test
javac SortingBenchmark.java
java  SortingBenchmark
```

### Run Individual Demos
```bash
# Merge Sort demo
cd merge-sort
javac MergeSort.java
java  MergeSort

# Quick Sort demo
cd quick-sort
javac QuickSort.java
java  QuickSort
```

---

## Learning Outcomes Demonstrated

| Skill | Evidence |
|-------|----------|
| Recursive algorithm implementation | MergeSort.java, QuickSort.java |
| Divide and Conquer applied correctly | Explicit DIVIDE / CONQUER / COMBINE comments |
| Empirical performance testing | SortingBenchmark.java with System.nanoTime() |
| Algorithm efficiency analysis | Results table + Key Insights section |
| Professional repository structure | Module READMEs, results folder, clean layout |
