package parsing.statements.parsed;

public class AssignmentOperator implements ParsedString {
    @Override
    public String getString() {
        return "=";
    }

    static private final AssignmentOperator assignmentOperator = new AssignmentOperator();

    static public AssignmentOperator get() {
        return assignmentOperator;
    }
}
