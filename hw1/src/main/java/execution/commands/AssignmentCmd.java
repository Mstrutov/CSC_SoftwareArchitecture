package execution.commands;

import environment.Environment;
import execution.ResultCode;

public class AssignmentCmd implements Binary {
    private final String variableName;
    private final Environment environment; //TODO: fabric?

    public AssignmentCmd(String variableName, Environment environment) {
        this.variableName = variableName;
        this.environment = environment;
    }

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
