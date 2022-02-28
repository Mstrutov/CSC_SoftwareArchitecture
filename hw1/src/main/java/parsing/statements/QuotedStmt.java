package parsing.statements;

import parsing.statements.parsed.QuoteProcessedString;

import java.util.List;
import java.util.NoSuchElementException;

import parsing.statements.parsed.QuoteProcessedString;

/**
 * Class that contains data with parsed quotes.
 */
public class QuotedStmt {
    private final List<QuoteProcessedString> parts;
    private int index;

    /**
     * Initialise data
     *
     * @param parts arguments of CLI command
     */
    public QuotedStmt(List<QuoteProcessedString> parts) {
        this.parts = parts;
    }

    /**
     * Returns true if the next token matches the pattern constructed from the
     * specified string.
     *
     * @return true if and only if this class has another token matching
     * the specified pattern
     */
    public boolean hasNext() {
        return index < parts.size();
    }

    /**
     * Returns the next token if it matches the pattern constructed from the specified string.
     * If the match is successful, the class advances past the input that matched the pattern.
     *
     * @return the next token
     */
    public QuoteProcessedString next() {
        if (!hasNext()) {
            throw new NoSuchElementException("There is no next QuoteProcessedString");
        }
        return parts.get(index++);
    }

}
