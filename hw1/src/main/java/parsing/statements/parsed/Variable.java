package parsing.statements.parsed;

public class Variable implements ParsedString {
    private final String variableName;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String getString() {
        return variableName;
    }
}
