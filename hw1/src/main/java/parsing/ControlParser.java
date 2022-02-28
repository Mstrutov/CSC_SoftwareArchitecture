package parsing;

import parsing.statements.LambdaStmt;
import parsing.statements.QuotedStmt;
import parsing.statements.parsed.QuoteProcessedString;

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
