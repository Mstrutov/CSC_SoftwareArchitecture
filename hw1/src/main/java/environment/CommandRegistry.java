package environment;

import execution.commands.BuiltInCmd;
import execution.commands.Cat;
import execution.commands.Echo;
import execution.commands.Exit;
import execution.commands.Pwd;
import execution.commands.Wc;
import java.util.HashMap;
import java.util.Map;

/**
 * Storage for known commands.
 */
public class CommandRegistry {
    private final Map<String, BuiltInCmd> cmdEnvironment;

    /**
     * {@code CommandRegistry} constructor. All known commands are initialized here
     */
    public CommandRegistry() {
        this.cmdEnvironment = new HashMap<>();
        cmdEnvironment.put("cat", new Cat());
        cmdEnvironment.put("echo", new Echo());
        cmdEnvironment.put("exit", new Exit());
        cmdEnvironment.put("pwd", new Pwd());
        cmdEnvironment.put("wc", new Wc());
    }

    /**
     * Returns the command class of the required command.
     *
     * @param cmdName String name of the command
     * @return Class of the command if {@code cmdName} is found. Null otherwise.
     */
    public BuiltInCmd getCmd(String cmdName) {
        return cmdEnvironment.get(cmdName);
    }
}
