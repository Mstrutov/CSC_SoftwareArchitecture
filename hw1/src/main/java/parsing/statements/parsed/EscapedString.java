package parsing.statements.parsed;

/**
 * String escaped with quotes
 */
public abstract class EscapedString extends QuoteProcessedString {
    public EscapedString(String str) {
        super(str);
    }
}
