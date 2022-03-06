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
        int unparsedStartIndex = 0;
        String dataString = rawStmt.getString();

        for (int i = 0; i < dataString.length(); i++) {
            char ch = dataString.charAt(i);
            if (ch == '\'') {
                int nextFullIndex = dataString.indexOf('\'', i + 1);
                if (nextFullIndex == -1) {
                    continue;
                }
                if (unparsedStartIndex != i) {
                    command.add(new RawString(dataString.substring(unparsedStartIndex, i)));
                }
                command.add(new FullQuotedString(dataString.substring(i + 1, nextFullIndex)));
                unparsedStartIndex = nextFullIndex + 1;
                i = nextFullIndex + 1;
            } else if (ch == '\"') {
                int nextWeakIndex = dataString.indexOf('\"', i + 1);
                if (nextWeakIndex == -1) {
                    continue;
                }
                if (unparsedStartIndex != i) {
                    command.add(new RawString(dataString.substring(unparsedStartIndex, i)));
                }
                command.add(new WeakQuotedString(dataString.substring(i + 1, nextWeakIndex)));
                unparsedStartIndex = nextWeakIndex + 1;
                i = nextWeakIndex + 1;
            }
        }
        if (unparsedStartIndex != dataString.length()) {
            command.add(new RawString(dataString.substring(unparsedStartIndex)));
        }
        return new QuotedStmt(command);
    }
}
