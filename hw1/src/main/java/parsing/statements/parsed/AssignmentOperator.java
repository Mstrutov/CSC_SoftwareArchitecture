package parsing.statements.parsed;

/**
 * Assignment operator known tokens
 */
public class AssignmentOperator implements ParsedString {
    /**
     * Get assignment operator
     * @return String value of the assignment operator
     */
    @Override
    public String getString() {
        return "=";
    }

    static private final AssignmentOperator assignmentOperator = new AssignmentOperator();

    /**
     * Get assignment operator class
     * @return Assignment operator class
     */
    static public AssignmentOperator get() {
        return assignmentOperator;
    }
}
