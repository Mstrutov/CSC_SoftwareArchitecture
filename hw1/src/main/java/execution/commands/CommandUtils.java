package execution.commands;

/**
 * Utility class for commands
 */
public class CommandUtils {
    /**
     * Utility method to get the first command argument in pipe
     * @param buffer Command buffer
     * @param args Command arguments
     * @return The first argument or buffer, if exists
     */
    public static String getArgumentFromBuffer(StringBuilder buffer, String[] args) {
        if (buffer.isEmpty() && args.length == 0) {
            System.err.println("Required at least 1 argument or non-empty buffer");
            return null;
        }
        String result = buffer.isEmpty() ? args[0] : buffer.toString();

        return result;
    }
}
