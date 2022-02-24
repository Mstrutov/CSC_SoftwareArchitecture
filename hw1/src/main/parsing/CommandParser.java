package main.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import main.environment.CommandRegistry;
import main.execution.Executable;
import main.execution.commands.AssignmentCmd;
import main.execution.commands.Binary;
import main.execution.commands.BuiltInCmd;
import main.execution.commands.ExternalCmd;
import main.parsing.statements.LambdaStmt;
import main.parsing.statements.parsed.AssignmentOperator;
import main.parsing.statements.parsed.EscapedString;
import main.parsing.statements.parsed.ParsedString;
import main.parsing.statements.parsed.RawString;

public class CommandParser {
    private final CommandRegistry commandRegistry;

    public CommandParser(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    public List<Executable> parse(List<LambdaStmt> inStmts) {
        if (inStmts == null) {
            return null;
        }

        List<Executable> executables = new ArrayList<>();
        for (LambdaStmt inStmt : inStmts) {
            executables.add(parseSingle(inStmt));
        }
        return executables;
    }

    private Executable parseSingle(LambdaStmt inStmt) {
        if (inStmt == null) {
            throw new NullPointerException(); // inStmt should not be null. assert instead?
        }

        Binary binary = null;
        List<String> arguments = new ArrayList<>();

        StringBuilder currentString = new StringBuilder();
        while (inStmt.hasNext()) {
            ParsedString parsedString = inStmt.next();

            // TODO: come up with some polymorphism
            if (parsedString instanceof AssignmentOperator) {
                if (binary == null) {
                    binary = new AssignmentCmd(); // TODO: pass varName to assign to
                } else {
                    currentString.append('=');
                }
            } else if (parsedString instanceof EscapedString) {
                // TODO: move getString() up to ParsedString
                currentString.append(((EscapedString) parsedString).getString());
            } else {
                String rawString = ((RawString) parsedString).getString();

                for (char ch : rawString.toCharArray()) { // Horrible nested stuff, I know...
                    if (isSpaceSymbol(ch)) {
                        if (currentString.length() > 0) {
                            if (binary == null) {
                                binary = resolveBinary(currentString.toString());
                            } else {
                                arguments.add(currentString.toString());
                                currentString.setLength(0);
                            }
                        }
                    } else {
                        currentString.append(ch);
                    }
                }
            }
        }
        if (currentString.length() > 0) {
            arguments.add(currentString.toString());
        }

        return new Executable(binary, arguments);
    }

    private Binary resolveBinary(String binaryString) {
        BuiltInCmd builtInCmd = commandRegistry.getCmd(binaryString);

        // TODO: pass binaryString to ExternalCmd
        return Objects.requireNonNullElseGet(builtInCmd, ExternalCmd::new);
    }

    // TODO: consider Character.isWhitespace()
    static private boolean isSpaceSymbol(char ch) {
        return ch == ' ';
    }
}
