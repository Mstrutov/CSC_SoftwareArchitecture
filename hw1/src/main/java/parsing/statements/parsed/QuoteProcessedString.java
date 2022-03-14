package parsing.statements.parsed;

/**
 * String with parsed quotes
 */
// TODO: ParsedString
public abstract class QuoteProcessedString implements ParsedString {
    private final String str;
    public QuoteProcessedString(String str) {
        this.str = str;
    }

    @Override
    public String getString() {
        return str;
    }
}
