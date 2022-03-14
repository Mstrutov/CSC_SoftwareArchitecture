package parsing.statements;

/**
 * Class that contains raw data.
 */
public class RawStmt {
    private final String str;

    /**
     * Constructor of {@code RawStmt}. Collects the given String.
     *
     * @param str - String to store.
     */
    public RawStmt(String str) {
        this.str = str;
    }

    /**
     * Getter of {@code RawStmt}.
     *
     * @return Stored String
     */
    public String getString() {
        return str;
    }
}
