import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * DistanceCalculator.java
 * ─────────────────────────────────────────────────────────────
 * Applies the Euclidean distance formula to every stored Location
 * relative to a user-supplied query point, then organises the
 * results in a TreeMap so the minimum is always at the front.
 *
 *   d = √( (x₂−x₁)² + (y₂−y₁)² )
 *
 * Collection choice — TreeMap<Double, Location>
 *   A TreeMap is backed by a Red-Black tree.  Keys (distances) are
 *   kept sorted automatically in O(log n) per insertion, so after
 *   all 1000 insertions:
 *     • firstEntry() → minimum distance in O(1)
 *     • Iterating the first N entries → top-N nearest, in order
 *   No separate sort step is needed.
 *
 * Collision handling
 *   Two distinct locations can produce identical double distances
 *   (rare but possible).  A plain put() would silently overwrite
 *   the existing entry.  We resolve this with Math.nextUp(d) which
 *   advances d by the smallest representable double increment,
 *   keeping both entries without corrupting the sort order.
 * ─────────────────────────────────────────────────────────────
 */
public class DistanceCalculator {

    // ── State ─────────────────────────────────────────────────
    private final TreeMap<Double, Location> distanceMap;
    private double queryX;
    private double queryY;

    // ── Constructor ───────────────────────────────────────────
    public DistanceCalculator() {
        distanceMap = new TreeMap<>();
    }

    public static double euclidean(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // ── Main computation ──────────────────────────────────────
    /**
     * Iterates all stored locations, computes their distance to
     * (qx, qy) and loads the result into the TreeMap.
     *
     * @param locations  the 1000 Location objects from DataStore
     * @param qx         query x-coordinate (the 1001st point)
     * @param qy         query y-coordinate (the 1001st point)
     */
    public void compute(List<Location> locations, double qx, double qy) {
        this.queryX = qx;
        this.queryY = qy;
        distanceMap.clear();

        for (Location loc : locations) {
            double d = euclidean(loc.getX(), loc.getY(), qx, qy);

            // Collision guard: nudge duplicate keys by one ULP
            while (distanceMap.containsKey(d)) {
                d = Math.nextUp(d);
            }

            distanceMap.put(d, loc);  // TreeMap keeps keys sorted ascending
        }

        System.out.printf("  Distances computed   : %,d%n", distanceMap.size());
        System.out.printf("  Query point          : (%.3f, %.3f)%n%n", qx, qy);
    }

    // ── Result accessors ──────────────────────────────────────
    /**
     * Returns the entry with the smallest distance (TreeMap.firstEntry).
     */
    public Map.Entry<Double, Location> getMinimum() {
        return distanceMap.firstEntry();
    }

    /**
     * Returns the top N nearest locations as an ordered sub-map view.
     * Callers iterate entrySet() to walk them in ascending distance order.
     *
     * @param n  how many results to include

    public TreeMap<Double, Location> getTopN(int n) {
        TreeMap<Double, Location> result = new TreeMap<>();
        int count = 0;
        for (Map.Entry<Double, Location> entry : distanceMap.entrySet()) {
            if (count++ >= n) break;
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    */

    /** Exposes the full sorted map for any additional analysis. */
    public TreeMap<Double, Location> getDistanceMap() {
        return distanceMap;
    }

    public double getQueryX() { return queryX; }
    public double getQueryY() { return queryY; }
}
