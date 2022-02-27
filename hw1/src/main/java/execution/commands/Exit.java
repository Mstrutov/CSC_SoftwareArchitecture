package execution.commands;

import execution.ResultCode;

public class Exit implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        return ResultCode.exitCode();
    }
}
