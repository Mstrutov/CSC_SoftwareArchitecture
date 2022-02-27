package execution.commands;

import execution.ResultCode;

public class Echo implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, String bufferOut) {
        throw new UnsupportedOperationException();
    }
}
