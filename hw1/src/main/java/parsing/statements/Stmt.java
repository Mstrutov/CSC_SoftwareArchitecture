package parsing.statements;

import parsing.statements.parsed.ParsedString;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Stmt {
    private final List<ParsedString> parts;
    private int index;
    public Stmt(List<ParsedString> parts) {
        this.parts = parts;
    }

    public Stmt() {
        parts = new ArrayList<>();
    }

    public void addPart(ParsedString part) {
        this.parts.add(part);
    }

    public List<ParsedString> getParts() {
        return parts;
    }

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
