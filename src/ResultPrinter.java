import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResultPrinter {

    /**
     * Prints the first {@code count} locations from the list as a
     * quick sanity-check that generation worked correctly.
     *
     * @param locations  full list from DataStore
     * @param count      how many rows to show
     */
   /* public void printSample(List<Location> locations, int count) {
        System.out.printf("─── Sample output: first %d of %,d locations %n",
            count, locations.size());
        for (int i = 0; i < Math.min(count, locations.size()); i++) {
            System.out.println("  " + locations.get(i));
        }
        System.out.println();
    }

   /* public void printTopN(TreeMap<Double, Location> topN, double queryX, double queryY) {

        System.out.printf("  TOP %d NEAREST LOCATIONS  (query point: %.3f, %.3f)%n",
            topN.size(), queryX, queryY);

        System.out.printf("  %-4s %-10s %-12s %-22s %s%n",
            "Rank", "Distance", "PIN", "State", "Country");


        int rank = 1;
        for (Map.Entry<Double, Location> entry : topN.entrySet()) {
            Location loc = entry.getValue();
            System.out.printf("  %-4d %,-10.4f PIN %-6d  %-22s %s%n",
                rank++,
                entry.getKey(),
                loc.getPin(),
                loc.getState(),
                loc.getCountry()
            );
        }
        System.out.println();
    }

    /**
     * Highlights the single nearest location — the answer.
     *
     * @param nearest  Map.Entry returned by DistanceCalculator.getMinimum()
     */
    public void printMinimum(Map.Entry<Double, Location> nearest) {
        Location loc = nearest.getValue();

        System.out.println("MINIMUM DISTANCE — NEAREST LOCATION");

        System.out.printf("  %s%n", loc);
        System.out.printf("  Distance  :  %.6f units%n", nearest.getKey());
        System.out.printf("  Coords    :  (%.4f, %.4f)%n", loc.getX(), loc.getY());

        System.out.println();
    }

    /**
     * Demonstrates HashMap look-up by PIN — confirms O(1) retrieval
     * of the winning location by its pin number.
     *
     * @param lookedUp  Location retrieved from DataStore.getByPin()
     */
    public void printPinLookup(Location lookedUp) {
        System.out.println("─── HashMap look-up by PIN (O(1) retrieval) ");
        if (lookedUp != null) {
            System.out.println("  " + lookedUp);
        } else {
            System.out.println("  [PIN not found]");
        }
        System.out.println();
    }
}
