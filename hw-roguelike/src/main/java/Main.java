import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import frame.Frame;
import frame.FrameCalculator;
import frame.FrameGenerator;
import graphics.GraphicsDrawer;
import input.InputScanner;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        FrameCalculator frameCalculator = new FrameCalculator();

        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            screen = defaultTerminalFactory.createScreen();
            screen.startScreen();
            screen.setCursorPosition(null);

            GraphicsDrawer graphicsDrawer = new GraphicsDrawer(screen);
            InputScanner inputScanner = new InputScanner(screen);

            while (true) {
                List<InputScanner.COMMAND> commands = inputScanner.getCommands();
                if (commands.stream().anyMatch(Predicate.isEqual(InputScanner.COMMAND.QUIT))) {
                    break;
                }
                Frame nextFrame = frameCalculator.nextFrame(commands);
                graphicsDrawer.draw(nextFrame);
                Thread.sleep(20);   //TODO: need to optimize?
            }

            screen.readInput();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
