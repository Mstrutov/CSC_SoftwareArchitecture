package main.java.parsing;

import main.java.parsing.statements.LambdaStmt;
import main.java.parsing.statements.QuotedStmt;
import main.java.parsing.statements.parsed.QuoteProcessedString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlParser {
    public List<LambdaStmt> parse(QuotedStmt quotedStmt) {
        List<QuoteProcessedString> command = new ArrayList<>();
        while (quotedStmt.hasNext()) {
            command.add(quotedStmt.next());
        }
        return Collections.singletonList(new LambdaStmt(command));
    }

}
