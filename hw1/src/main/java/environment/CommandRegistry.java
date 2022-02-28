package environment;

import execution.commands.BuiltInCmd;
import execution.commands.Cat;
import execution.commands.Echo;
import execution.commands.Exit;
import execution.commands.Pwd;
import execution.commands.Wc;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private Map<String, BuiltInCmd> cmdEnvironment;

    public CommandRegistry() {
        this.cmdEnvironment = new HashMap<>();
        cmdEnvironment.put("cat", new Cat());
        cmdEnvironment.put("echo", new Echo());
        cmdEnvironment.put("exit", new Exit());
        cmdEnvironment.put("pwd", new Pwd());
        cmdEnvironment.put("wc", new Wc());
    }

    public BuiltInCmd getCmd(String cmdName) {
        return cmdEnvironment.get(cmdName);
    }
}
