package input;

import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputScanner {
    Screen screen;

    private final Map<Character, COMMAND> charToCommand;

    // TODO: classes with polymorphism instead?
    public enum COMMAND {
        MOVE_UP,
        MOVE_DOWN,
        MOVE_LEFT,
        MOVE_RIGHT,
        QUIT
    }

    public InputScanner(Screen screen) {
        this.screen = screen;

        this.charToCommand = new HashMap<>();
        charToCommand.put('w', COMMAND.MOVE_UP);
        charToCommand.put('s', COMMAND.MOVE_DOWN);
        charToCommand.put('a', COMMAND.MOVE_LEFT);
        charToCommand.put('d', COMMAND.MOVE_RIGHT);
    }

    public List<COMMAND> getCommands() {
        List<COMMAND> commands = new ArrayList<>();

        try{
            var input = screen.pollInput();
            while (input != null) {
                switch (input.getKeyType()) {
                    case Character -> addIfMatchesAny(commands, input.getCharacter());
                    case Escape -> commands.add(COMMAND.QUIT);
                }
                input = screen.pollInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commands;
    }

    private void addIfMatchesAny(List<COMMAND> commands, char key) {
        if (charToCommand.containsKey(key)) {
            commands.add(charToCommand.get(key));
        }
    }
}
