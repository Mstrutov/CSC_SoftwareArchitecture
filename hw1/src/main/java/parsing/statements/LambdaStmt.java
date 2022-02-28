package parsing.statements;

import parsing.statements.parsed.QuoteProcessedString;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LambdaStmt {
    private final List<QuoteProcessedString> parts;
    private int index = 0;

    public LambdaStmt(List<QuoteProcessedString> parts) {
        this.parts = parts;
    }

    public LambdaStmt() {
        parts = new ArrayList<>();
    }

    public void addPart(QuoteProcessedString part) {
        this.parts.add(part);
    }

    public List<QuoteProcessedString> getParts() {
        return parts;
    }

    // TODO: hasNext(), next() to the architecture diagram
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
