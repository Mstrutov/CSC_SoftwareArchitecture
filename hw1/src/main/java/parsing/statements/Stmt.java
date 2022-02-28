package parsing.statements;

import parsing.statements.parsed.ParsedString;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that stores commands with substitutions done.
 */
public class Stmt {
    private final List<ParsedString> parts;
    private int index;

    /**
     * Constructor with parsed commands
     *
     * @param parts Commands
     */
    public Stmt(List<ParsedString> parts) {
        this.parts = parts;
    }

    /**
     * Constructor without parsed commands. Creates empty List to store data.
     */
    public Stmt() {
        parts = new ArrayList<>();
    }

    /**
     * Add command
     *
     * @param part Command
     */
    public void addPart(ParsedString part) {
        this.parts.add(part);
    }

    /**
     * Returns all stored commands
     *
     * @return List of commands
     */
    public List<ParsedString> getParts() {
        return parts;
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
    public ParsedString next() {
        if (!hasNext()) {
            throw new NoSuchElementException("There is no next QuoteProcessedString");
        }
        return parts.get(index++);
    }


}
