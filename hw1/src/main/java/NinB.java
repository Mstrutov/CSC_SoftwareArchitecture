import environment.CommandRegistry;
import environment.Environment;
import execution.Executable;
import execution.Executor;
import execution.ResultCode;
import parsing.CommandParser;
import parsing.ControlParser;
import parsing.QuoteParser;
import parsing.Reader;
import parsing.statements.LambdaStmt;
import parsing.statements.QuotedStmt;
import parsing.statements.RawStmt;
import parsing.statements.Stmt;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The NinB evaluation state engine.  This is the central class in the NinB
 * application. A {@code NinB} instance holds the evolving compilation and
 * execution state.
 */
public class NinB {
    /**
     * Evaluate the input String, including definition and/or execution, if applicable. The input is checked for errors, unless the errors can be deferred , errors will not abort evaluation.
     * The input should be exactly one command. To break arbitrary input into individual complete snippets, use "quit".
     *
     * @param args required by java
     */
    public static void main(String[] args) {
        Reader reader = new Reader(System.in);
        QuoteParser quoteParser = new QuoteParser();
        ControlParser controlParser = new ControlParser();
        CommandParser commandParser = new CommandParser(new CommandRegistry(), new Environment());
        Executor executor = new Executor();
        while (true) {
            RawStmt rawStmt = reader.read();
            QuotedStmt quotedStmt = quoteParser.parse(rawStmt);
            List<LambdaStmt> commands = controlParser.parse(quotedStmt);
            // Substitutor?
            List<Stmt> parsedCommands = commands.stream()
                    .map(item -> new Stmt(item.getParts()))
                    .collect(Collectors.toList());
            List<Executable> executables = commandParser.parse(parsedCommands);
            ResultCode resultCode = executor.execute(executables);
            System.out.println("Command finished with result code " + resultCode.getReturnCode());
            if (resultCode.isExitSignal()) {
                break;
            }
        }
    }
}
