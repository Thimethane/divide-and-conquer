# Benchmark Execution Results

## Environment

| Property | Value |
|----------|-------|
| JVM | Java 21 (OpenJDK) |
| OS | Ubuntu 24 |
| Trials per size | 20 (averaged) |
| Random seed | 42 (fixed, reproducible) |
| Integer range | [0, 10 000) |

---

## Raw Benchmark Output

```
╔══════════════════════════════════════════════════╗
║   Divide and Conquer Sorting — Benchmark Suite  ║
╚══════════════════════════════════════════════════╝

  Configuration: 20 trial(s) per dataset size, seed = 42

Dataset Size       Merge Sort Time        Quick Sort Time
────────────────────────────────────────────────────────────────

  Dataset Size:    10
  Merge Sort Time: 0.0042 ms
  Quick Sort Time: 0.0021 ms

  Dataset Size:    50
  Merge Sort Time: 0.0191 ms
  Quick Sort Time: 0.0416 ms

  Dataset Size:    100
  Merge Sort Time: 0.0144 ms
  Quick Sort Time: 0.0099 ms

════════════════════════════════════════════════════════════════
  RESULTS SUMMARY
════════════════════════════════════════════════════════════════
  Size           Merge Sort (avg ms)    Quick Sort (avg ms)
  ────────────────────────────────────────────────────────────
  10             0.0042                 0.0021
  50             0.0191                 0.0416
  100            0.0144                 0.0099

  Note: Each time is averaged over 20 trials.
  Both algorithms produce correctly sorted output.
```

---

## Results Table

| Dataset Size | Merge Sort (avg ms) | Quick Sort (avg ms) | Faster Algorithm |
|:---:|:---:|:---:|:---:|
| 10 | 0.0042 | 0.0021 | Quick Sort |
| 50 | 0.0191 | 0.0416 | Merge Sort |
| 100 | 0.0144 | 0.0099 | Quick Sort |

---

## Analysis

### Size 10 — Quick Sort wins

Quick Sort is roughly **2× faster** at 10 elements. At this scale, Merge Sort's overhead from allocating temporary left/right arrays for every merge call is disproportionately expensive. Quick Sort's in-place partitioning avoids any heap allocation, giving it a clear constant-factor advantage on tiny inputs.

### Size 50 — Merge Sort wins

The result inverts: Merge Sort is about **2× faster** at 50 elements. The Lomuto partition scheme used in this Quick Sort implementation can exhibit poor pivot selection on certain random sequences, causing some trials to approach O(n²) behaviour. These suboptimal trials raise the average. Merge Sort is entirely immune to pivot luck — its O(n log n) guarantee holds unconditionally.

### Size 100 — Quick Sort wins again

At 100 elements, Quick Sort recovers its lead (about **1.5× faster**). With more elements, the JVM's JIT compiler has had time to optimise the hot inner loop of the partition function. Additionally, Quick Sort's sequential memory access pattern during partitioning benefits from CPU cache locality more than Merge Sort's two-way read pattern.

---

## Scalability Explanation

Both algorithms are **O(n log n)** asymptotically, so neither will dramatically outperform the other at these small dataset sizes — the differences seen are driven by:

1. **Memory allocation** — Merge Sort allocates `O(n)` auxiliary space; Quick Sort needs only `O(log n)` stack space. At larger n, this becomes significant.
2. **Cache behaviour** — Quick Sort tends to access contiguous memory regions, fitting well in CPU caches.
3. **Pivot quality** — Lomuto's worst-case (O(n²)) can surface on partially sorted or adversarial inputs. Production implementations use randomised pivots or introsort to avoid this.
4. **Stability requirements** — When stable sorting is needed (e.g., sorting database records by secondary key after a primary sort), Merge Sort is mandatory. Quick Sort is not stable.

### At large n (millions of elements)

- **Quick Sort** (with randomised pivot or median-of-three) is typically fastest for in-memory sorting.
- **Merge Sort** is preferred for **external sorting** (data too large for RAM) because it reads and writes data sequentially, minimising expensive disk seeks.
- **Timsort** (Python, Java objects) and **Pdqsort** (Rust, C++) combine both strategies adaptively.

---

## Conclusion

Neither algorithm strictly dominates the other across all scenarios. The right choice depends on:

| Factor | Prefer Merge Sort | Prefer Quick Sort |
|--------|-------------------|-------------------|
| Stability required | ✅ | ❌ |
| Memory constrained | ❌ | ✅ |
| External (disk) sort | ✅ | ❌ |
| Adversarial input risk | ✅ | ❌ (use randomised pivot) |
| Cache performance | ❌ | ✅ |
| Guaranteed worst case | ✅ | ❌ |
