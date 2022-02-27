package main.java.environment;

import main.java.execution.commands.BuiltInCmd;
import main.java.execution.commands.Cat;
import main.java.execution.commands.Echo;
import main.java.execution.commands.Exit;
import main.java.execution.commands.Pwd;
import main.java.execution.commands.Wc;

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
