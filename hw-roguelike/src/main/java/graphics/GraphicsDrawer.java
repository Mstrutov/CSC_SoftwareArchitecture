package graphics;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import entities.Mob;
import entities.Obstacle;
import frame.Frame;
import frame.FrameGenerator;

import java.io.IOException;

public class GraphicsDrawer {
    private enum CHAR_OF {
        BLANK(' '),
        OBSTACLE('#'),
        MOB('%'),
        PLAYER('@');

        private final char underlyingChar;

        CHAR_OF(char c) {
            underlyingChar = c;
        }

        public char get() {
            return underlyingChar;
        }
    }

    private Screen screen = null;

    private static void clearScreen(Screen screen) {
        // initialize window, it could be useful to draw here some lines, HUD block edges or smth
        for (int column = 0; column < FrameGenerator.PLAYGROUND_WIDTH; column++) {
            for (int row = 0; row < FrameGenerator.PLAYGROUND_HEIGHT; row++) {
                screen.setCharacter(column, row, TextCharacter.fromCharacter(
                        CHAR_OF.BLANK.get(),
                        TextColor.ANSI.DEFAULT,
                        TextColor.ANSI.DEFAULT)[0]);
            }
        }
    }

    public GraphicsDrawer(Screen screen) {
        this.screen = screen;

        clearScreen(screen);
    }

    public void draw(Frame frame) {
        clearScreen(screen);

        for (Obstacle obstacle : frame.getObstacles()) {
            for (int column = obstacle.getLeftBorder(); column <= obstacle.getRightBorder(); column++) {
                for (int row = obstacle.getBottomBorder(); row <= obstacle.getTopBorder(); row++) {
                    screen.setCharacter(column, row, TextCharacter.fromCharacter(
                            CHAR_OF.OBSTACLE.get(),
                            TextColor.ANSI.DEFAULT,
                            TextColor.ANSI.DEFAULT)[0]);
                }
            }
        }
        for (Mob mob : frame.getMobs()) {
            screen.setCharacter(mob.getCoordX(), mob.getCoordY(), TextCharacter.fromCharacter(
                    CHAR_OF.MOB.get(),
                    mob.isDead() ? TextColor.ANSI.RED : TextColor.ANSI.DEFAULT,
                    TextColor.ANSI.DEFAULT)[0]);
        }
        if (frame.getPlayer() != null) {
            screen.setCharacter(frame.getPlayer().getCoordX(), frame.getPlayer().getCoordY(), TextCharacter.fromCharacter(
                    CHAR_OF.PLAYER.get(),
                    TextColor.ANSI.DEFAULT,
                    TextColor.ANSI.DEFAULT)[0]);
        }
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
