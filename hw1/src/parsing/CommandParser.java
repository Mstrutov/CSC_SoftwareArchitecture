package parsing;

import environment.CommandRegistry;
import execution.Executable;
import execution.commands.AssignmentCmd;
import execution.commands.Binary;
import execution.commands.BuiltInCmd;
import execution.commands.ExternalCmd;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import parsing.statements.LambdaStmt;
import parsing.statements.parsed.AssignmentOperator;
import parsing.statements.parsed.EscapedString;
import parsing.statements.parsed.ParsedString;
import parsing.statements.parsed.RawString;

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

    static private boolean isSpaceSymbol(char ch) {
        return ch == ' ';
    }
}
