package frame;

import entities.Player;
import entities.PlayerDirection;
import entities.mobs.Mob;
import graphics.GraphicsDrawer;
import input.Command;

import java.util.*;
import java.util.function.Predicate;

public class FrameCalculator {
    private Frame currentFrame;
    private final FrameGenerator frameGenerator = new FrameGenerator();
    private final Player player = new Player();
    private Map<Integer, Map<Integer, Frame>> frames = new HashMap<>();
    private int coordX = 0;
    private int coordY = 0;
    private final GraphicsDrawer graphicsDrawer;


    public FrameCalculator(GraphicsDrawer graphicsDrawer, Frame startFrame) {
        this.graphicsDrawer = graphicsDrawer;
        currentFrame = startFrame;
        frames.put(0, new HashMap<>(Map.of(0, currentFrame)));
    }

    public FrameCalculator(GraphicsDrawer graphicsDrawer) {
        this.graphicsDrawer = graphicsDrawer;
        currentFrame = frameGenerator.getNextFrame();
        currentFrame.addPlayer(player);
        frames.put(0, new HashMap<>(Map.of(0, currentFrame)));
    }

    public Frame nextFrame(List<Command> commands) {
        processPlayerMove(commands);
        processPlayerAttack(commands);
        processMobsAction();
        return currentFrame;
    }

    private void processMobsAction() {
        for (Mob mob : currentFrame.getMobs()) {
            if (!mob.isDead()) {
                if (mob.action(player, currentFrame)) {
                    graphicsDrawer.drawMobAttack(mob);
                }
            }
        }
        if (player.isDead()) {
            graphicsDrawer.endGame();
        }
    }

    private void processPlayerMove(List<Command> commands) {
        int newCoordX = processDeltaX(commands);
        int newCoordY = processDeltaY(commands);

        if (CollisionController.isOkToMove(currentFrame, player.getCoordX() + newCoordX, player.getCoordY() + newCoordY)) {
            player.moveCharacter(newCoordX, newCoordY);
            if (playerOutOfRoomBounds()) {
                changeFrame();
            }
        }

        setDirectionByLastMove(commands);
    }

    private boolean playerOutOfRoomBounds() {
        return FrameGenerator.outOfRoomBounds(player.getCoordX(), player.getCoordY());
    }

    private static class MeleeAttack {
        private final int ontoX;
        private final int ontoY;
        private final int damage;

        MeleeAttack(int ontoX, int ontoY, int damage) {
            this.ontoX = ontoX;
            this.ontoY = ontoY;
            this.damage = damage;
        }

        public int getDamage() {
            return damage;
        }

        public int getOntoX() {
            return ontoX;
        }

        public int getOntoY() {
            return ontoY;
        }
    }

    private void processPlayerAttack(List<Command> commands) {
        int totalDamage = commands.stream()
                .filter(Predicate.isEqual(Command.ATTACK))
                .mapToInt(attack -> 50)
                .reduce(0, Integer::sum);

        if (totalDamage == 0) {
            return;
        }

        int damageDirectionX = switch (player.getDirection()) {
            case RIGHT -> 1;
            case LEFT -> -1;
            default -> 0;
        };
        int ontoX = player.getCoordX() + damageDirectionX;

        int damageDirectionY = switch (player.getDirection()) {
            case DOWN -> 1;
            case UP -> -1;
            default -> 0;
        };
        int ontoY = player.getCoordY() + damageDirectionY;

        if (FrameGenerator.outOfRoomBounds(ontoX, ontoY)) {
            return;
        }

        List<Point> gunShots = new ArrayList<>();
        MeleeAttack attackFirstCell = new MeleeAttack(ontoX, ontoY, totalDamage);
        gunShots.add(new Point(ontoX, ontoY));
        ontoY = ontoY + damageDirectionY;
        ontoX = ontoX + damageDirectionX;
        gunShots.add(new Point(ontoX, ontoY));
        MeleeAttack attackSecondCell = new MeleeAttack(ontoX, ontoY, totalDamage);
        graphicsDrawer.drawCells(gunShots);
        assignDamageToMobs(attackFirstCell);
        assignDamageToMobs(attackSecondCell);
    }

    private void assignDamageToMobs(MeleeAttack attack) {
        List<Mob> mobs = currentFrame.getMobs();
        if (mobs == null) {
            return;
        }

        mobs.stream()
                .filter(mob -> mob.occupiesCell(attack.getOntoX(), attack.getOntoY()))
                .forEach(mob -> mob.setHealthPoints(mob.getHealthPoints() - attack.getDamage()));
    }

    private void changeFrame() {
        if (player.getCoordX() < 0) {
            coordX -= 1;
            player.moveCharacter(FrameGenerator.PLAYGROUND_WIDTH, 0);
        } else if (player.getCoordX() >= FrameGenerator.PLAYGROUND_WIDTH) {
            coordX += 1;
            player.moveCharacter(-FrameGenerator.PLAYGROUND_WIDTH, 0);
        } else if (player.getCoordY() < 0) {
            coordY -= 1;
            player.moveCharacter(0, FrameGenerator.PLAYGROUND_HEIGHT);
        } else if (player.getCoordY() >= FrameGenerator.PLAYGROUND_HEIGHT) {
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

    private int processDeltaX(List<Command> commands) {
        return (int) (
                commands.stream()
                        .filter(Predicate.isEqual(Command.MOVE_RIGHT))
                        .count() -
                        commands.stream()
                                .filter(Predicate.isEqual(Command.MOVE_LEFT))
                                .count());

    }

    private int processDeltaY(List<Command> commands) {
        return (int) (
                commands.stream()
                        .filter(Predicate.isEqual(Command.MOVE_DOWN))
                        .count() -
                        commands.stream()
                                .filter(Predicate.isEqual(Command.MOVE_UP))
                                .count());
    }

    private void setDirectionByLastMove(List<Command> commands) {
        if (commands == null) {
            return;
        }

        Optional<Command> lastMoveCommand = commands.stream()
                .filter(Predicate.isEqual(Command.MOVE_DOWN)
                        .or(Predicate.isEqual(Command.MOVE_UP))
                        .or(Predicate.isEqual(Command.MOVE_RIGHT))
                        .or(Predicate.isEqual(Command.MOVE_LEFT)))
                .reduce((prev, succ) -> succ);
        if (lastMoveCommand.isEmpty()) {
            return;
        }

        PlayerDirection directionAfterLastMove =
                switch (lastMoveCommand.get()) {
                    case MOVE_DOWN -> PlayerDirection.DOWN;
                    case MOVE_UP -> PlayerDirection.UP;
                    case MOVE_LEFT -> PlayerDirection.LEFT;
                    case MOVE_RIGHT -> PlayerDirection.RIGHT;
                    default -> throw new RuntimeException("stumbled upon unexpected command");
                };
        player.setDirection(directionAfterLastMove);
    }
}
