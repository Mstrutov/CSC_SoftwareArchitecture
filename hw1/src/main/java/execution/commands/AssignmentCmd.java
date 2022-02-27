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
    public ResultCode execute(String[] args) {
        String variableValue = "";
        if (args != null && args.length > 0) {
            environment.setValue(variableName, variableValue);
        }

        return new ResultCode(0, false);
    }
}
