package graphics;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import entities.Obstacle;
import entities.mobs.Mob;
import entities.player.Player;
import frame.Frame;
import frame.Point;
import frame.RoomGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphicsDrawer {
    public enum CHAR_OF {
        BLANK(' '),
        OBSTACLE('#'),
        MOB('%'),
        PLAYER('@'),
        GUNSHOT('*');

        private final char underlyingChar;

        CHAR_OF(char c) {
            underlyingChar = c;
        }

        public char get() {
            return underlyingChar;
        }
    }

    private final Screen screen;
    private List<Point> cellsToDraw;
    private int gunshotCountdown = 5;
    private boolean isGameEnded = false;

    private final Map<Mob, Integer> mobsAttacked;

    private static void clearScreen(Screen screen) {
        // initialize window, it could be useful to draw here some lines, HUD block edges or smth
        for (int column = 0; column < RoomGenerator.PLAYGROUND_WIDTH; column++) {
            for (int row = 0; row < RoomGenerator.PLAYGROUND_HEIGHT; row++) {
                screen.setCharacter(column, row, TextCharacter.fromCharacter(
                        CHAR_OF.BLANK.get(),
                        TextColor.ANSI.DEFAULT,
                        TextColor.ANSI.DEFAULT)[0]);
            }
        }
    }

    public GraphicsDrawer(Screen screen) {
        this.screen = screen;
        cellsToDraw = new ArrayList<>();
        mobsAttacked = new HashMap<>();
        clearScreen(screen);
    }

    public void drawCells(List<Point> cellsToDraw) {
        this.cellsToDraw.addAll(cellsToDraw);
        gunshotCountdown = 5;
    }

    public void drawMobAttack(Mob mob) {
        mobsAttacked.put(mob, 15);
    }

    public void endGame() {
        isGameEnded = true;
    }

    public void draw(Frame frame) {
        clearScreen(screen);
        if (isGameEnded) {
            drawEndGameScreen();
            return;
        }

        for (Point cellToDraw : cellsToDraw) {
            screen.setCharacter(cellToDraw.getX(), cellToDraw.getY(), TextCharacter
                    .fromCharacter(
                            CHAR_OF.GUNSHOT.get(),
                            TextColor.ANSI.BLUE,
                            TextColor.ANSI.DEFAULT)[0]);
        }
        if (!cellsToDraw.isEmpty()) {
            gunshotCountdown--;
            if (gunshotCountdown == 0) {
                cellsToDraw = new ArrayList<>();
            }
        }
        List<Mob> keysToRemove = new ArrayList<>();
        for (Map.Entry<Mob, Integer> entry : mobsAttacked.entrySet()) {
            for (int i = entry.getKey().getCoordX() - entry.getKey().getRange(); i <= entry.getKey().getCoordX() + entry.getKey().getRange(); i++) {
                for (int j = entry.getKey().getCoordY() - entry.getKey().getRange(); j <= entry.getKey().getCoordY() + entry.getKey().getRange(); j++) {
                    if (i >= 0 && i < RoomGenerator.PLAYGROUND_WIDTH && j >= 0 && j < RoomGenerator.PLAYGROUND_HEIGHT) {
                        screen.setCharacter(i, j, TextCharacter
                                .fromCharacter(
                                        CHAR_OF.GUNSHOT.get(),
                                        TextColor.ANSI.MAGENTA,
                                        TextColor.ANSI.DEFAULT)[0]);
                    }
                }
            }
            if (entry.getValue() == 0) {
                keysToRemove.add(entry.getKey());
            } else {
                mobsAttacked.put(entry.getKey(), entry.getValue() - 1);
            }
        }
        for (Mob key : keysToRemove) {
            mobsAttacked.remove(key);
        }

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
            TextColor mobColor;
            if (mob.isDead()) {
                mobColor = TextColor.ANSI.RED;
            } else if (mob.isHit()) {
                mobColor = TextColor.ANSI.BLUE;
                if (gunshotCountdown == 0) {
                    mob.recover();
                }
            } else {
                mobColor = TextColor.ANSI.DEFAULT;
            }
            screen.setCharacter(mob.getCoordX(), mob.getCoordY(), TextCharacter.fromCharacter(
                    CHAR_OF.MOB.get(),
                    mobColor,
                    TextColor.ANSI.DEFAULT)[0]);
        }
        if (frame.getPlayer() != null) {
            screen.setCharacter(frame.getPlayer().getCoordX(), frame.getPlayer().getCoordY(), TextCharacter.fromCharacter(
                    CHAR_OF.PLAYER.get(),
                    getColorForPlayerStatus(frame.getPlayer()),
                    TextColor.ANSI.DEFAULT)[0]);
        }
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: refactor status system. decorators?
    private TextColor getColorForPlayerStatus(Player player) {
        if (player.isHit()) {
            return TextColor.ANSI.MAGENTA;
        } else if (player.isGainedXp()) {
            return TextColor.ANSI.YELLOW;
        } else if (player.isLeveledUp()) {
            return TextColor.ANSI.YELLOW_BRIGHT;
        } else {
            return TextColor.ANSI.DEFAULT;
        }
    }

    private void drawEndGameScreen() {
        int index = 0;
        String endMessage = "dead  ";
        for (int i = 0; i < RoomGenerator.PLAYGROUND_HEIGHT; i++) {
            for (int j = 0; j < RoomGenerator.PLAYGROUND_WIDTH; j++) {
                screen.setCharacter(j, i, TextCharacter.fromCharacter((endMessage.charAt(index)), TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT)[0]);
                index++;
                if (index == endMessage.length()) {
                    index = 0;
                }
            }
        }
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
