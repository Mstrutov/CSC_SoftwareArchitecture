package parsing;

import environment.Environment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parsing.statements.LambdaStmt;
import parsing.statements.Stmt;
import parsing.statements.parsed.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubstitutorTest {

    private static final Environment environment = new Environment();
    private static final Substitutor substitutor = new Substitutor(environment);

    private static final String SIMPLE_VALUE_KEY = "simpleValue";
    private static final String VALUE_IN_SINGLE_QUOTES_KEY = "valueInSingleQuotes";
    private static final String VALUE_IN_DOUBLE_QUOTES_KEY = "valueInDoubleQuotes";
    private static final String EMPTY_VALUE_KEY = "emptyValue";

    private static final String SIMPLE_VALUE = "value";
    private static final String VALUE_IN_SINGLE_QUOTES = "'value'";
    private static final String VALUE_IN_DOUBLE_QUOTES = "\"value\"";
    private static final String EMPTY_VALUE = "";

    @BeforeAll
    static void fillEnvironment(){
        environment.setValue(SIMPLE_VALUE_KEY, SIMPLE_VALUE);
        environment.setValue(VALUE_IN_SINGLE_QUOTES_KEY, VALUE_IN_SINGLE_QUOTES);
        environment.setValue(VALUE_IN_DOUBLE_QUOTES_KEY, VALUE_IN_DOUBLE_QUOTES);
        environment.setValue(EMPTY_VALUE_KEY, EMPTY_VALUE);
    }

    @Test
    public void nullOnNullTest() {
        List<LambdaStmt> inStmts = null;

        List<Stmt> actual = substitutor.substitute(inStmts);
        assertNull(actual);
    }

    @Test
    public void emptyOnEmptyTest() {
        List<LambdaStmt> inStmts = new ArrayList<>();

        int actual = substitutor.substitute(inStmts).size();
        int expected = 0;

        assertEquals(expected, actual);
    }

    @Test
    public void noVariableAsEmptyValue() {
        List<LambdaStmt> inStmts = new ArrayList<>();
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new Variable("missingKey"));
        inStmts.add(inStmt);

        Stmt actual = substitutor.substitute(inStmts).get(0);

        ParsedString actualSubstitution = actual.getParts().get(0);
        RawString expectedSubstitution = new RawString("");

        assertEqualParsedStrings(expectedSubstitution, actualSubstitution);
    }


    @ParameterizedTest
    @ValueSource(strings = {SIMPLE_VALUE_KEY, VALUE_IN_SINGLE_QUOTES_KEY, VALUE_IN_DOUBLE_QUOTES_KEY})
    public void SubstitutesDifferentKindsOfVariables(String variableKey) {
        List<LambdaStmt> inStmts = new ArrayList<>();
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new Variable(variableKey));
        inStmts.add(inStmt);

        Stmt actual = substitutor.substitute(inStmts).get(0);

        ParsedString actualSubstitution = actual.getParts().get(0);
        RawString expectedSubstitution = new RawString(environment.getValue(variableKey));

        assertEqualParsedStrings(expectedSubstitution, actualSubstitution);
    }

    private void assertEqualParsedStrings(ParsedString expected, ParsedString actual) {
        assertEquals(expected.getClass(), actual.getClass());
        assertEquals(expected.getString(), actual.getString());
    }
}
