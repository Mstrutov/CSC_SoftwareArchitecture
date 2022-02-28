package parsing;

import environment.CommandRegistry;
import environment.Environment;
import execution.Executable;
import execution.commands.Cat;
import execution.commands.Echo;
import execution.commands.ExternalCmd;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parsing.statements.Stmt;
import parsing.statements.parsed.FullQuotedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.WeakQuotedString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser(new CommandRegistry(), new Environment());

    @Test
    public void nullOnNullTest() {
        List<Stmt> inStmt = null;

        List<Executable> actual = commandParser.parse(inStmt);
        assertNull(actual);
    }

    @Test
    public void emptyOnEmptyTest() {
        List<Stmt> inStmt = new ArrayList<>();

        int actual = commandParser.parse(inStmt).size();
        int expected = 0;

        assertEquals(expected, actual);
    }

    //echo abc, echo  abc, ...
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 100})
    public void parsesRawStringSingleSpaceTest(int numSpaces) {
        Stmt inStmt = new Stmt();
        String spaces = " ".repeat(numSpaces);
        inStmt.addPart(new RawString("echo" + spaces + "abc"));
        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs();

        Class<?> expectedBinaryClass = Echo.class;
        String[] expectedArgs = new String[]{"abc"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //x=abc
    /*@Test
    public void parsesAssignmentOperatorNoSpaceTest() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new RawString("x"));
        inStmt.addPart(new AssignmentOperator());
        inStmt.addPart(new RawString("abc"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);

        Class<?> actualBinaryClass = actual.getBinary().getClass();
        Class<?> expectedBinaryClass = AssignmentCmd.class;
        assertEquals(expectedBinaryClass, actualBinaryClass);

        String actualVariable = ((AssignmentCmd) actual.getBinary()).getVariableName();
        String[] actualArgs = actual.getArgs();

        String expectedVaribale = "x";
        String[] expectedArgs = new String[]{"abc"};

        assertEquals(expectedVaribale, actualVariable);
        assertArrayEquals(expectedArgs, actualArgs);
    }*/

    //x=abc def
    /*@Test
    public void parsesAssignmentOperatorSingleSpaceTest() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new RawString("x"));
        inStmt.addPart(new AssignmentOperator());
        inStmt.addPart(new RawString("abc def"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);

        Class<?> actualBinaryClass = actual.getBinary().getClass();
        Class<?> expectedBinaryClass = AssignmentCmd.class;
        assertEquals(expectedBinaryClass, actualBinaryClass);

        String actualVariable = ((AssignmentCmd) actual.getBinary()).getVariableName();
        String[] actualArgs = actual.getArgs();

        String expectedVaribale = "x";
        // TODO: actually, bash would interpret def as a separate cmd.
        //  NinB views it as an arg, for now
        String[] expectedArgs = new String[]{"abc", "def"};

        assertEquals(expectedVaribale, actualVariable);
        assertArrayEquals(expectedArgs, actualArgs);
    }*/

    //cat 'abc ff=def' amd
    @Test
    public void parsesFullQuotedTest() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new RawString("cat "));
        inStmt.addPart(new FullQuotedString("abc ff=def"));
        inStmt.addPart(new RawString(" amd"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs();

        Class<?> expectedBinaryClass = Cat.class;
        String[] expectedArgs = new String[]{"abc ff=def", "amd"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //cat "abc ff=def " amd
    @Test
    public void parsesWeakQuotedTest() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new RawString("cat "));
        inStmt.addPart(new WeakQuotedString("abc ff=def "));
        inStmt.addPart(new RawString(" amd"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs();

        Class<?> expectedBinaryClass = Cat.class;
        String[] expectedArgs = new String[]{"abc ff=def ", "amd"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //"echo" abc
    @Test
    public void parsesWeakQuotedBinary() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new WeakQuotedString("echo"));
        inStmt.addPart(new RawString(" abc"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs();

        Class<?> expectedBinaryClass = Echo.class;
        String[] expectedArgs = new String[]{"abc"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //'echo' abc
    @Test
    public void parsesFullQuotedBinary() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new FullQuotedString("echo"));
        inStmt.addPart(new RawString(" abc"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs();

        Class<?> expectedBinaryClass = Echo.class;
        String[] expectedArgs = new String[]{"abc"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //'x='3
    @Test
    public void parsesFullQuotedAssignment() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new FullQuotedString("x="));
        inStmt.addPart(new RawString("3"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);

        Class<?> actualBinaryClass = actual.getBinary().getClass();
        Class<?> expectedBinaryClass = ExternalCmd.class;
        assertEquals(expectedBinaryClass, actualBinaryClass);

        String[] actualArgs = actual.getArgs();
        String actualCommand = ((ExternalCmd)actual.getBinary()).getCommand();

        String[] expectedArgs = new String[0];
        String expectedCommand = "x=3";

        assertEquals(expectedCommand, actualCommand);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //"x="3
    @Test
    public void parsesWeakQuotedAssignment() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new WeakQuotedString("x="));
        inStmt.addPart(new RawString("3"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);

        Class<?> actualBinaryClass = actual.getBinary().getClass();
        Class<?> expectedBinaryClass = ExternalCmd.class;
        assertEquals(expectedBinaryClass, actualBinaryClass);

        String[] actualArgs = actual.getArgs();
        String actualCommand = ((ExternalCmd)actual.getBinary()).getCommand();

        String[] expectedArgs = new String[0];
        String expectedCommand = "x=3";

        assertEquals(expectedCommand, actualCommand);
        assertArrayEquals(expectedArgs, actualArgs);
    }

    //echo =3
    @Test
    public void parsesAssignmentAfterBinary() {
        Stmt inStmt = new Stmt();
        inStmt.addPart(new RawString("echo =3"));

        List<Stmt> inStmts = Collections.singletonList(inStmt);

        Executable actual = commandParser.parse(inStmts).get(0);
        Class<?> actualBinaryClass = actual.getBinary().getClass();
        String[] actualArgs = actual.getArgs();

        Class<?> expectedBinaryClass = Echo.class;
        String[] expectedArgs = new String[]{"=3"};

        assertEquals(expectedBinaryClass, actualBinaryClass);
        assertArrayEquals(expectedArgs, actualArgs);
    }
}
