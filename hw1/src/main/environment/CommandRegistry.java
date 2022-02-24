package main.environment;

import java.util.Map;
import main.execution.commands.BuiltInCmd;

public class CommandRegistry {
    private Map<String, BuiltInCmd> cmdEnvironment;

    //TODO: либо наполнять в конструкторе NinB, либо как-нибудь через static придумать
    public BuiltInCmd getCmd(String cmdName) {
        return cmdEnvironment.get(cmdName);
    }
}
