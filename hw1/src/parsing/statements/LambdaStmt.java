package parsing.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import parsing.statements.parsed.ParsedString;

public class LambdaStmt {
    private final List<ParsedString> parts = new ArrayList<>();
    private int index = 0;

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
