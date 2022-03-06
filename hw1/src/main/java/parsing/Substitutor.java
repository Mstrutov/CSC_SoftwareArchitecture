package parsing;

import environment.Environment;
import parsing.statements.LambdaStmt;
import parsing.statements.Stmt;
import parsing.statements.parsed.ParsedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.Variable;

import java.util.ArrayList;
import java.util.List;

public class Substitutor {
    private Environment environment;

    public Substitutor(Environment environment) {
        this.environment = environment;
    }

    public List<Stmt> substitute(List<LambdaStmt> inStmts) {
        List<Stmt> outStmts = new ArrayList<>();
        for (LambdaStmt inStmt : inStmts) {
            List<ParsedString> substitutedStrings = new ArrayList<>();
            for (ParsedString part : inStmt.getParts()) {
                if (part instanceof Variable) {
                    String variableValue = environment.getValue(part.getString());
                    // TODO: check if <..., RawString, RawString, ...> is parsed correctly down the line
                    substitutedStrings.add(new RawString(variableValue));
                } else {
                    substitutedStrings.add(part);
                }
            }
            outStmts.add(new Stmt(substitutedStrings));
        }
        return outStmts;
    }
}
