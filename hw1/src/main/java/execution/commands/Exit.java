package execution.commands;

import execution.ResultCode;
/**
 * Exit REPL command class.
 */
public class Exit implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        return ResultCode.exitCode();
    }
}
