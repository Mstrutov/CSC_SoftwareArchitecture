package main.java;

import main.java.environment.CommandRegistry;
import main.java.environment.Environment;
import main.java.execution.Executable;
import main.java.execution.Executor;
import main.java.execution.ResultCode;
import main.java.parsing.CommandParser;
import main.java.parsing.ControlParser;
import main.java.parsing.QuoteParser;
import main.java.parsing.Reader;
import main.java.parsing.statements.LambdaStmt;
import main.java.parsing.statements.QuotedStmt;
import main.java.parsing.statements.RawStmt;
import main.java.parsing.statements.Stmt;

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
