package frame;

import entities.Player;
import input.InputScanner;

import java.util.*;
import java.util.function.Predicate;

public class FrameCalculator {
    private Frame currentFrame;
    private final FrameGenerator frameGenerator = new FrameGenerator();
    private final Player player = new Player();
    private Map<Integer, Map<Integer, Frame>> frames = new HashMap<>();
    private int coordX = 0;
    private int coordY = 0;


    public FrameCalculator(Frame startFrame) {
        currentFrame = startFrame;
        frames.put(0, new HashMap<>(Map.of(0, currentFrame)));
    }

    public FrameCalculator() {
        currentFrame = frameGenerator.getNextFrame();
        currentFrame.addPlayer(player);
        frames.put(0, new HashMap<>(Map.of(0, currentFrame)));
    }

    public Frame nextFrame(List<InputScanner.COMMAND> commands) {
        int newCoordX = processDeltaX(commands);
        int newCoordY = processDeltaY(commands);

        if (CollisionController.isOkToMove(currentFrame, player.getCoordX() + newCoordX, player.getCoordY() + newCoordY)) {
            player.moveCharacter(newCoordX, newCoordY);
            if (player.getCoordX() < 0 || player.getCoordX() > FrameGenerator.PLAYGROUND_WIDTH
                    || player.getCoordY() < 0 || player.getCoordY() > FrameGenerator.PLAYGROUND_HEIGHT) {
                changeFrame();
            }

        }
        return currentFrame;
    }

    private void changeFrame() {
        if (player.getCoordX() < 0) {
            coordX -= 1;
            player.moveCharacter(FrameGenerator.PLAYGROUND_WIDTH, 0);
        } else if (player.getCoordX() > FrameGenerator.PLAYGROUND_WIDTH) {
            coordX += 1;
            player.moveCharacter(-FrameGenerator.PLAYGROUND_WIDTH, 0);
        } else if (player.getCoordY() < 0) {
            coordY -= 1;
            player.moveCharacter(0, FrameGenerator.PLAYGROUND_HEIGHT);
        } else if (player.getCoordY() > FrameGenerator.PLAYGROUND_HEIGHT) {
            coordY += 1;
            player.moveCharacter(0, -FrameGenerator.PLAYGROUND_HEIGHT);
        }
        if (frames.get(coordX) == null) {
            currentFrame = frameGenerator.getNextFrame();
            frames.put(coordX, new HashMap<>(Map.of(coordY, currentFrame)));
        } else if (frames.get(coordX).get(coordY) == null) {
                currentFrame = frameGenerator.getNextFrame();
                frames.get(coordX).put(coordY, currentFrame);
        } else {
            currentFrame = frames.get(coordX).get(coordY);
        }
        currentFrame.addPlayer(player);
    }

    private int processDeltaX(List<InputScanner.COMMAND> commands) {
        return (int) (
                commands.stream()
                        .filter(Predicate.isEqual(InputScanner.COMMAND.MOVE_RIGHT))
                        .count() -
                        commands.stream()
                                .filter(Predicate.isEqual(InputScanner.COMMAND.MOVE_LEFT))
                                .count());

    }

    private int processDeltaY(List<InputScanner.COMMAND> commands) {
        return (int) (
                commands.stream()
                        .filter(Predicate.isEqual(InputScanner.COMMAND.MOVE_DOWN))
                        .count() -
                        commands.stream()
                                .filter(Predicate.isEqual(InputScanner.COMMAND.MOVE_UP))
                                .count());
    }


}
