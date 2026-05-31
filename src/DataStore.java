import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * DataStore.java
 * ─────────────────────────────────────────────────────────────
 * Responsible for:
 *   • Generating 1000 random Location objects (seeded RNG)
 *   • Storing them in two complementary collections:
 *       – ArrayList  : preserves insertion order, supports index access
 *       – HashMap    : O(1) lookup by PIN number
 *
 * Design note — why two collections for the same data?
 *   ArrayList  is efficient when you need to iterate every element
 *   (computing all 1000 distances).
 *   HashMap is efficient when you already know a PIN and want the
 *   full Location record instantly (e.g. after finding the winner).
 * ─────────────────────────────────────────────────────────────
 */
public class DataStore {

    // ── Constants ─────────────────────────────────────────────
    public static final int    TOTAL_LOCATIONS = 1000;
    public static final long   SEED            = 42L;
    public static final double RANGE           = 500.0;  // coords in [-500, 500)

    // ── Reference data pools ──────────────────────────────────
    private static final String[] STATES = {
        "Uttar Pradesh", "Maharashtra",    "Tamil Nadu",      "Karnataka",
        "West Bengal",   "Rajasthan",      "Gujarat",         "Bihar",
        "Madhya Pradesh","Andhra Pradesh", "Odisha",          "Telangana",
        "Punjab",        "Haryana",        "Himachal Pradesh"
    };

    private static final String[] COUNTRIES = {
        "India", "Nepal", "Bhutan", "Sri Lanka", "Bangladesh"
    };

    // ── Collections ───────────────────────────────────────────
    private final List<Location>         locationList;   // ordered, for iteration
    private final Map<Integer, Location> pinMap;         // keyed by PIN, for lookup

    // ── Constructor ───────────────────────────────────────────
    public DataStore() {
        locationList = new ArrayList<>(TOTAL_LOCATIONS);
        pinMap       = new HashMap<>(TOTAL_LOCATIONS);
    }

    /**
     * Generates TOTAL_LOCATIONS random Location objects and populates
     * both the ArrayList and the HashMap.
     *
     * Called once from Main before any distance computation.
     */
    public void generate() {
        Random rng = new Random(SEED);   // seeded → reproducible across runs

        for (int i = 1; i <= TOTAL_LOCATIONS; i++) {
            double x       = (rng.nextDouble() * 2 * RANGE) - RANGE;
            double y       = (rng.nextDouble() * 2 * RANGE) - RANGE;
            String state   = STATES  [rng.nextInt(STATES.length)];
            String country = COUNTRIES[rng.nextInt(COUNTRIES.length)];

            Location loc = new Location(i, x, y, state, country);
            locationList.add(loc);      // ArrayList: O(1) amortised append
            pinMap.put(i, loc);         // HashMap:   O(1) insert
        }

        System.out.printf("  ArrayList  : %,d locations stored%n", locationList.size());
        System.out.printf("  HashMap    : %,d PIN→Location entries%n%n", pinMap.size());
    }

    /**
     * Returns the full ordered list for sequential iteration.
     * Callers should treat this as read-only.
     */
    public List<Location> getAll() {
        return locationList;
    }

    /**
     * O(1) lookup by PIN number.
     * Returns null if the PIN does not exist (should never happen
     * in normal flow, but guards against stale references).
     */
    public Location getByPin(int pin) {
        return pinMap.get(pin);
    }
}
