package main.java.execution.commands;

import main.java.execution.ResultCode;

public class ExternalCmd implements Binary {
    private final String command;

    public ExternalCmd(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        throw new UnsupportedOperationException();
    }
}
