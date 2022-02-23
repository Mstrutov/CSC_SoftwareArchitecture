package environment;

import execution.commands.BuiltInCmd;
import java.util.Map;

public class CommandRegistry {
    Map<String, BuiltInCmd> cmdEnvironment;

    public BuiltInCmd getCmd(String cmdName) {
        return cmdEnvironment.getOrDefault(cmdName, null);
    }
}
