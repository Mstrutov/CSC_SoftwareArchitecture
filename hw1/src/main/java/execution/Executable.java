package execution;

import execution.commands.Binary;
import java.util.List;

public class Executable {
    private final Binary binary;
    private final String[] args;

    public Executable(Binary binary, List<String> args) {
        this.binary = binary;

        this.args = args == null
                ? null
                : args.toArray(new String[0]);
    }

    public Binary getBinary() {
        return binary;
    }

    public String[] getArgs() {
        return args;
    }

    public ResultCode execute(StringBuilder buffer) {
        binary.execute(args, buffer);

        throw new UnsupportedOperationException();
    }
}
