package parsing.statements.parsed;

/**
 * Variable name following the substitution symbol '$'
 */
public class Variable implements ParsedString {
    private final String variableName;

    /**
     * {@code Variable} constructor
     *
     * @param variableName Variable name
     */
    public Variable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String getString() {
        return variableName;
    }
}
