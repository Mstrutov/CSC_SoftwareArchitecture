package main.java.execution.commands;

import main.java.environment.Environment;
import main.java.execution.ResultCode;

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
        String variableValue = "";
        if (args != null && args.length > 0) {
            environment.setValue(variableName, variableValue);
        }

        return ResultCode.okCode();
    }
}
