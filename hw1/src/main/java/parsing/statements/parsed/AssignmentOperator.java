package parsing.statements.parsed;

public class AssignmentOperator implements ParsedString {
    @Override
    public String getString() {
        return "=";
    }
}
