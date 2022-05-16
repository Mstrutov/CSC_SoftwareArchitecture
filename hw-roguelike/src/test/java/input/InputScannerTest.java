package input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InputScannerTest {

    @Test
    void emptyInputOnEmptyCommandBuffer() throws IOException {
        Screen screen = mock(Screen.class);
        when(screen.pollInput()).thenReturn(null);

        final InputScanner inputScanner = new InputScanner(screen);

        assertEquals(new ArrayList<Command>(), inputScanner.getCommands());
    }

    @Test
    void scansMiscellaneousCommands() throws IOException {
        Screen screen = mock(Screen.class);

        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.Escape), KeyStroke.fromString("w"),
                KeyStroke.fromString("a"), KeyStroke.fromString("s"), KeyStroke.fromString("d"),
                KeyStroke.fromString("e"), KeyStroke.fromString("f"), null);

        final InputScanner inputScanner = new InputScanner(screen);

        ArrayList<Command> expected = new ArrayList<>();
        expected.add(Command.QUIT);
        expected.add(Command.MOVE_UP);
        expected.add(Command.MOVE_LEFT);
        expected.add(Command.MOVE_DOWN);
        expected.add(Command.MOVE_RIGHT);
        expected.add(Command.ATTACK);

        assertEquals(expected, inputScanner.getCommands());
    }

}
