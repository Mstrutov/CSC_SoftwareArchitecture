package input;

import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputScanner {
    Screen screen;

    private final Map<Character, Command> charToCommand;

    public InputScanner(Screen screen) {
        this.screen = screen;

        this.charToCommand = new HashMap<>();
        charToCommand.put('e', Command.ATTACK);
        charToCommand.put('w', Command.MOVE_UP);
        charToCommand.put('s', Command.MOVE_DOWN);
        charToCommand.put('a', Command.MOVE_LEFT);
        charToCommand.put('d', Command.MOVE_RIGHT);
    }

    public List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();

        try{
            var input = screen.pollInput();
            while (input != null) {
                switch (input.getKeyType()) {
                    case Character -> addIfMatchesAny(commands, input.getCharacter());
                    case Escape -> commands.add(Command.QUIT);
                }
                input = screen.pollInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commands;
    }

    private void addIfMatchesAny(List<Command> commands, char key) {
        if (charToCommand.containsKey(key)) {
            commands.add(charToCommand.get(key));
        }
    }
}
