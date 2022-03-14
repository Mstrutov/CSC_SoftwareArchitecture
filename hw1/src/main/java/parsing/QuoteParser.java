package parsing;

import parsing.statements.QuotedStmt;
import parsing.statements.RawStmt;
import parsing.statements.parsed.EscapedString;
import parsing.statements.parsed.FullQuotedString;
import parsing.statements.parsed.QuoteProcessedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.WeakQuotedString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
                int nextFullIndex = parseToNextFullQuote(dataString, unparsedStartIndex, i, command);
                if (nextFullIndex != -1) {
                    unparsedStartIndex = nextFullIndex + 1;
                    i = nextFullIndex + 1;
                }
            } else if (ch == '\"') {
                int nextWeakIndex = parseToNextWeakQuote(dataString, unparsedStartIndex, i, command);
                if (nextWeakIndex != -1) {
                    unparsedStartIndex = nextWeakIndex + 1;
                    i = nextWeakIndex + 1;
                }
            }
        }
        if (unparsedStartIndex != dataString.length()) {
            command.add(new RawString(dataString.substring(unparsedStartIndex)));
        }
        return new QuotedStmt(command);
    }

    private int parseToNextFullQuote(String dataString, int unparsedStartIndex, int i,
                                     List<QuoteProcessedString> command) {
        return parseToNextQuote('\'', dataString, unparsedStartIndex, i, FullQuotedString::new, command);
    }

    private int parseToNextWeakQuote(String dataString, int unparsedStartIndex, int i,
                                     List<QuoteProcessedString> command) {
        return parseToNextQuote('"', dataString, unparsedStartIndex, i, WeakQuotedString::new, command);
    }

    private int parseToNextQuote(char quotation, String dataString, int unparsedStartIndex, int i,
                                 Function<String, ? extends EscapedString> constructor,
                                 List<QuoteProcessedString> command) {
        int nextQuoteIndex = dataString.indexOf(quotation, i + 1);
        if (nextQuoteIndex == -1) {
            return nextQuoteIndex;
        }
        if (unparsedStartIndex != i) {
            command.add(new RawString(dataString.substring(unparsedStartIndex, i)));
        }
        command.add(constructor.apply(dataString.substring(i + 1, nextQuoteIndex)));
        return nextQuoteIndex;
    }
}
