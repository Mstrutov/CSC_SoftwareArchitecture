package parsing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import environment.CommandRegistry;
import execution.Executable;
import execution.commands.AssignmentCmd;
import execution.commands.ExternalCmd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parsing.statements.LambdaStmt;
import parsing.statements.parsed.AssignmentOperator;
import parsing.statements.parsed.FullQuotedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.WeakQuotedString;

public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser(new CommandRegistry());

    @Test
    public void nullOnNullTest() {
        List<LambdaStmt> inStmt = null;

        List<Executable> actual = commandParser.parse(inStmt);
        assertNull(actual);
    }

    @Test
    public void emptyOnEmptyTest() {
        List<LambdaStmt> inStmt = new ArrayList<>();

        int actual = commandParser.parse(inStmt).size();
        int expected = 0;

        assertEquals(expected, actual);
    }

    //echo abc, echo  abc, ...
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 100})
    public void parsesRawStringSingleSpaceTest(int numSpaces) {
        LambdaStmt inStmt = new LambdaStmt();
        String spaces = " ".repeat(numSpaces);
        inStmt.addPart(new RawString("echo" + spaces + "abc"));
        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: proper BuiltInCmd after CR filling
        Class<?> expectedBinaryClass = ExternalCmd.class;
        String[] expectedArgs = new String[]{"abc"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //x=abc
    @Test
    public void parsesAssignmentOperatorNoSpaceTest() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new RawString("x"));
        inStmt.addPart(new AssignmentOperator());
        inStmt.addPart(new RawString("abc"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: check varName == x after varName pass to AssignmentCmd
        Class<?> expectedBinaryClass = AssignmentCmd.class;
        String[] expectedArgs = new String[]{"abc"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //x=abc def
    @Test
    public void parsesAssignmentOperatorSingleSpaceTest() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new RawString("x"));
        inStmt.addPart(new AssignmentOperator());
        inStmt.addPart(new RawString("abc def"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: check varName == x after varName pass to AssignmentCmd
        Class<?> expectedBinaryClass = AssignmentCmd.class;
        // TODO: actually, bash would interpret def as a separate cmd.
        //  NinB views it as an arg, for now
        String[] expectedArgs = new String[]{"abc", "def"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //cat 'abc ff=def' amd
    @Test
    public void parsesFullQuotedTest() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new RawString("cat "));
        inStmt.addPart(new FullQuotedString("abc ff=def"));
        inStmt.addPart(new RawString(" amd"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: proper BuiltInCmd after CR filling
        Class<?> expectedBinaryClass = ExternalCmd.class;
        String[] expectedArgs = new String[]{"abc ff=def", "amd"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //cat "abc ff=def " amd
    @Test
    public void parsesWeakQuotedTest() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new RawString("cat "));
        inStmt.addPart(new WeakQuotedString("abc ff=def "));
        inStmt.addPart(new RawString(" amd"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: proper BuiltInCmd after CR filling
        Class<?> expectedBinaryClass = ExternalCmd.class;
        String[] expectedArgs = new String[]{"abc ff=def ", "amd"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //"echo" abc
    @Test
    public void parsesWeakQuotedBinary() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new WeakQuotedString("echo"));
        inStmt.addPart(new RawString(" abc"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: proper BuiltInCmd after CR filling
        Class<?> expectedBinaryClass = ExternalCmd.class;
        String[] expectedArgs = new String[]{"abc"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //'echo' abc
    @Test
    public void parsesFullQuotedBinary() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new FullQuotedString("echo"));
        inStmt.addPart(new RawString(" abc"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: proper BuiltInCmd after CR filling
        Class<?> expectedBinaryClass = ExternalCmd.class;
        String[] expectedArgs = new String[]{"abc"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //'x='3
    @Test
    public void parsesFullQuotedAssignment() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new FullQuotedString("x="));
        inStmt.addPart(new RawString("3"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: proper BuiltInCmd after CR filling
        Class<?> expectedBinaryClass = ExternalCmd.class;
        String[] expectedArgs = new String[0];

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //"x="3
    @Test
    public void parsesWeakQuotedAssignment() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new WeakQuotedString("x="));
        inStmt.addPart(new RawString("3"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: proper BuiltInCmd after CR filling
        Class<?> expectedBinaryClass = ExternalCmd.class;
        String[] expectedArgs = new String[0];

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //echo =3
    @Test
    public void parsesAssignmentAfterBinary() {
        LambdaStmt inStmt = new LambdaStmt();
        inStmt.addPart(new RawString("echo =3"));

        List<LambdaStmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs().toArray(String[]::new);

        //TODO: proper BuiltInCmd after CR filling
        Class<?> expectedBinaryClass = ExternalCmd.class;
        String[] expectedArgs = new String[]{"=3"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }
}
