package parsing;

import parsing.statements.LambdaStmt;
import parsing.statements.QuotedStmt;
import parsing.statements.parsed.AssignmentOperator;
import parsing.statements.parsed.FullQuotedString;
import parsing.statements.parsed.ParsedString;
import parsing.statements.parsed.QuoteProcessedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.Variable;
import parsing.statements.parsed.WeakQuotedString;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to process '=', '|' and '$' operators
 */
public class ControlParser {
    /**
     * Parses big command into a bunch of small ones. If the command is not a pipe - list will contain only one element.
     *
     * @param quotedStmt Pre-parsed data
     * @return List of small commands in one big command
     */
    public List<LambdaStmt> parse(QuotedStmt quotedStmt) {
        List<ParsedString> command = new ArrayList<>();
        List<LambdaStmt> lambdaStmts = new ArrayList<>();

        StringBuilder currentString = new StringBuilder();
        while (quotedStmt.hasNext()) {
            QuoteProcessedString parsedString = quotedStmt.next();

            if (parsedString instanceof FullQuotedString) {
                // TODO: nothing flushes currently
                command.add(parsedString);
            } else if (parsedString instanceof WeakQuotedString) {
                String weakQuotedString = parsedString.getString();
                boolean isFillingVariable = false;
                for (char ch : weakQuotedString.toCharArray()) {
                    if (isFillingVariable) {
                        if (isSubstitutionSymbol(ch) || isSpaceSymbol(ch)) {
                            flushVariable(currentString, command);
                            isFillingVariable = false;
                        } else {
                            currentString.append(ch);
                        }
                    }
                    else if (isSubstitutionSymbol(ch)) {
                        flushWeakQuotedString(currentString, command);
                        isFillingVariable = true;
                    } else {
                        currentString.append(ch);
                    }
                }
                flushRawString(currentString, command);
                if (!command.isEmpty()) {
                    lambdaStmts.add(new LambdaStmt(command));
                }
            } else {
                String rawString = parsedString.getString();
                boolean isFillingVariable = false;

                for (char ch : rawString.toCharArray()) {
                    if (isFillingVariable) {
                        if (isSubstitutionSymbol(ch) || isSpaceSymbol(ch) || isPipeSymbol(ch) || isAssignmentSymbol(ch)) {
                            flushVariable(currentString, command);
                            isFillingVariable = false;
                        } else {
                            currentString.append(ch);
                            continue;
                        }
                    }

                    if (isAssignmentSymbol(ch)) {
                        flushRawString(currentString, command);
                        command.add(AssignmentOperator.get());
                    } else if (isSubstitutionSymbol(ch)) {
                        flushRawString(currentString, command);
                        isFillingVariable = true;
                    } else if (isPipeSymbol(ch)) {
                        flushRawString(currentString, command);
                        lambdaStmts.add(new LambdaStmt(command));
                        command = new ArrayList<>();
                    } else {
                        currentString.append(ch);
                    }
                }
                if (isFillingVariable) {
                    flushVariable(currentString, command);
                } else {
                    flushRawString(currentString, command);
                }
                if (!command.isEmpty()) {
                    lambdaStmts.add(new LambdaStmt(command));
                }
            }
        }
        return lambdaStmts;
    }

    private void flushRawString(StringBuilder currentString, List<ParsedString> command) {
        command.add(new RawString(currentString.toString()));
        currentString.setLength(0);
    }

    private void flushWeakQuotedString(StringBuilder currentString, List<ParsedString> command) {
        command.add(new WeakQuotedString(currentString.toString()));
        currentString.setLength(0);
    }

    private void flushVariable(StringBuilder currentString, List<ParsedString> command) {
        command.add(new Variable(currentString.toString()));
        currentString.setLength(0);
    }

    static private boolean isSpaceSymbol(char ch) {
        return ch == ' ';
    }

    static private boolean isSubstitutionSymbol(char ch) {
        return ch == '$';
    }

    static private boolean isAssignmentSymbol(char ch) {
        return ch == '=';
    }

    static private boolean isPipeSymbol(char ch) {
        return ch == '|';
    }
}
