package frame;

import entities.Player;
import input.InputScanner;

import java.util.List;
import java.util.function.Predicate;

public class FrameCalculator {
    Frame currentFrame;
    FrameGenerator frameGenerator = new FrameGenerator();
    Player player = new Player();


    public FrameCalculator(Frame startFrame) {
        currentFrame = startFrame;
    }

    public FrameCalculator() {
        currentFrame = frameGenerator.getNextFrame();
        currentFrame.addPlayer(player);
    }

    public Frame nextFrame(List<InputScanner.COMMAND> commands) {
        player.moveCharacter(processDeltaX(commands), processDeltaY(commands));
        return currentFrame;
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
