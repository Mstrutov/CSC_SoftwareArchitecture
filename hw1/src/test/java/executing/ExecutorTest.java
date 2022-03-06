package executing;

import environment.CommandRegistry;
import environment.Environment;
import execution.Executable;
import execution.Executor;
import execution.ResultCode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsing.CommandParser;
import parsing.ControlParser;
import parsing.QuoteParser;
import parsing.statements.LambdaStmt;
import parsing.statements.QuotedStmt;
import parsing.statements.RawStmt;
import parsing.statements.Stmt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutorTest {

    private static QuoteParser quoteParser;
    private static ControlParser controlParser;
    private static CommandParser commandParser;
    private static Executor executor;

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;


    @BeforeAll
    static void init() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        quoteParser = new QuoteParser();
        controlParser = new ControlParser();
        commandParser = new CommandParser(new CommandRegistry(), new Environment());
        executor = new Executor();

        String fileName = "testfile1";
        Path newFilePath = Paths.get(fileName);
        try {
            Files.createFile(newFilePath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void reset() {
        outContent.reset();
        errContent.reset();
    }

    @AfterAll
    static void close() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        try {
            Files.deleteIfExists(Paths.get("testfile1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Executable> getExecutablesFromString(String input) {
        RawStmt rawStmt = new RawStmt(input);
        QuotedStmt quotedStmt = quoteParser.parse(rawStmt);
        List<LambdaStmt> commands = controlParser.parse(quotedStmt);
        // Substitutor?
        List<Stmt> parsedCommands = commands.stream()
                .map(item -> new Stmt(item.getParts()))
                .collect(Collectors.toList());
        return commandParser.parse(parsedCommands);
    }

    @Test
    public void quoteTest() {
        assertEquals(executor.execute(getExecutablesFromString("echo lol")), executor.execute(getExecutablesFromString("echo \"lol\"")));
        assertEquals(executor.execute(getExecutablesFromString("echo lol")), executor.execute(getExecutablesFromString("echo 'lol'")));
        assertTrue(errContent.toString().isEmpty());
        // TODO: add more tests for full quoted commands
    }

    @Test
    public void fullQuoteEchoTest() {
        ResultCode resultCode = executor.execute(getExecutablesFromString("echo '"));
        assertFalse(resultCode.isExitSignal());
        assertEquals(resultCode.getReturnCode(), 0);
        assertEquals(outContent.toString(), "'" + System.lineSeparator());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    public void weakQuoteEchoTest() {
        ResultCode resultCode = executor.execute(getExecutablesFromString("echo \""));
        assertFalse(resultCode.isExitSignal());
        assertEquals(resultCode.getReturnCode(), 0);
        assertEquals(outContent.toString(), "\"" + System.lineSeparator());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    public void pwdTest() {
        ResultCode resultCode = executor.execute(getExecutablesFromString("pwd"));
        assertFalse(resultCode.isExitSignal());
        assertEquals(resultCode.getReturnCode(), 0);
        assertEquals(outContent.toString(), System.getProperty("user.dir") + System.lineSeparator());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    public void catTest() {
        ResultCode resultCode = executor.execute(getExecutablesFromString("cat testfile1"));
        assertFalse(resultCode.isExitSignal());
        assertEquals(resultCode.getReturnCode(), 0);
        assertEquals(outContent.toString(), "testfile1" + System.lineSeparator());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    public void exitTest() {
        ResultCode resultCode = executor.execute(getExecutablesFromString("exit"));
        assertTrue(resultCode.isExitSignal());
        assertEquals(resultCode.getReturnCode(), 0);
        assertEquals(outContent.toString(), "Execution was abandoned due to exit command, passed # 0" + System.lineSeparator());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    public void wcTest() {
        ResultCode resultCode = executor.execute(getExecutablesFromString("wc testfile1"));
        assertFalse(resultCode.isExitSignal());
        assertEquals(resultCode.getReturnCode(), 0);
        assertEquals(outContent.toString(), "1" + System.lineSeparator());
        assertTrue(errContent.toString().isEmpty());
    }

    // TODO: add ExternalCmd test

}
