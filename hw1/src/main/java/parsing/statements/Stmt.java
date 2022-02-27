package main.java.parsing.statements;

import main.java.parsing.statements.parsed.QuoteProcessedString;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Stmt {
    private final List<QuoteProcessedString> parts;
    private int index;
    public Stmt(List<QuoteProcessedString> parts) {
        this.parts = parts;
    }

    public Stmt() {
        parts = new ArrayList<>();
    }

    public void addPart(QuoteProcessedString part) {
        this.parts.add(part);
    }

    public List<QuoteProcessedString> getParts() {
        return parts;
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
