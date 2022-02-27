package execution.commands;

import execution.ResultCode;

public class Cat implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        throw new UnsupportedOperationException();
    }
}
