package frame;

import entities.Obstacle;
import entities.mobs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FrameGenerator {
    public static final int OBSTACLES_RANDOM_MAX_NUMBER = 4;
    public static final int OBSTACLES_RANDOM_MAX_SIZE = 10;

    public static final int MOB_RANDOM_MAX_SIZE = 3;
    public static final int MOB_RANDOM_MAX_POWER = 10;

    // TODO: figure out the dependency: terminal size <-> playground size
    public static final int PLAYGROUND_WIDTH = 80;
    public static final int PLAYGROUND_HEIGHT = 24;

    private final Random random = new Random(0);

    public Frame getNextFrame() {
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
        return new Frame(obstacleList, mobList);
    }

    private BehaviourStrategy getMobStrategy(int pick) {
        return switch (pick) {
            case 0 -> new CowardBehaviourStrategy();
            case 1 -> new AggressiveBehaviourStrategy();
            default -> new PassiveBehaviourStrategy();
        };
    }
}
