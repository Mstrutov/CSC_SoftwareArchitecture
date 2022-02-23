package execution;

import execution.commands.Binary;
import java.util.List;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class Executable {
    private final Binary binary;
    private final List<String> args;

    public Executable(Binary binary, List<String> args) {
        this.binary = binary;
        this.args = args;
    }

    public Binary getBinary() {
        return binary;
    }

    public List<String> getArgs() {
        return args;
    }

    public ResultCode execute() throws NotImplementedException {
        // binary.execute(args);

        throw new NotImplementedException("Not yet");
    }
}
