package parsing;

import parsing.statements.LambdaStmt;
import parsing.statements.QuotedStmt;
import parsing.statements.parsed.AssignmentOperator;
import parsing.statements.parsed.EscapedString;
import parsing.statements.parsed.ParsedString;
import parsing.statements.parsed.QuoteProcessedString;
import parsing.statements.parsed.RawString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlParser {
    public List<LambdaStmt> parse(QuotedStmt quotedStmt) {
        List<ParsedString> command = new ArrayList<>();

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
                    } else {
                        currentString.append(ch);
                    }
                }
                flushRawString(currentString, command);
            }
        }
        return Collections.singletonList(new LambdaStmt(command));
    }

    private void flushRawString(StringBuilder currentString, List<ParsedString> command) {
        command.add(new RawString(currentString.toString()));
        currentString.setLength(0);
    }

    static private boolean isAssignmentSymbol(char ch) {
        return ch == '=';
    }
}
