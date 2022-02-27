package execution.commands;

import execution.ResultCode;

public class Exit implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, String bufferOut) {
        throw new UnsupportedOperationException();
    }
}
