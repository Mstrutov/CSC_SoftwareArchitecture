package execution.commands;

import execution.ResultCode;

/**
 * Echo linux command class.
 */
public class Echo implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        buffer.setLength(0);
        if (args != null) {
            buffer.append(String.join(" ", args));
        }
        return ResultCode.okCode();
    }
}
