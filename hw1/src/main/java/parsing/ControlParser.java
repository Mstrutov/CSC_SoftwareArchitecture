package parsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import parsing.statements.LambdaStmt;
import parsing.statements.QuotedStmt;
import parsing.statements.parsed.AssignmentOperator;
import parsing.statements.parsed.EscapedString;
import parsing.statements.parsed.ParsedString;
import parsing.statements.parsed.QuoteProcessedString;
import parsing.statements.parsed.RawString;

/**
 * Class used to process '=' and '$' operators
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

            // TODO: phase 2 -- parse $ in WQS
            if (parsedString instanceof EscapedString) {
                command.add(parsedString);
            } else {
                String rawString = parsedString.getString();

                for (char ch : rawString.toCharArray()) {
                    if (isAssignmentSymbol(ch)) {
                        flushRawString(currentString, command);
                        command.add(AssignmentOperator.get());
                    } else if (isPipeSymbol(ch)) {
                        flushRawString(currentString, command);
                        lambdaStmts.add(new LambdaStmt(command));
                        command = new ArrayList<>();
                    } else {
                        currentString.append(ch);
                    }
                }
                flushRawString(currentString, command);
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

    static private boolean isAssignmentSymbol(char ch) {
        return ch == '=';
    }

    static private boolean isPipeSymbol(char ch) {
        return ch == '|';
    }
}
