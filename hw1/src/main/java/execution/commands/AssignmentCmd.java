package execution.commands;

import environment.Environment;
import execution.ResultCode;

/**
 * Class for '=' operator
 */
public class AssignmentCmd implements Binary {
    private final String variableName;
    private final Environment environment; //TODO: fabric?

    /**
     * Constructor of {@code AssignmentCmd} class
     *
     * @param variableName Environment variable name
     * @param environment  Environment of the REPL
     */
    public AssignmentCmd(String variableName, Environment environment) {
        this.variableName = variableName;
        this.environment = environment;
    }

    /**
     * Get variable name
     *
     * @return Variable name
     */
    public String getVariableName() {
        return variableName;
    }

    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        String variableValue = args != null && args.length > 0
                ? args[0]
                : "";
        environment.setValue(variableName, variableValue);

        return ResultCode.okCode();
    }
}
