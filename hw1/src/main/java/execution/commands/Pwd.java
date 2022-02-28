package execution.commands;

import execution.ResultCode;

import java.io.File;

public class Pwd implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        buffer.setLength(0);

        File currentDirFile = new File("");
        buffer.append(currentDirFile.getAbsolutePath());
        return ResultCode.okCode();
    }
}
