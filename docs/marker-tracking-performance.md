# Marker Tracking Performance and Migration Notes

This guide focuses on improving tracking speed for gait/body-marker workflows in Tracker, especially when many markers are active.

## Why tracking slows down with multiple markers

`AutoTracker` evaluates each marker point frame-by-frame using template matching. As marker count grows, total work increases roughly by:

- number of active tracks,
- size of the search region,
- size of the template,
- number of processed frames.

In practical terms, large search windows and many simultaneous autotracks are the biggest bottlenecks.

## Practical speed improvements (no code changes required)

1. **Reduce search region size first**  
   Keep the search rectangle just large enough for expected marker motion between frames.
2. **Use smaller, high-contrast templates**  
   Tight templates around a marker reduce per-frame matching cost.
3. **Track key markers first, then derive others**  
   For gait analysis, tracking fewer landmarks and computing derived quantities can be faster than tracking every visible point.
4. **Preprocess videos before loading into Tracker**  
   Downsample resolution and frame rate to what your analysis actually requires.
5. **Split long recordings into phases**  
   Analyze stance/swing segments separately to limit active frame ranges.
6. **Prefer fixed camera + high shutter speed capture**  
   Better contrast and less motion blur improve automatic matching confidence and reduce rework.

## Code-level options in this repository

If you plan to extend Tracker directly, these are high-impact directions:

- **ROI caching and early-exit heuristics in `AutoTracker`**  
  Reuse intermediate values when movement is small; skip expensive match steps when confidence is already high.
- **Parallelize multi-track matching**  
  Independent marker tracks can be evaluated in parallel worker threads, then synchronized to update UI safely.
- **Decouple matching from EDT/UI work**  
  Ensure heavy matching happens off the Swing event dispatch thread and only publish final marker updates to UI.
- **Optional native acceleration path**  
  Add a pluggable backend (e.g., OpenCV via JavaCPP) while keeping existing Java matching as a fallback.

## Alternative code base strategy

If your primary goal is speed with many markers, a hybrid architecture is usually lower-risk than a full rewrite:

1. Keep Tracker for calibration, coordinate systems, and educational workflow.
2. Export frames/regions to an external tracker service (Python/C++ OpenCV or deep-learning models).
3. Re-import marker trajectories into Tracker-compatible data structures for analysis and plotting.

This approach lets you gain performance and modern CV tooling without immediately replacing the full Java application.

## Recommended migration paths

- **Python + OpenCV + NumPy/Pandas**: fastest route for experimentation and model iteration.
- **C++ + OpenCV**: strongest raw performance for real-time/high-volume processing.
- **Java + OpenCV bindings**: keeps JVM deployment model while adding native acceleration.

## Decision checklist

Use this checklist before committing to a rewrite:

- Is tracking speed limited by video quality and marker visibility more than CPU?
- Can ROI/template tuning solve >50% of your current bottleneck?
- Do you need real-time tracking, or only faster offline batch processing?
- Do you need full compatibility with existing Tracker project files/workflows?

If compatibility is essential, prefer a hybrid backend. If not, a dedicated Python/C++ pipeline may be the better long-term platform.
