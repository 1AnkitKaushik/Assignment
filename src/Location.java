
public class Location {

    // ── Fields ────────────────────────────────────────────────
    private final int    pin;
    private final double x;
    private final double y;
    private final String state;
    private final String country;

    // ── Constructor ───────────────────────────────────────────
    public Location(int pin, double x, double y, String state, String country) {
        this.pin     = pin;
        this.x       = x;
        this.y       = y;
        this.state   = state;
        this.country = country;
    }

    // ── Getters ───────────────────────────────────────────────
    public int    getPin()     { return pin;     }
    public double getX()       { return x;       }
    public double getY()       { return y;       }
    public String getState()   { return state;   }
    public String getCountry() { return country; }

    // ── Display ───────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
            "PIN %-6d | (%8.3f, %8.3f) | %-20s | %s",
            pin, x, y, state, country
        );
    }
}
