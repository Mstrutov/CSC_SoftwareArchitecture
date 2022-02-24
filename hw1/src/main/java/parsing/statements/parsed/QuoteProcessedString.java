package parsing.statements.parsed;

// TODO: ParsedString
public abstract class QuoteProcessedString implements ParsedString {
    private final String str;
    public QuoteProcessedString(String str) {
        this.str = str;
    }

    public String getString() {
        return str;
    }
}
