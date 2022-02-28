package parsing;

import environment.CommandRegistry;
import environment.Environment;
import execution.Executable;
import execution.commands.AssignmentCmd;
import execution.commands.Binary;
import execution.commands.BuiltInCmd;
import execution.commands.ExternalCmd;
import parsing.statements.Stmt;
import parsing.statements.parsed.AssignmentOperator;
import parsing.statements.parsed.EscapedString;
import parsing.statements.parsed.ParsedString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandParser {
    private final CommandRegistry commandRegistry;
    private final Environment environment;

    public CommandParser(CommandRegistry commandRegistry, Environment environment) {
        this.commandRegistry = commandRegistry;
        this.environment = environment;
    }

    public List<Executable> parse(List<Stmt> inStmts) {
        if (inStmts == null) {
            return null;
        }

        List<Executable> executables = new ArrayList<>();
        for (Stmt inStmt : inStmts) {
            executables.add(parseSingle(inStmt));
        }
        return executables;
    }

    private Executable parseSingle(Stmt inStmt) {
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
                    binary = new AssignmentCmd(currentString.toString(), environment);
                    currentString.setLength(0);
                } else {
                    currentString.append('=');
                }
            } else if (parsedString instanceof EscapedString) {
                currentString.append(parsedString.getString());
            } else {
                String rawString = parsedString.getString();

                for (char ch : rawString.toCharArray()) {
                    if (isSpaceSymbol(ch)) {
                        binary = resolve(binary, arguments, currentString);
                    } else {
                        currentString.append(ch);
                    }
                }
            }
        }
        binary = resolve(binary, arguments, currentString);

        return new Executable(binary, arguments);
    }

    private Binary resolve(Binary binary, List<String> arguments, StringBuilder currentString) {
        if (currentString.length() > 0) {
            if (binary == null) {
                binary = resolveBinary(currentString.toString());
            } else {
                arguments.add(currentString.toString());
            }
            currentString.setLength(0);
        }
        return binary;
    }

    private Binary resolveBinary(String binaryString) {
        BuiltInCmd builtInCmd = commandRegistry.getCmd(binaryString);

        return Objects.requireNonNullElseGet(builtInCmd, () -> new ExternalCmd(binaryString));
    }

    // TODO: consider Character.isWhitespace()
    static private boolean isSpaceSymbol(char ch) {
        return ch == ' ';
    }
}
