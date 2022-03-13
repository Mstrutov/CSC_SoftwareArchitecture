package parsing;

import environment.Environment;
import parsing.statements.LambdaStmt;
import parsing.statements.Stmt;
import parsing.statements.parsed.ParsedString;
import parsing.statements.parsed.RawString;
import parsing.statements.parsed.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * The component responsible for substitution of environment variables into the pre-parsed statement.
 */
public class Substitutor {
    private final Environment environment;

    /**
     * {@code Substitutor} constructor
     *
     * @param environment - an environment with variables to substitute
     */
    public Substitutor(Environment environment) {
        this.environment = environment;
    }

    /**
     * Uses the environment passed to the constructor to replace variables in the pre-parsed statements with
     * their values. Takes every Variable x in {@code inStmts} and replaces it with RawString containing
     * the Variable value.
     *
     * @param inStmts - list of statements to substitute into
     * @return list of statements with substituted variable values
     * */
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
