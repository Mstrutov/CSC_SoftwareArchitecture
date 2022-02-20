package parsing;

import parsing.statements.QuotedStmt;
import parsing.statements.RawStmt;
import parsing.statements.parsed.FullQuotedString;
import parsing.statements.parsed.QuoteProcessedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.WeakQuotedString;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuoteParser {
    public QuotedStmt parse(RawStmt rawStmt) {
//        TODO: fix method
        /*Scanner sc = new Scanner(rawStmt.getString());
        List<QuoteProcessedString> parts = new ArrayList<>();
        while (sc.hasNext()) {
            String tmp = sc.next();
            if (tmp.isEmpty()) {
                continue;
            }
            if (tmp.charAt(0) == '\'' && tmp.charAt(tmp.length() - 1) == '\'') {
                parts.add(new FullQuotedString(tmp.substring(1, tmp.length() - 1)));
            } else if (tmp.charAt(0) == '\"' && tmp.charAt(tmp.length() - 1) == '\"') {
                parts.add(new WeakQuotedString(tmp.substring(1, tmp.length() - 1)));
            } else {
                parts.add(new RawString(tmp));
            }
        }
        return new QuotedStmt(parts);*/
        return null;
    }

}
