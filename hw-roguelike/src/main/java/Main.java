import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import frame.FrameCalculator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FrameCalculator frameCalculator = new FrameCalculator();

        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = null;
        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.putCharacter('H');
            terminal.putCharacter('e');
            terminal.putCharacter('l');
            terminal.putCharacter('l');
            terminal.putCharacter('o');
            terminal.putCharacter('\n');
            terminal.flush();
            terminal.readInput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
