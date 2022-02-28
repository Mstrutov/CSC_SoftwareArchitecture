package execution;

import execution.commands.Binary;

import java.util.List;

/**
 * Class which stores parsed data as command class with arguments
 */
public class Executable {
    private final Binary binary;
    private final String[] args;

    /**
     * Construct executable of the command
     *
     * @param binary Command
     * @param args   Arguments of the command
     */
    public Executable(Binary binary, List<String> args) {
        this.binary = binary;

        this.args = args == null
                ? null
                : args.toArray(new String[0]);
    }

    /**
     * Get the command binary
     *
     * @return Binary
     */
    public Binary getBinary() {
        return binary;
    }

    /**
     * Get the command arguments
     *
     * @return Arguments
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Execute command with arguments. Method puts command's output into the buffer.
     *
     * @param buffer Command buffer
     * @return ResultCode of the command
     */
    public ResultCode execute(StringBuilder buffer) {
        return binary.execute(args, buffer);
    }
}
