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

public class NinB {
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
            if (resultCode.getReturnCode() == 0) {
                System.out.println(resultCode.getReturnCode());;
            } else {
                System.err.println(resultCode.getReturnCode());
            }
            if (resultCode.isExitSignal()) {
                break;
            }
        }
    }
}
