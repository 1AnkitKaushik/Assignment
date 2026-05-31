import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        DataStore          store      = new DataStore();
        DistanceCalculator calculator = new DistanceCalculator();
        ResultPrinter      printer    = new ResultPrinter();


        // ── Step 2: Generate 1000 locations ───────────────────
        System.out.println("Generating locations...");
        store.generate();

       // printer.printSample(store.getAll(), 8);

        // ── Step 3: Read the 1001st coordinate from the user ──
        double queryX = 0;
        double queryY = 0;

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("─── Enter the 1001st coordinate ─────────────────────────");
            System.out.print("  x-coordinate : ");
            queryX = sc.nextDouble();
            System.out.print("  y-coordinate : ");
            queryY = sc.nextDouble();
        }

        System.out.println();

        // ── Step 4: Compute all 1000 distances ────────────────
        System.out.println("Computing distances...");
        calculator.compute(store.getAll(), queryX, queryY);

        // ── Step 5: Display top 5 nearest ─────────────────────
       // TreeMap<Double, Location> top5 = calculator.getTopN(5);
      //  printer.printTopN(top5, queryX, queryY);

        // ── Step 6: Display the minimum distance winner ───────
        Map.Entry<Double, Location> nearest = calculator.getMinimum();
        printer.printMinimum(nearest);

        // ── Step 7: Demonstrate HashMap PIN look-up ───────────
        int winnerPin = nearest.getValue().getPin();
        Location fromMap = store.getByPin(winnerPin);
        printer.printPinLookup(fromMap);
    }
}
