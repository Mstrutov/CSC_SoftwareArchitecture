package parsing;

import java.util.ArrayList;
import java.util.List;
import parsing.statements.QuotedStmt;
import parsing.statements.RawStmt;
import parsing.statements.parsed.FullQuotedString;
import parsing.statements.parsed.QuoteProcessedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.WeakQuotedString;

/**
 * Class that processes quoted arguments. Wraps arguments in corresponding classes.
 */
public class QuoteParser {
    /**
     * Parsing method. Parses data and wraps it with {@code FullQuotedString}, {@code WeakQuotedString} or {@code RawString}.
     *
     * @param rawStmt Raw String
     * @return Input with processed quotes
     */
    public QuotedStmt parse(RawStmt rawStmt) {
        List<QuoteProcessedString> command = new ArrayList<>();

        StringBuilder currentString = new StringBuilder();

        boolean isWeakQuoted = false;
        boolean isFullQuoted = false;
        for (char ch : rawStmt.getString().toCharArray()) {
            if (ch == '\'' && !isWeakQuoted) {
                if (isFullQuoted) {
                    if (!currentString.isEmpty()) {
                        command.add(new FullQuotedString(currentString.toString()));
                        currentString.setLength(0);
                    }
                    isFullQuoted = false;
                } else {
                    if (!currentString.isEmpty()) {
                        command.add(new RawString(currentString.toString()));
                        currentString.setLength(0);
                    }
                    isFullQuoted = true;
                }
            } else if (ch == '\"' && !isFullQuoted) {
                if (isWeakQuoted) {
                    if (!currentString.isEmpty()) {
                        command.add(new WeakQuotedString(currentString.toString()));
                        currentString.setLength(0);
                    }
                    isWeakQuoted = false;
                } else {
                    if (!currentString.isEmpty()) {
                        command.add(new RawString(currentString.toString()));
                        currentString.setLength(0);
                    }
                    isWeakQuoted = true;
                }
            } else {
                currentString.append(ch);
            }
        }
        if (!currentString.isEmpty() || isWeakQuoted || isFullQuoted) {
            command.add(new RawString((isWeakQuoted ? "\"" : "") + (isFullQuoted ? '\'' : "") + currentString));
        }
        return new QuotedStmt(command);
    }
}
