package parsing.statements;

import parsing.statements.parsed.QuoteProcessedString;

import java.util.List;
import java.util.NoSuchElementException;

public class QuotedStmt {
    private final List<QuoteProcessedString> parts;
    private int index;
    public QuotedStmt(List<QuoteProcessedString> parts) {
        this.parts = parts;
    }

    public boolean hasNext() {
        return index < parts.size();
    }

    public QuoteProcessedString next() {
        if (!hasNext()) {
            throw new NoSuchElementException("There is no next QuoteProcessedString");
        }
        return parts.get(index++);
    }

}
