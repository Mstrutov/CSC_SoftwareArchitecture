package parsing;

import parsing.statements.QuotedStmt;
import parsing.statements.RawStmt;
import parsing.statements.parsed.FullQuotedString;
import parsing.statements.parsed.QuoteProcessedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.WeakQuotedString;

import java.util.ArrayList;
import java.util.List;

public class QuoteParser {
    public QuotedStmt parse(RawStmt rawStmt) {
        List<QuoteProcessedString> command = new ArrayList<>();

        StringBuilder currentString = new StringBuilder();

        boolean isWeakQuoted = false;
        boolean isFullQuoted = false;
        for (char ch : rawStmt.getString().toCharArray()) {
            if (ch == '\'' && !isWeakQuoted) {
                if (isFullQuoted) {
                    command.add(new FullQuotedString(currentString.toString()));
                    currentString.setLength(0);
                    isFullQuoted = false;
                } else {
                    command.add(new RawString(currentString.toString()));
                    currentString.setLength(0);
                    isFullQuoted = true;
                }
            } else if (ch == '\"' && !isFullQuoted) {
                if (isWeakQuoted) {
                    command.add(new WeakQuotedString(currentString.toString()));
                    currentString.setLength(0);
                    isWeakQuoted = false;
                } else {
                    command.add(new RawString(currentString.toString()));
                    currentString.setLength(0);
                    isWeakQuoted = true;
                }
            } else {
                currentString.append(ch);
            }
        }
        // TODO: process unclosed quoting, e.g. rawStmt: echo 'ffff
        command.add(new RawString(currentString.toString()));

        return new QuotedStmt(command);
    }
}
