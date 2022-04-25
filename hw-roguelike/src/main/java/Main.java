import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import frame.Frame;
import frame.FrameCalculator;
import frame.RoomGenerator;
import graphics.GraphicsDrawer;
import input.Command;
import input.InputScanner;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {


        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(RoomGenerator.PLAYGROUND_WIDTH,
                RoomGenerator.PLAYGROUND_HEIGHT + GraphicsDrawer.INVENTORY_HEIGHT));
        Screen screen = null;
        try {
            screen = defaultTerminalFactory.createScreen();
            screen.startScreen();
            screen.setCursorPosition(null);

            GraphicsDrawer graphicsDrawer = new GraphicsDrawer(screen);
            FrameCalculator frameCalculator = new FrameCalculator(graphicsDrawer);
            InputScanner inputScanner = new InputScanner(screen);

            while (true) {
                List<Command> commands = inputScanner.getCommands();
                if (commands.stream().anyMatch(Predicate.isEqual(Command.QUIT))) {
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
