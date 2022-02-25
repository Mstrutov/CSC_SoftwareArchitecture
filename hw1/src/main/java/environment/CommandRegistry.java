package environment;

import execution.commands.BuiltInCmd;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private Map<String, BuiltInCmd> cmdEnvironment = new HashMap<>();

    //TODO: либо наполнять в конструкторе NinB, либо как-нибудь через static придумать
    public BuiltInCmd getCmd(String cmdName) {
        return cmdEnvironment.get(cmdName);
    }
}
