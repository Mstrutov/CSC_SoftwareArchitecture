package frame;

import entities.Obstacle;
import entities.mobs.*;
import entities.player.Player;

import java.util.*;

public class MapGenerator {
    public static final int OBSTACLES_RANDOM_MAX_NUMBER = 4;
    public static final int OBSTACLES_RANDOM_MAX_SIZE = 10;

    public static final int MOB_RANDOM_MAX_SIZE = 3;
    public static final int MOB_RANDOM_MAX_POWER = 10;

    // TODO: figure out the dependency: terminal size <-> playground size
    public static final int PLAYGROUND_WIDTH = 80;
    public static final int PLAYGROUND_HEIGHT = 24;

    private final Random random = new Random(0);
    private final Map<Integer, Map<Integer, Frame>> frames = new HashMap<>();

    private Frame getNextFrame(int x, int y) {
        List<Obstacle> obstacleList = generateObstacles();
        List<Mob> mobList = generateMobs(obstacleList);

        return new Frame(obstacleList, mobList, x, y);
    }

    private List<Obstacle> generateObstacles() {
        List<Obstacle> obstacleList = new ArrayList<>();
        int numberOfObstacles = random.nextInt(OBSTACLES_RANDOM_MAX_NUMBER) + 1;
        for (int i = 0; i < numberOfObstacles; i++) {
            while (true) {
                int leftBorder = random.nextInt(PLAYGROUND_WIDTH);
                int bottomBorder = random.nextInt(PLAYGROUND_HEIGHT);
                int size = random.nextInt(OBSTACLES_RANDOM_MAX_SIZE);
                if (bottomBorder + size >= PLAYGROUND_HEIGHT || leftBorder + size >= PLAYGROUND_WIDTH) {
                    continue;
                }
                Obstacle obstacle = new Obstacle(leftBorder, bottomBorder, leftBorder + size, bottomBorder + size);
                boolean badObstacle = false;
                for (Obstacle oldObstacle : obstacleList) {
                    if (CollisionController.isObstacleCollidesWithObstacle(oldObstacle, obstacle)) {
                        badObstacle = true;
                        break;
                    }
                }
                if (badObstacle) {
                    continue;
                }
                obstacleList.add(obstacle);
                break;
            }
        }
        return obstacleList;
    }

    private List<Mob> generateMobs(List<Obstacle> obstacleList) {
        List<Mob> mobList = new ArrayList<>();

        int numberOfMobs = random.nextInt(MOB_RANDOM_MAX_SIZE);
        for (int i = 0; i < numberOfMobs; i++) {
            while (true) {
                Mob mob = new DefaultMob(random.nextInt(PLAYGROUND_WIDTH), random.nextInt(PLAYGROUND_HEIGHT), 5, getMobStrategy(random.nextInt(3)));
                boolean badMob = false;
                for (Obstacle obstacle : obstacleList) {
                    if (CollisionController.isObstacleCollidesWithMob(obstacle, mob)) {
                        badMob = true;
                        break;
                    }
                }
                for (Mob oldMob : mobList) {
                    if (CollisionController.isMobCollidesWithMob(oldMob, mob)) {
                        badMob = true;
                        break;
                    }
                }
                if (!badMob) {
                    mobList.add(mob);
                    break;
                }
            }
        }
        return mobList;
    }

    public Frame getInitialFrame() {
        Frame constructedFrame = getNextFrame(0, 0);
        frames.put(0, Map.of(0, constructedFrame));
        return constructedFrame;
    }

    public Frame getAdjacentFrame(Frame currentFrame, Player player) {
        int newFrameX = currentFrame.getCoordX();
        int newFrameY = currentFrame.getCoordY();
        if (player.getCoordX() < 0) {
            newFrameX -= 1;
            player.moveCharacter(PLAYGROUND_WIDTH, 0);
        } else if (player.getCoordX() >= PLAYGROUND_WIDTH) {
            newFrameX += 1;
            player.moveCharacter(-PLAYGROUND_WIDTH, 0);
        } else if (player.getCoordY() < 0) {
            newFrameY -= 1;
            player.moveCharacter(0, PLAYGROUND_HEIGHT);
        } else if (player.getCoordY() >= PLAYGROUND_HEIGHT) {
            newFrameY += 1;
            player.moveCharacter(0, -PLAYGROUND_HEIGHT);
        }
        Frame nextFrame;
        if (frames.get(newFrameX) == null) {
            nextFrame = getNextFrame(newFrameX, newFrameY);
            frames.put(newFrameX, Map.of(newFrameY, nextFrame));
        } else if (frames.get(newFrameX).get(newFrameY) == null) {
            nextFrame = getNextFrame(newFrameX, newFrameY);
            frames.get(newFrameX).put(newFrameY, nextFrame);
        } else {
            nextFrame = frames.get(newFrameX).get(newFrameY);
        }
        nextFrame.addPlayer(player);
        return nextFrame;
    }

    private BehaviourStrategy getMobStrategy(int pick) {
        return switch (pick) {
            case 0 -> new CowardBehaviourStrategy();
            case 1 -> new AggressiveBehaviourStrategy();
            default -> new PassiveBehaviourStrategy();
        };
    }

    public static boolean outOfRoomBounds(int x, int y) {
        return x < 0 || x >= MapGenerator.PLAYGROUND_WIDTH || y < 0 || y >= MapGenerator.PLAYGROUND_HEIGHT;
    }
}
