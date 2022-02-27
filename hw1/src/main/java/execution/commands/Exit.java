package main.java.execution.commands;

import main.java.execution.ResultCode;

public class Exit implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        return ResultCode.exitCode();
    }
}
