package parsing.statements;

import parsing.statements.parsed.ParsedString;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LambdaStmt {
    private final List<ParsedString> parts;
    private int index = 0;

    public LambdaStmt(List<ParsedString> parts) {
        this.parts = parts;
    }

    public LambdaStmt() {
        parts = new ArrayList<>();
    }

    public void addPart(ParsedString part) {
        this.parts.add(part);
    }

    public List<ParsedString> getParts() {
        return parts;
    }

    // TODO: hasNext(), next() to the architecture diagram
    public boolean hasNext() {
        return index < parts.size();
    }

    public ParsedString next() {
        if (!hasNext()) {
            throw new NoSuchElementException("There is no next QuoteProcessedString");
        }
        return parts.get(index++);
    }
}
